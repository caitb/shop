package com.masiis.shop.web.platform.controller.message;

import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/tolist")
    public String toShopMessageList(){
        return "platform/shop/cluster_message_list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public String messageList(@RequestParam Integer cur){
        try{

        } catch (Exception e) {

        }
        return "";
    }
}
