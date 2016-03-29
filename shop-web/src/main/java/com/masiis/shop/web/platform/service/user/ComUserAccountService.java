package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfBorder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lzh on 2016/3/19.
 */
@Service
public class ComUserAccountService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    ComUserAccountMapper accountMapper;

    /**
     * 根据用户id查询用户资产账户
     *
     * @param id
     * @return
     */
    public ComUserAccount findAccountByUserid(Long id) {
        return accountMapper.findByUserId(id);
    }

    /**
     * 创建用户之初,创建用户的资产对象
     *
     * @param user
     */
    public void createAccountByUser(ComUser user) {
        ComUserAccount account = new ComUserAccount();
        account.setComUserId(user.getId());
        account.setCostFee(new BigDecimal(0));
        account.setCountingFee(new BigDecimal(0));
        account.setExtractableFee(new BigDecimal(0));
        account.setBailFee(new BigDecimal(0));
        account.setCreatedTime(new Date());
        accountMapper.insert(account);
    }

    /**
     * 订单完成,根据订单来计算结算和总销售额,并创建对应的账单子项
     *
     * @param order
     */
    public void countByOrder(PfBorder order){

    }
}
