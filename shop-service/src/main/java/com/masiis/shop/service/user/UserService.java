package com.masiis.shop.service.user;

import com.masiis.shop.dao.user.User;
import com.masiis.shop.dao.user.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cai_tb on 16/2/16.
 */
@Service
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
