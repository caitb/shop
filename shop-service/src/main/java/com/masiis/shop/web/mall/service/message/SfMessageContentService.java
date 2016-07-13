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
     * 查询关注的店铺未读消息数量和最新未读消息内容
     * @param userId
     */
    public List<Map<String, String>> queryNumsAndFirstByUser(Long userId){
        List<Map<String, String>> returnList = new ArrayList<>();
        List<Map<String, Long>> temp = sfMessageContentMapper.selectUnreadNumsAndFromByUser(userId);
        System.out.println("------------------------" + temp);
        Set<Long> difUserSet = new HashSet<>();
        Map<Long, Map<String, Long>> difMap = new LinkedHashMap<>();
        for(int i = 0; i < temp.size(); i++){
            Long fromUser = temp.get(i).get("fromUser");
            if(!difUserSet.contains(fromUser)){
                difUserSet.add(fromUser);
                Map<String, Long> tempMap = new LinkedHashMap<>();
                tempMap.put("contentId", temp.get(i).get("contentId"));
                tempMap.put("num", 1L);
                difMap.put(fromUser, tempMap);
            }else{
                Map<String, Long> tempMap = difMap.get(fromUser);
                tempMap.put("num", tempMap.get("num")+1);
            }
        }
        System.out.println("------++++++++++++++------------------" + difMap);
        for(Map<String, Long> valueMap : difMap.values()){
            System.out.println(valueMap.get("contentId"));
            Map<String, String> infoMap = sfMessageContentMapper.selectShopInfoAndFirMsgByMsgId(valueMap.get("contentId"));
            infoMap.put("num", valueMap.get("num").toString());
            System.out.println(infoMap);
            returnList.add(infoMap);
        }
        return returnList;
    }
}
