package com.masiis.shop.web.platform.controller.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.message.PfMessageCenterDetail;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfMessageContent;
import com.masiis.shop.web.common.service.UserService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.message.PfMessageContentService;
import com.masiis.shop.web.platform.service.message.PfMessageSrRelationService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2016/7/12
 * @Author lzh
 */
@Controller
@RequestMapping("/message")
public class PFMessageController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfMessageContentService pfMessageContentService;
    @Resource
    private PfMessageSrRelationService srRelationService;
    @Resource
    private UserService userService;

    @RequestMapping("/center.shtml")
    public String toCenter(){
        return "platform/message/pf_message/message_center_platform";
    }

    @RequestMapping("/centerlist.do")
    @ResponseBody
    public String centerList(HttpServletRequest request,
                             @RequestParam(required = true) Integer cur){
        JSONObject res = new JSONObject();
        List<PfMessageCenterDetail> resData = null;
        int pageSize = 10;
        try{
            ComUser user = getComUser(request);
            if(user == null){
                throw new BusinessException("怎么能没用户呢！");
            }
            if(cur == null || cur < 0){
                throw new BusinessException("当前页码不正确");
            }
            int totalNums = srRelationService.queryNumsToUser(user, 2);
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
            res.put("cur", cur);
            res.put("hasData", true);
            res.put("pageSize", pageSize);
            res.put("totalPage",pageNums);
            int start = cur * pageSize;
            // 查询要展现的消息数据
            resData = srRelationService.queryFromUsersByToUserWithPaging(user, 2, start, pageSize);
            res.put("data", resData);
        } catch (Exception e) {
            String resMsg = res.getString("resMsg");
            if(StringUtils.isBlank(resMsg)){
                res.put("resMsg", "网络错误");
            }
            res.put("resCode", "fail");
        }

        return JSONObject.toJSONStringWithDateFormat(res, "yyyy-MM-dd HH:mm:ss");
    }

    @RequestMapping("/detail.shtml")
    public String detail(@RequestParam(required = true) Long userId,
                         Model model){
        model.addAttribute("userId", userId);
        return "platform/message/pf_message/message_detail";
    }

    @RequestMapping("/detaillist.do")
    @ResponseBody
    public String detailList(HttpServletRequest request,
                             @RequestParam(required = true) Integer cur,
                             @RequestParam(required = true) Long uid){
        JSONObject res = new JSONObject();
        List<PfMessageContent> resData = null;
        int pageSize = 10;
        try{
            ComUser user = getComUser(request);
            if(user == null){
                throw new BusinessException("怎么能没用户呢！");
            }
            if(cur == null || cur < 0){
                throw new BusinessException("当前页码不正确");
            }
            if(uid == null){
                log.error("消息来源用户为空");
                res.put("resMsg", "消息来源用户为空");
                throw new BusinessException("消息来源用户为空");
            }
            ComUser fUser = userService.getUserById(uid);
            if(fUser == null){
                log.error("消息来源用户找不到");
                res.put("resMsg", "消息来源用户找不到");
                throw new BusinessException("消息来源用户找不到");
            }
            int totalNums = srRelationService.queryNumsFromUserAndToUser(user, fUser.getId(), 2);
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
            res.put("cur", cur);
            res.put("hasData", true);
            res.put("pageSize", pageSize);
            res.put("totalPage",pageNums);
            res.put("fromUserName", fUser.getRealName());
            int start = cur * pageSize;
            // 查询要展现的消息数据
            resData = srRelationService.queryDetailByFromUserAndToUserWithPaging(user.getId(), fUser.getId(), 2, start, pageSize);
            res.put("data", resData);
        } catch (Exception e) {
            String resMsg = res.getString("resMsg");
            if(StringUtils.isBlank(resMsg)){
                res.put("resMsg", "网络错误");
            }
            res.put("resCode", "fail");
        }

        return JSON.toJSONStringWithDateFormat(res, "yyyy-MM-dd HH:mm:ss");
    }
}
