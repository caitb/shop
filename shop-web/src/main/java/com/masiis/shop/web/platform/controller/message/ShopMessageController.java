package com.masiis.shop.web.platform.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.message.SfMessageDetail;
import com.masiis.shop.dao.mall.message.SfMessageDetailMapper;
import com.masiis.shop.dao.beans.message.SfMessagePageList;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
    private SfMessageDetailMapper detailMapper;

    @RequestMapping("/tolist")
    public String toShopMessageList(){
        return "platform/shop/cluster_message_list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public String messageList(@RequestParam(required = true) Integer cur){
        SfMessagePageList res = new SfMessagePageList();
        try{
            if(cur == null || cur < 0){
                throw new BusinessException("当前页码不正确");
            }
            SfMessageDetail detail = detailMapper.selectByPrimaryKey(1l);
        } catch (Exception e) {

        }
        return JSONObject.toJSONString(res);
    }
}
