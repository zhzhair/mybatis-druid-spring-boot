package com.example.demo.user.controller;

import com.example.demo.common.controller.BaseController;
import com.example.demo.common.dto.BaseResponse;
import com.example.demo.config.TokenManager;
import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.LoginResponse;
import com.example.demo.user.dto.response.User;
import com.example.demo.user.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private TokenManager tokenManager;

    @RequestMapping(value = "/loginByMobile", method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<LoginResponse> loginByMobile(@RequestParam(value = "mobile") String mobile) {
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        Integer userId = userService.getUserIdByMobile(mobile);
        if(userId != null){
            baseResponse.setCode(0);
            baseResponse.setMsg("手机号登录成功");
            String token = tokenManager.generateToken(userId);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setExpire(System.currentTimeMillis() + 3600 * 1000);
            loginResponse.setToken(token);
            loginResponse.setUserId(userId);
            baseResponse.setData(loginResponse);
        }else{
            baseResponse.setCode(-3);
            baseResponse.setMsg("手机号未注册");
        }
        return baseResponse;
    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<User> register(@RequestBody UserRequest userRequest) {
        BaseResponse<User> baseResponse = new BaseResponse<>();
        Integer userId = userService.getUserIdByMobile(userRequest.getMobile());
        if(userId == null){
            User user = userService.register(userRequest);
            baseResponse.setCode(0);
            baseResponse.setData(user);
            baseResponse.setMsg("注册成功");
        }else{
            baseResponse.setCode(1);
            baseResponse.setMsg("手机号已被注册");
        }
        return baseResponse;
    }

}
