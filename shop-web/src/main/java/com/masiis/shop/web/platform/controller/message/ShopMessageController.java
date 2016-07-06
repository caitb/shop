package com.masiis.shop.web.platform.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.message.SfMessageDetail;
import com.masiis.shop.dao.mall.message.SfMessageDetailMapper;
import com.masiis.shop.dao.beans.message.SfMessagePageList;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.service.message.SfMessageDetailService;
import com.masiis.shop.web.mall.service.message.SfMessageSrRelationService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @RequestMapping("/tolist")
    public String toShopMessageList(){
        return "platform/shop/cluster_message_list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public String messageList(HttpServletRequest request,
                              @RequestParam(required = true) Integer cur){
        int pageSize = 10;
        SfMessagePageList res = new SfMessagePageList();
        try{
            ComUser user = getComUser(request);
            if(user == null){
                throw new BusinessException("怎么能没用户呢！");
            }
            if(cur == null || cur < 0){
                throw new BusinessException("当前页码不正确");
            }
            long totalNums = srRelationService.countNumsFromUser(user);
            if(totalNums == 0){
                res.setHasData(0);
                throw new BusinessException("暂无数据");
            }
            // 第一页页码是0
            long pageNums = totalNums/pageSize;
            // 页码+1
            cur++;
            // 检测页码是否超出总页数
            if(cur.intValue() + 1 > pageNums){
                throw new BusinessException("下一页码不正确");
            }
            // 检测是否是最后一页
            if(cur.intValue() + 1 == pageNums){
                // 最后一页
                res.setCur(cur);
                res.setIsLast(1);
            }else{
                // 非最后一页
                res.setCur(cur);
                res.setIsLast(0);
            }
            res.setHasData(1);
            res.setPageSize(pageSize);
            res.setTotalPage(pageNums);
            Long start = (long)cur * pageSize;
            // 查询要展现的消息数据
            List<SfMessageDetail> details = sfMessageDetailService.queryMessageDetailFromUser(user, start, pageSize);

        } catch (Exception e) {

        }
        return JSONObject.toJSONString(res);
    }
}
