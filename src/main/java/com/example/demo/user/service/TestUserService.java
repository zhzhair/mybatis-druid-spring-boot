package com.example.demo.user.service;

import com.example.demo.user.dto.response.User;

public interface TestUserService {

    void dropTables();

    void createTables();

    Integer getUserIdByMobile();

    User register();
}
