package com.masiis.shop.web.mall.controller.message;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.message.SfMessageContentService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by huangwei on 2016/7/12.
 */
@Controller
@RequestMapping(value = "mallmessage")
public class MallMessageController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfMessageContentService contentService;

    @RequestMapping("/toMessageCenter.shtml")
    public String toMessageCenter(HttpServletRequest request, Model model){
        ComUser user = getComUser(request);
        try{
            if(user == null){
                throw new BusinessException("怎么能没用户呢！");
            }
            Long userId = user.getId();
            List<Map<String, String>> messageList = contentService.queryUnreadShopInfosByUser(userId);
            model.addAttribute("messageList", messageList);
        }catch(Exception e){
            log.error("查询mall粉丝消息失败![userId=" + user.getId() + "]" + e);
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }
        return "mall/message/messageCenter";
    }


}
