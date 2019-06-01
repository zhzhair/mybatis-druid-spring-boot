package com.example.demo;

import com.example.demo.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@MapperScan(basePackages = "com.example.demo.*.dao")
@SpringBootApplication
public class UserApplication {

    @Resource
    private UserService userService;
    private static UserService staticUserService;

    @PostConstruct
    public void init(){
        staticUserService = this.userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        staticUserService.initUserId();
    }
}
