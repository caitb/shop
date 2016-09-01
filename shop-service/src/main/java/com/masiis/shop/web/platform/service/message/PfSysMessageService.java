package com.masiis.shop.web.platform.service.message;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.platform.message.PfSysMessageMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfMessageContent;
import com.masiis.shop.dao.po.PfSysMessage;
import com.masiis.shop.web.common.utils.notice.AppPfNoticeUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Date 2016/8/6
 * @Author lzh
 */
@Service
public class PfSysMessageService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfSysMessageMapper pfSysMessageMapper;


    public PfMessageCenterDetail querySysNotSeeGeneral(Long userId) {
        return pfSysMessageMapper.querySysGeneralInfo(userId);
    }

    public Integer queryNumsByUserIdAndType(Long tUserId, Integer mType) {
        return pfSysMessageMapper.selectNumsByUserIdAndType(tUserId, mType);
    }

    public List<PfMessageContent> queryContentByUserIdAndTypeWithPaging(Long userId, int mType, int start, int pageSize) {
        return pfSysMessageMapper.queryByUserIdAndTypeWithPaging(userId, mType, start, pageSize);
    }

    public PfSysMessage initBean(){
        PfSysMessage message = new PfSysMessage();

        message.setCreateTime(new Date());
        message.setIsSee(0);
        message.setmType(1);
        message.setIsSend(0);
        message.setStatus(1);

        return message;
    }

    public PfSysMessage initBeanByUser(ComUser user){
        PfSysMessage message = new PfSysMessage();

        message.setCreateTime(new Date());
        message.setIsSee(0);
        message.setmType(1);
        message.setIsSend(0);
        message.setStatus(1);
        message.setUserId(user.getId());

        return message;
    }

    @Transactional
    public void handleMessageAdd(PfSysMessage message) {
        try{
            if(message == null){
                throw new BusinessException("消息对象为空");
            }
            if(message.getId() != null){
                throw new BusinessException("怎么可能有呢");
            }
            pfSysMessageMapper.insert(message);
            AppPfNoticeUtils.getInstance().getMessageQueue().put(message.getId() + "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

    @Transactional
    public void handleMessageSend(String param) {
        try{
            log.info("queue_param:" + param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }
}
