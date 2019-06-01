package com.example.demo.user.service.impl;

import com.example.demo.common.util.StringTools;
import com.example.demo.user.dao.UserMapper;
import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.User;
import com.example.demo.user.service.TestUserService;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class TestUserServiceImpl implements TestUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Value("${table-num}")
    private int tableNum;

    @Override
    public void dropTables() {
        IntStream.range(0,tableNum).parallel().forEach(this::dropTables);
    }

    private void dropTables(int i){
        userMapper.dropTable("user_" + i);
        userMapper.dropTable("user_mobile_" + i);
    }

    @Override
    public void createTables() {
        IntStream.range(0,tableNum).parallel().forEach(this::createTables);
    }

    private void createTables(int i){
        String suffix = String.valueOf(i);
        userMapper.createTableUser(suffix);
        userMapper.createTableUserMobile(suffix);
    }

    @Override
    public Integer getUserIdByMobile() {
        return userService.getUserIdByMobile(StringTools.getMobileStr());
    }

    @Override
    public User register() {
        UserRequest userRequest = new UserRequest();
        userRequest.setMobile(StringTools.getMobileStr());
        userRequest.setIcon("http://127.0.0.1/"+StringTools.getMobileStr()+".jpg");
        int rand = new Random().nextInt(4);
        userRequest.setNickname(new String[]{"xiaoming","xiaohong","xiaoqiang","xiaoli"}[rand]);
        return userService.register(userRequest);
    }

}
