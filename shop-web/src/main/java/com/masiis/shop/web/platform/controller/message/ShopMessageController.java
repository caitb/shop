package com.masiis.shop.web.platform.controller.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.*;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.message.SfMessageDetail;
import com.masiis.shop.dao.beans.message.SfMessagePageFList;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfMessageContent;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.service.message.SfMessageContentService;
import com.masiis.shop.web.mall.service.message.SfMessageDetailService;
import com.masiis.shop.web.mall.service.message.SfMessageSrRelationService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺消息controller
 *
 * @Date 2016/7/4
 * @Author lzh
 */
@Controller
@RequestMapping("/shopmessage")
public class ShopMessageController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfMessageSrRelationService srRelationService;
    @Resource
    private SfMessageDetailService sfMessageDetailService;
    @Resource
    private SfMessageContentService contentService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private SfShopSkuService sfShopSkuService;

    @RequestMapping("/mycluster.shtml")
    public String toShopMessageList(){
        return "platform/shop/cluster_message_list";
    }

    /**
     * 分页查询我发出的群发消息
     *
     * @param request
     * @param cur
     * @return
     */
    @RequestMapping("/mycluster.do")
    @ResponseBody
    public String messageList(HttpServletRequest request,
                              @RequestParam(required = true) Integer cur){
        int pageSize = 10;
        JSONObject res = new JSONObject();
        List<SfMessageContent> resData = new ArrayList<>();
        res.put("data", resData);
        try{
            ComUser user = getComUser(request);
            if(user == null){
                throw new BusinessException("怎么能没用户呢！");
            }
            if(cur == null || cur < 0){
                throw new BusinessException("当前页码不正确");
            }
            int totalNums = contentService.queryNumsFromUser(user);
            if(totalNums == 0){
                res.put("hasData", false);
                res.put("resCode", "success");
                res.put("resMsg", "暂无数据");
                return res.toJSONString();
            }
            // 第一页页码是0
            int pageNums = totalNums/pageSize + (totalNums%pageSize == 0 ? 0 : 1);
            // 检测页码是否超出总页数
            if(cur + 1 > pageNums){
                throw new BusinessException("下一页码不正确");
            }
            // 检测是否是最后一页
            if(cur + 1 == pageNums){
                // 最后一页
                res.put("isLast", true);
            }else{
                // 非最后一页
                res.put("isLast", false);
            }
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());
            res.put("resCode", "success");
            res.put("cur", cur);
            res.put("hasData", true);
            res.put("pageSize", pageSize);
            res.put("totalPage",pageNums);
            res.put("head_url", user.getWxHeadImg());
            res.put("shop_name", shop.getName());
            int start = cur * pageSize;
            // 查询要展现的消息数据
            resData = contentService.queryContentFromUser(user, start, pageSize);
            res.put("data", resData);
        } catch (Exception e) {
            res.put("resCode", "fail");
            res.put("resMsg", e.getMessage());
        }
        return JSONObject.toJSONStringWithDateFormat(res, "yyyy-MM-dd HH:mm:ss");
    }

    @RequestMapping("/toNew.shtml")
    public String toNewMessage(){
        return "platform/shop/new_shop_cluster_message";
    }

    @RequestMapping("/toNewData.do")
    public String toNewMessageData(HttpServletRequest request){
        JSONObject res = new JSONObject();
        try {
            ComUser user = getComUser(request);
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());
            // 店铺粉丝数量
            Integer fansNum = sfUserRelationService.getFansNumsByShopId(shop.getId(), user.getId());
            // 店铺代言人数量
            Integer spokeManNum = sfUserRelationService.getAllSfSpokesManNum(shop.getId());


            res.put("fansNum", fansNum);
            res.put("spokeManNum", spokeManNum);
        } catch (Exception e) {

        }
        return res.toJSONString();
    }
}
