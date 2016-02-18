package com.masiis.shop.dao.user;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cai_tb on 16/2/16.
 */
public interface UserMapper {

    void add(@Param("user") User user);

    User findById(@Param("id") Long id);

    User findByUserNameAndPwd(@Param("userName")String userName, @Param("password")String password);

    List<User> listByCondition(@Param("user")User user);

}
