package com.semicolon.centaurs.service;

import com.semicolon.centaurs.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
