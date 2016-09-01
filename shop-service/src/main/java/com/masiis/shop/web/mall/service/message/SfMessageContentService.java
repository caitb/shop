package com.masiis.shop.web.mall.service.message;

import com.masiis.shop.dao.mall.message.SfMessageContentMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfMessageContent;
import com.masiis.shop.dao.po.SfShop;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Date 2016/7/5
 * @Author lzh
 */
@Service
public class SfMessageContentService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfMessageContentMapper sfMessageContentMapper;

    /**
     * 查询群发类型消息数量
     *
     * @param user
     * @return
     */
    public Integer queryNumsFromUser(ComUser user){
        Map<String, Object> params = new HashMap<>();
        params.put("userId", user.getId());
        Integer[] types = {3, 4};
        params.put("types", types);
        return sfMessageContentMapper.queryNumsFromUser(params);
    }

    public List<SfMessageContent> queryContentFromUser(ComUser user, Integer start, Integer size) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", user.getId());
        Integer[] types = {3, 4};
        params.put("types", types);
        params.put("start", start);
        params.put("size", size);
        return sfMessageContentMapper.selectByFromUserAndType(params);
    }

    public SfMessageContent createMessageByShopAndType(SfShop shop, String message, Integer type, String remark, String url) {
        SfMessageContent content = new SfMessageContent();

        content.setContent(message);
        content.setCreateTime(new Date());
        content.setShopId(shop.getId());
        content.setStatus(1);
        content.setType(type);
        content.setUserId(shop.getUserId());
        content.setUpdateTime(new Date());
        content.setContentUrl(url);
        content.setRemark(remark);

        return content;
    }

    public void insert(SfMessageContent content) {
        sfMessageContentMapper.insert(content);
    }

    /**
     * 查询mall粉丝收到消息的店铺个数
     * @param userId
     * @return
     */
    public Integer queryShopNums(Long userId){
        return sfMessageContentMapper.queryShopNums(userId);
    }

    /**
     * 查询关注的店铺未读消息数量和最新未读消息内容
     * @param userId
     */
    public List<Map<String, String>> queryUnreadShopInfosByUser(Long userId, Integer start, Integer size){
        return sfMessageContentMapper.selectShopInfoAndFirstMsg(userId, start, size);
    }

    public void updateRelationIsSeeByFromUserAndToUser(Long fromUser, Long toUser){
        sfMessageContentMapper.updateRelationIsSeeByFromUserAndToUser(fromUser, toUser);
    }

    /**
     * 根据消息来源用户和当前用户查询消息总条数
     * @param fromUser
     * @param toUser
     * @return
     */
    public Integer queryNumsFromUserAndToUser(Long fromUser, Long toUser){
        return sfMessageContentMapper.queryNumsFromUserAndToUser(fromUser, toUser);
    }

    /**
     * 根据消息来源和当前用户查询消息详情（带分页）
     *
     * @param userId
     * @param fUserId
     * @param start
     * @param pageSize
     * @return
     */
    public List<SfMessageContent> queryDetailByFromUserAndToUserWithPaging(Long userId, Long fUserId, Integer start, Integer pageSize){
        return sfMessageContentMapper.queryDetailByFromUserAndToUserWithPaging(userId, fUserId, start, pageSize);
    }

    public SfMessageContent createMessageByShopAndAppType(SfShop shop,
                      String message, Integer messageType, String remark, String url, Integer appType, String appParam) {
        SfMessageContent content = new SfMessageContent();

        content.setContent(message);
        content.setCreateTime(new Date());
        content.setShopId(shop.getId());
        content.setStatus(1);
        content.setType(messageType);
        content.setUserId(shop.getUserId());
        content.setUpdateTime(new Date());
        content.setContentUrl(url);
        content.setUrlAppType(appType);
        content.setUrlAppParam(appParam);
        content.setRemark(remark);

        return content;
    }
}
