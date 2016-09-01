package com.masiis.shop.web.mall.service.message;

import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.mall.message.SfSysMessageMapper;
import com.masiis.shop.dao.po.SfMessageContent;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2016/8/8
 * @Author lzh
 */
@Service
public class SfSysMessageService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfSysMessageMapper sfSysMessageMapper;


    public PfMessageCenterDetail querySysNotSeeGeneral(Long userId) {
        return sfSysMessageMapper.querySysGeneralInfo(userId);
    }

    public Integer queryNumsByUserIdAndType(Long userId, int mType) {
        return sfSysMessageMapper.selectNumsByUserIdAndType(userId, mType);
    }

    public List<SfMessageContent> queryContentByUserIdAndTypeWithPaging(Long userId, int mType, int start, int pageSize) {
        return sfSysMessageMapper.queryByUserIdAndTypeWithPaging(userId, mType, start, pageSize);
    }
}
