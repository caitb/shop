package com.masiis.shop.dao.user;

import java.util.List;

/**
 * Created by cai_tb on 16/2/16.
 */
public interface UserMapper {

    void add(User user);

    User findById(Long id);

    User findByUserNameAndPwd(String userName, String password);

    List<User> listByCondition(User user);

}
