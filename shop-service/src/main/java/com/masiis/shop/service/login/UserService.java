package com.masiis.shop.service.login;

import com.masiis.shop.dao.login.mapper.User;
import com.masiis.shop.dao.login.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/16.
 */
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User findByUserNameAndPwd(String userName, String password){
        return this.userMapper.findByUserNameAndPwd(userName, password);
    }

    public List<User> listUserByCondition(User user){
        return this.userMapper.listByCondition(user);
    }

    public void addUser(User user){
        this.userMapper.add(user);
    }

}
