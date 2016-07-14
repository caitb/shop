package com.masiis.shop.web.platform.service.message;

import com.masiis.shop.dao.platform.message.PfMessageContentMapper;
import com.masiis.shop.dao.po.PfMessageContent;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
