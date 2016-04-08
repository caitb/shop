package com.masiis.shop.web.mall.service.user;

import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.po.SfUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangbingjian on 2016/4/8.
 */
@Service
public class SfUserAccountService {

    @Autowired
    private SfUserAccountMapper userAccountMapper;

    /**
     * 根据用户id查询分销用户账户表
     * @param userId
     * @return
     */
    public SfUserAccount findAccountByUserId(Long userId){
        return userAccountMapper.selectByUserId(userId);
    }

}
