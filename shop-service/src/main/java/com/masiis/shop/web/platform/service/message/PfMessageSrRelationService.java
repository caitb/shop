package com.masiis.shop.web.platform.service.message;

import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.platform.message.PfMessageSrRelationMapper;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合伙人间消息收发关系service
 *
 * @Date 2016/7/12
 * @Author lzh
 */
@Service
public class PfMessageSrRelationService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfMessageSrRelationMapper srRelationMapper;

    /**
     * 查询收到的消息来自多少人(消息来源人的总数)
     *
     * @param user
     * @return
     */
    public Integer queryNumsToUser(ComUser user, Integer mType) {
        return srRelationMapper.querySrRelationNumsToUser(user.getId(), mType);
    }

    /**
     * 按消息接受者来查询消息目录(分页)
     *
     * @param user
     * @param mType
     * @param start
     * @param pageSize
     * @return
     */
    public List<PfMessageCenterDetail> queryFromUsersByToUserWithPaging(ComUser user, Integer mType, Integer start, Integer pageSize) {
        return srRelationMapper.queryByToUserWithPaging(user.getId(), mType, start, pageSize);
    }
}
