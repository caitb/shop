package com.masiis.shop.web.platform.service.message;

import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.platform.message.PfMessageSrRelationMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfMessageContent;
import com.masiis.shop.dao.po.PfMessageSrRelation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    /**
     * 根据消息来源用户和当前用户查询消息总条数
     *
     * @param tUserId
     * @param fUserId
     * @param mType
     * @return
     */
    public Integer queryNumsFromUserAndToUser(Long tUserId, Long fUserId, Integer mType) {
        return srRelationMapper.queryNumsByFromUseAndToUserAndType(tUserId, fUserId, mType);
    }

    /**
     * 根据消息来源和当前用户查询消息详情（带分页）
     *
     * @param userId
     * @param fUserId
     * @param mType
     * @param start
     * @param pageSize
     * @return
     */
    public List<PfMessageContent> queryDetailByFromUserAndToUserWithPaging(Long userId, Long fUserId,
                                                                           Integer mType, Integer start, Integer pageSize) {
        return srRelationMapper.queryDetailByFromUserAndToUserWithPaging(userId, fUserId, mType, start, pageSize);
    }

    /**
     * 根据来源用户和接收用户更新消息为已查看
     *
     * @param fUserId
     * @param tUserId
     */
    public Integer updateRelationIsSeeByFromUserAndToUser(Long fUserId, Long tUserId) {
        return srRelationMapper.updateRelationIsSeeByFromUserAndToUser(fUserId, tUserId);
    }

    public PfMessageSrRelation createRelationByContent(PfMessageContent content, Long toUser) {
        PfMessageSrRelation relation = new PfMessageSrRelation();

        relation.setRemark(content.getRemark());
        relation.setCreateTime(new Date());
        relation.setStatus(1);
        relation.setFromUser(content.getUserId());
        relation.setIsSee(0);
        relation.setmType(content.getType());
        relation.setPfMessageContentId(content.getId());
        relation.setToUser(toUser);

        return relation;
    }

    public void insert(PfMessageSrRelation srRelation) {
        srRelationMapper.insert(srRelation);
    }

    /**
     * 根据接收消息人id和消息类型查询未读消息数量
     *
     * @param toUserId
     * @param mType
     * @return
     */
    public Integer queryNoSeeNumsByToUserAndType(Long toUserId, Integer mType){
        return srRelationMapper.queryNoSeeNumsByToUserAndType(toUserId, mType);
    }
}
