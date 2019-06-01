package com.example.demo.user.dao;

import com.example.demo.user.dto.response.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    Integer getUserByMobile(@Param("mobile") String mobile, @Param("suffix") String suffix);
    Integer insertUser(@Param("user") User user, @Param("suffix") String suffix);
    Integer insertUserMobile(@Param("mobile") String mobile, @Param("userId") Integer userId, @Param("suffix") String suffix);
    Integer getMaxUserId(@Param("suffix") String suffix);

    void dropTable(@Param("tableName") String tableName);
    void createTableUser(@Param("suffix") String suffix);
    void createTableUserMobile(@Param("suffix") String suffix);
}