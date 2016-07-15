package com.masiis.shop.web.platform.service.message;

import com.masiis.shop.dao.platform.message.PfMessageContentMapper;
import com.masiis.shop.dao.po.PfMessageContent;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 合伙人间消息内容service
 *
 * @Date 2016/7/12
 * @Author lzh
 */
@Service
public class PfMessageContentService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfMessageContentMapper contentMapper;

    /**
     * 查看发出的消息内容
     *
     * @param userId
     * @return
     */
    public List<PfMessageContent> queryContentByUserId(Long userId, Integer type) {
        return contentMapper.queryByUserId(userId, type);
    }

    /**
     * 查询发出的最新一条消息
     *
     * @param userId
     * @param type
     * @return
     */
    public PfMessageContent queryContentLatestByUserId(Long userId, Integer type) {
        return contentMapper.queryLatestByUserIdAndType(userId, type);
    }

    public PfMessageContent createMessageByType(Long userId, String message, Integer messageType, String remark, String url) {
        PfMessageContent content = new PfMessageContent();
        content.setType(messageType);
        content.setStatus(1);
        content.setCreateTime(new Date());
        content.setContent(message);
        content.setContentUrl(url);
        content.setRemark(remark);
        content.setUpdateTime(new Date());
        content.setUserId(userId);
        return content;
    }

    public void insert(PfMessageContent content) {
        contentMapper.insert(content);
    }

    /**
     * 根据消息创建者和类型查询消息（带分页）
     *
     * @param fUserId
     * @param type
     * @param start
     * @param pageSize
     * @return
     */
    public List<PfMessageContent> queryContentByUserIdAndTypeWithPaging(Long fUserId, Integer type,
                                                                        Integer start, Integer pageSize) {
        return contentMapper.queryByUserIdAndTypeWithPaging(fUserId, type, start, pageSize);
    }

    /**
     * 根据消息发送者和消息类型查询消息总数
     *
     * @param fUserId
     * @param mType
     * @return
     */
    public Integer queryNumsByUserIdAndType(Long fUserId, int mType) {
        return contentMapper.queryNumsByUserIdAndType(fUserId, mType);
    }
}
