package com.masiis.shop.web.mall.service.message;

import com.masiis.shop.dao.mall.message.SfMessageSrRelationMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfMessageSrRelation;
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

    public Long countNumsFromUser(ComUser user) {
        return srRelationMapper.countNumsFromUser(user.getId());
    }

    public void insert(SfMessageSrRelation sr) {
        srRelationMapper.insert(sr);
    }

    /**
     * 查询未读消息数量
     *
     * @param toUserId
     * @return
     */
    public Integer queryUnseeMessageNumsByToUser(Long toUserId){
        return srRelationMapper.queryUnseeMessageNumsByToUser(toUserId, 1);
    }
}
