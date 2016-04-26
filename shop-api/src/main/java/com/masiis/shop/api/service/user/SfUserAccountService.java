package com.masiis.shop.api.service.user;

import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfUserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

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

    /**
     * 根据ComUser创建小铺端用户账户对象
     *
     * @param user
     */
    public void createSfAccountByUser(ComUser user) {
        SfUserAccount account = new SfUserAccount();

        account.setCreateTime(new Date());
        account.setExtractableFee(new BigDecimal(0));
        account.setCountingFee(new BigDecimal(0));
        account.setUserId(user.getId());
        account.setVersion(0L);

        userAccountMapper.insert(account);
    }
}
