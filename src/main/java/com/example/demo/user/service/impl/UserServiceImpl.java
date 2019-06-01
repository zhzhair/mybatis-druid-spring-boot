package com.example.demo.user.service.impl;

import com.example.demo.user.dao.UserMapper;
import com.example.demo.user.dto.request.UserRequest;
import com.example.demo.user.dto.response.User;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    private final String USER_ID_INC = "USER_ID_INC";
    @Resource
    private UserMapper userMapper;
    @Resource(name = "stringRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Value("${table-num}")
    private int tableNum;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public User register(UserRequest userRequest) {
        Long num = redisTemplate.opsForValue().increment(USER_ID_INC,1);
        Integer userId = Integer.valueOf(num + "");
        User user = new User();
        user.setUserId(userId);
        user.setMobile(userRequest.getMobile());
        user.setIcon(userRequest.getIcon());
        user.setNickname(userRequest.getNickname());
        int rem = userId % tableNum;
        userMapper.insertUser(user,String.valueOf(rem));
        int rem0 = Math.abs(userRequest.getMobile().hashCode()) % tableNum;
        String mobile = userRequest.getMobile();
        userMapper.insertUserMobile(mobile,userId,String.valueOf(rem0));
        return user;
    }

    @Override
    public Integer getUserIdByMobile(String mobile) {
        int rem = Math.abs(mobile.hashCode()) % tableNum;
        return userMapper.getUserByMobile(mobile,String.valueOf(rem));
    }

    @Override
    public void initUserId() {
        String usercode = redisTemplate.opsForValue().get(USER_ID_INC);
        if(usercode == null){
            int temp = 0;
            for (int i = 0; i < tableNum; i++) {
                Integer maxUserId = userMapper.getMaxUserId(String.valueOf(i));
                if(maxUserId != null && temp < maxUserId){
                    temp = maxUserId;
                }
            }
            redisTemplate.opsForValue().set(USER_ID_INC,String.valueOf(temp + 1));
        }
    }

}
