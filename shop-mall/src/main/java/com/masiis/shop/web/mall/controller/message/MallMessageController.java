package com.masiis.shop.web.mall.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.message.SfMessageContentService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String toMessageCenter(Model model){
        //model.addAttribute("cur", 0);
        return "mall/message/messageCenter";
    }

    @RequestMapping("/shopMsgList.shtml")
    @ResponseBody
    public String shopMsgList(@RequestParam(required = true) Integer cur, HttpServletRequest request){
        JSONObject res = new JSONObject();
        int pageSize = 10;
        ComUser user = getComUser(request);
        try{
            if(user == null){
                throw new BusinessException("怎么能没用户呢！");
            }
            if(cur == null || cur < 0){
                throw new BusinessException("当前页码不正确");
            }
            Long userId = user.getId();
            int totalNums = contentService.queryShopNums(userId);
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
            res.put("resCode", "success");
            //res.put("cur", cur+1);
            res.put("hasData", true);
            int start = cur * pageSize;
            List<Map<String, String>> messageList = contentService.queryUnreadShopInfosByUser(userId, start, pageSize);
            System.out.println("------------------" + messageList);
            res.put("messageList", messageList);
        }catch(Exception e){
            log.error("查询mall粉丝消息失败![userId=" + user.getId() + "]" + e);
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }
        return res.toJSONString();
    }

}
