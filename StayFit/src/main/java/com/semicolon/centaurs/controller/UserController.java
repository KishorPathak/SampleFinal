package com.semicolon.centaurs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semicolon.centaurs.model.User;
import com.semicolon.centaurs.service.SecurityService;
import com.semicolon.centaurs.service.UserMasterService;
import com.semicolon.centaurs.service.UserService;
import com.semicolon.centaurs.validator.UserValidator;
import com.semicolon.centaurs.valueobjects.AppUserRegisterRequestVO;
import com.semicolon.centaurs.valueobjects.AppUserRequestVO;
import com.semicolon.centaurs.valueobjects.AppUserResponseVO;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private UserMasterService userMasterService;
    
    private static final String APPLICATION_JSON = "application/json";
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET )
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    
    @ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/loginApp", method = RequestMethod.POST, consumes = APPLICATION_JSON)
    public AppUserResponseVO loginApp(@RequestBody final AppUserRequestVO appUserVO) {
    	AppUserResponseVO appUserResponseVO = new AppUserResponseVO();
    	User user = userService.findByUsername(appUserVO.getUsername());
    	if(null!=user){
    		appUserResponseVO.setUsername(user.getUsername());
    		appUserResponseVO.setPassword(user.getPassword());
    		return appUserResponseVO;
    	}
    	return appUserResponseVO;
    }
    
    @SuppressWarnings("unused")
	@ResponseBody
	@Transactional(propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/registerApp", method = RequestMethod.POST, consumes = APPLICATION_JSON)
    public AppUserResponseVO registerApp(@RequestBody final AppUserRegisterRequestVO request) {
    	 
    	AppUserResponseVO appUserResponseVO = new AppUserResponseVO();
    	User user = new User();
    	user.setUsername(request.getEmail());
    	user.setPassword(request.getPassword());
    	user.setPasswordConfirm(request.getPasswordConfirm());
    	
    	userService.save(user);
    	userMasterService.saveEmployeeMaster(request);
    	if(null!=user){
    		appUserResponseVO.setUsername(user.getUsername());
    		appUserResponseVO.setPassword(user.getPassword());
    		return appUserResponseVO;
    	}
    	return appUserResponseVO;
    }
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = {"/", "/hr"}, method = RequestMethod.GET)
    public String hr(Model model) {
        return "hr";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
    	Long userId = getCurrentLoggedInUserId();
    	String userName = getCurrentLoggedInUserName();
    	model.addAttribute("userName", userName);
    	model.addAttribute("userId", userId);
        return "index";
    }
    
    @RequestMapping(value = {"/", "/employee"}, method = RequestMethod.GET)
    public String employee(Model model) {
    	Long userId = getCurrentLoggedInUserId();
    	String userName = getCurrentLoggedInUserName();
    	model.addAttribute("userName", userName);
    	model.addAttribute("userId", userId);
        return "employee";
    }
    
    private Long getCurrentLoggedInUserId() {
		String userName = getCurrentLoggedInUserName();
    	User user = userService.findByUsername(userName);
    	Long userId = user.getId();
		return userId;
	}

	private String getCurrentLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String userName = authentication.getName();
		return userName;
	}
}
