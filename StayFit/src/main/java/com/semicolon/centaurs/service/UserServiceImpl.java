package com.semicolon.centaurs.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.semicolon.centaurs.model.Role;
import com.semicolon.centaurs.model.User;
import com.semicolon.centaurs.repository.RoleRepository;
import com.semicolon.centaurs.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> rolesList = roleRepository.findAll();
        Set<Role> roleSet = new HashSet<Role>();
        for (Role role : rolesList) {
        	role.getName().equals("ROLE_USER");
        	roleSet.add(role);
		}
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
