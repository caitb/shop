package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.po.ComUserAccount;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
