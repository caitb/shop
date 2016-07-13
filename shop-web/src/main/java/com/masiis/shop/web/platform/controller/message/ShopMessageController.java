package com.masiis.shop.web.platform.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.mall.service.message.SfMessageContentService;
import com.masiis.shop.web.mall.service.message.SfMessageSrRelationService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.shop.SfShopSkuService;
import com.masiis.shop.web.mall.service.user.SfUserRelationService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private SfMessageContentService contentService;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SfUserRelationService sfUserRelationService;
    @Resource
    private SfShopSkuService sfShopSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private SfMessageContentService sfMessageContentService;
    @Resource
    private SfMessageSrRelationService sfMessageSrRelationService;

    @RequestMapping("/mycluster.shtml")
    public String toShopMessageList(){
        return "platform/message/shopmessage/cluster_message_list";
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
        return "platform/message/shopmessage/new_shop_cluster_message";
    }

    @RequestMapping("/toNewData.do")
    @ResponseBody
    public String toNewMessageData(HttpServletRequest request){
        JSONObject res = new JSONObject();
        try {
            ComUser user = getComUser(request);
            // 查询是否是代理商
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());
            if(shop == null){
                // 还不是代理商
                res.put("resCode", "fail");
                res.put("resMsg", "该用户还没有店铺");
                return res.toJSONString();
            }
            // 店铺粉丝数量
            Integer fansNum = sfUserRelationService.getFansNumsByShopId(shop.getId(), user.getId());
            // 店铺代言人数量
            Integer spokeManNum = sfUserRelationService.getSfSpokesManNumByShopId(shop.getId(), user.getId());
            // 查询代理商品skuId
            List<SfShopSku> shopSkus = sfShopSkuService.findSfShopOnSaleSkuByShopId(shop.getId());

            res.put("fansNum", fansNum);
            res.put("spokeManNum", spokeManNum);
            res.put("skus", shopSkus);
            res.put("resCode", "success");
        } catch (Exception e) {
            res.put("resCode", "fail");
            res.put("resMsg", "网络错误");
        }
        return res.toJSONString();
    }

    @RequestMapping("/newmessage.do")
    @ResponseBody
    public String newMessage(HttpServletRequest request,
                             @RequestParam(required = true) String message,
                             @RequestParam(required = true) Long urlType,
                             @RequestParam(required = true) Integer sendType){
        JSONObject res = new JSONObject();
        try{
            if(StringUtils.isBlank(message)){
                res.put("resMsg", "消息内容为空");
                throw new BusinessException("消息内容为空");
            }
            if(sendType.intValue() != 1 && sendType.intValue() != 2){
                res.put("resMsg", "发送类型不正确");
                throw new BusinessException("发送类型不正确");
            }

            SfShopSku sku = null;
            String url = null;
            ComUser user = getComUser(request);
            SfShop shop = sfShopService.getSfShopByUserId(user.getId());
            if(urlType.intValue() == -1){}
            else if (urlType.intValue() == 0) {
                url = shop.getId() + "/" + user.getId() + "/shop.shtml";
            }else {
                sku = sfShopSkuService.findShopSkuById(urlType);
                if(sku == null){
                    res.put("resMsg", "链接地址不正确");
                    throw new BusinessException("链接地址不正确");
                }
                url = "shop/detail.shtml" + "?skuId=" + sku.getSkuId()
                        + "&shopId=" + shop.getId() + "&isOwnShip=" + sku.getIsOwnShip();
            }

            List<SfUserRelation> relations = null;
            Integer messageType = 0;
            String remark = "";
            if(sendType.intValue() == 1){
                messageType = 3;
                remark = "店铺群发粉丝";
                relations = sfUserRelationService.findAllFansRelationByShopId(shop.getId(), user.getId());
            } else if(sendType.intValue() == 2){
                messageType = 4;
                remark = "店铺群发代言人";
                relations = sfUserRelationService.findAllSpokeManRelationByShopId(shop.getId(), user.getId());
            }

            SfMessageContent content = sfMessageContentService.createMessageByShopAndType(shop, message, messageType, remark, url);
            sfMessageContentService.insert(content);

            for(SfUserRelation relation:relations){
                SfMessageSrRelation sr = new SfMessageSrRelation();
                sr.setIsSee(0);
                sr.setCreateTime(new Date());
                sr.setRemark(remark);
                sr.setFromUser(user.getId());
                sr.setToUser(relation.getUserId());
                sr.setSfMessageContentId(content.getId());
                sr.setStatus(1);
                sfMessageSrRelationService.insert(sr);
            }

            res.put("resCode", "success");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if(StringUtils.isBlank(res.getString("resMsg"))){
                res.put("resMsg", "网络错误");
            }
            res.put("resCode", "fail");
        }

        return res.toJSONString();
    }

    @RequestMapping("/success.shtml")
    public String createSuccess(){
        return "platform/message/shopmessage/shop_message_create_success";
    }
}
