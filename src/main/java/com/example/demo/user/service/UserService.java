package com.example.demo.user.service;

import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.User;

public interface UserService {

    User register(UserRequest userRequest);

    Integer getUserIdByMobile(String mobile);

    void initUserId();
}
