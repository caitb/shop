package com.masiis.shop.web.mall.service.message;

import com.masiis.shop.dao.mall.message.SfMessageSrRelationMapper;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Date 2016/7/5
 * @Author lzh
 */
@Service
public class SfMessageSrRelationService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfMessageSrRelationMapper srRelationMapper;

    public Integer countNumsFromUser(ComUser user) {
        return srRelationMapper.countNumsFromUser(user.getId());
    }
}
