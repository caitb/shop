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

    public User findById(Long id){
        return this.userMapper.findById(id);
    }

    public User findByUserNameAndPwd(String userName, String password){
        return this.userMapper.findByUserNameAndPwd(userName, password);
    }

    public List<User> listUserByCondition(String userName, String phone){
        return this.userMapper.listByCondition(userName, phone);
    }

    public void addUser(User user){
        this.userMapper.add(user);
    }

}
