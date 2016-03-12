package com.semicolon.centaurs.validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.semicolon.centaurs.util.MessageConstants;

    @Component
    public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    	static Logger LOGGER = Logger.getLogger(CustomSuccessHandler.class.getName());
    	
        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        
        @Override
        protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            String targetUrl = determineTargetUrl(authentication);

            if (response.isCommitted()) {
            	LOGGER.info(MessageConstants.REDIRECT_ERROR);
                return;
            }

            redirectStrategy.sendRedirect(request, response, targetUrl);
        }

        protected String determineTargetUrl(Authentication authentication) {
            String url="";

            Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();

            List<String> roles = new ArrayList<String>();

            for (GrantedAuthority a : authorities) {
                roles.add(a.getAuthority());
            }

            if (isHr(roles)) {
                url = "/index";
            } else if (isUser(roles)) {
                url = "/employee";
            } else {
                url="/login";
            }
            return url;
        }

        public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
            this.redirectStrategy = redirectStrategy;
        }
        
        protected RedirectStrategy getRedirectStrategy() {
            return redirectStrategy;
        }

        private boolean isUser(List<String> roles) {
            if (roles.contains(MessageConstants.ROLE_USER)) {
                return true;
            }
            return false;
        }

        private boolean isHr(List<String> roles) {
            if (roles.contains(MessageConstants.ROLE_HR)) {
                return true;
            }
            return false;
        }

    }
