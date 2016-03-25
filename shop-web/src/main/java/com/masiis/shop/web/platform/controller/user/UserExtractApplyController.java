package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserExtractApplyService;
import com.masiis.shop.web.platform.service.user.UserExtractwayInfoService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.tags.Param;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/19.
 */
@Controller
@RequestMapping("/extract")
public class UserExtractApplyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());
    private final Integer pageSize = 20;

    @Resource
    private UserService userService;
    @Resource
    private UserExtractwayInfoService extractwayInfoService;
    @Resource
    private ComUserAccountService accountService;
    @Resource
    private ComDictionaryService dictionaryService;
    @Resource
    private UserExtractApplyService applyService;

    @RequestMapping("/toapply")
    public String toApply(HttpServletRequest request, Model model){
        ComUser user = getComUser(request);
        if(user == null) {
            user = userService.getUserByOpenid("oUIwkwgLzn8CKMDrvbCSE3T-u5fs");
        }
        ComUserAccount account = accountService.findAccountByUserid(user.getId());
        if(account == null){
            log.error("系统错误,请联系管理员!");
        }
        List<ComUserExtractwayInfo> extractwayInfos = extractwayInfoService.findByUserId(user.getId());
        boolean hasCard = false;
        ComUserExtractwayInfo extractwayInfo = new ComUserExtractwayInfo();
        String extractWay = null;
        if(extractwayInfos != null && extractwayInfos.size() > 0){
            hasCard = true;
            for(ComUserExtractwayInfo info:extractwayInfos){
                if(info.getIsDefault() == 0){
                    extractwayInfo = info;
                }
            }
            if(extractwayInfo == null){
                extractwayInfo = extractwayInfos.get(0);
            }
            ComDictionary cd = dictionaryService.findByCodeAndKey("COM_USER_EXTRACT_WAY", extractwayInfo.getExtractWay().intValue());
            if(cd == null){
                log.error("系统错误,字典表没查到提现方式!");
            }
            extractWay = cd.getValue();
        }

        String extractMoney = account.getExtractableFee().toString();

        model.addAttribute("extractMoney", extractMoney);
        model.addAttribute("extractwayInfo", extractwayInfo);
        model.addAttribute("hasCard", hasCard);
        model.addAttribute("extractWay", extractWay);
        return "platform/user/extract_apply";
    }


    @RequestMapping("/apply")
    @ResponseBody
    public String extractApply(HttpServletRequest request,
                @RequestParam(required = true) String money){
        JSONObject res = new JSONObject();
        BigDecimal exMoney = null;
        ComUser user = null;
        try{
            user = getComUser(request);
            if(user == null){
                throw new BusinessException("该用户未登录");
            }
            if(StringUtils.isBlank(money)){
                log.error("提现金额未填写,用户id:" + user.getId());
                throw new BusinessException("提现金额未填写!");
            }
            if(!SysBeanUtils.isNumeric(money)){
                log.error("金额不是数字类型的值,用户id:" + user.getId());
                throw new BusinessException("金额不是数字类型的值!");
            }
            // 提现金额
            exMoney = new BigDecimal(money);
            // 获取用户资产
            ComUserAccount account = accountService.findAccountByUserid(user.getId());
            if(account == null){
                log.error("系统错误,用户资产未找到");
                throw new BusinessException("系统错误,用户资产未找到");
            }
            // 查询默认的支付方式
            ComUserExtractwayInfo info = extractwayInfoService.findDefaultInfo(user.getId());
            if(info == null){
                throw new BusinessException();
            }

            // 开始提现业务
            applyService.applyExtract(account, exMoney, user, info);
            res.put("resCode", "success");
            res.put("resMsg", "");
            res.put("resUrl", "extract/success");
        } catch (Exception e) {
            log.error("提现申请错误:" + e.getMessage(), e);
            res.put("resCode", "fail");
            res.put("resMsg", e.getMessage());
        }
        return res.toJSONString();
    }

    @RequestMapping("/success")
    public String applySuccess(HttpServletRequest request){

        return "platform/user/extract_success";
    }

    @RequestMapping("/list")
    public String extractList(HttpServletRequest request, Model model){
        ComUser user = null;
        Integer cur = 0;
        /*List<ComUserExtractApply> list = null;
        boolean hasData = true;
        try {
            user = getComUser(request);
            if (user == null) {
                throw new BusinessException("该用户未登录");
            }
            // 展示提现申请记录的时间区间
            Date start = DateUtil.getFirstTimeInMonth(new Date());
            Date end = DateUtil.getLastTimeInMonth(new Date());
            // 根据用户id查询提现记录,查询当前月的提现记录
            Integer count = applyService.findNumsByUserAndDate(user, start, end);
            if(count == null || count <= 0){
                hasData = false;
                throw new BusinessException("暂无数据");
            }
            // 获取总页数
            Integer pageNums = count%pageSize == 0 ? count/pageSize : count/pageSize + 1;
            Integer startNum = (cur - 1) * pageSize;
            Integer qSize = pageSize;
            if(cur == pageNums){
                qSize = count - startNum;
            }

            list = applyService.findListByUserAndDateAndPageNum(user, start, end, startNum, qSize);
        } catch (Exception e) {
            log.error("查询用户提现记录失败:" + e.getMessage());
        }

        model.addAttribute("hasData", hasData);
        model.addAttribute("exList", list);*/
        model.addAttribute("cur", cur);
        return "platform/user/extract_list";
    }

    @RequestMapping("/listmore")
    @ResponseBody
    public String ajaxListMore(HttpServletRequest request,
                                  String time, Integer cur){
        JSONObject res = new JSONObject();
        ComUser user = null;
        try{
            user = getComUser(request);
            if (user == null) {
                throw new BusinessException("该用户未登录");
            }
            if(StringUtils.isBlank(time)
                    || cur == null || cur <= 0){
                throw new BusinessException();
            }
            Date curtime = null;
            try{
                curtime = DateUtil.String2Date(time, "yyyy-MM");
            } catch (Exception e) {
                log.error("时间格式化错误,原时间字符串为:" + time);
                throw new BusinessException("时间格式化错误,原时间字符串为:" + time);
            }
            // 展示提现申请记录的时间区间
            Date start = DateUtil.getFirstTimeInMonth(curtime);
            Date end = DateUtil.getLastTimeInMonth(curtime);
            // 获取时间区间内的总提现数
            Integer count = applyService.findNumsByUserAndDate(user, start, end);
            if(count == null || count <= 0){
                log.error("当前时间区间内没有数据");
                throw new BusinessException("暂无数据");
            }
            log.info("获取时间区间内的总提现数,总记录数:" + count);
            // 获取总页数
            Integer pageNums = count%pageSize == 0 ? count/pageSize : count/pageSize + 1;
            Integer startNum = (cur - 1) * pageSize;
            Integer qSize = pageSize;
            if(cur > pageNums){
                log.error("当前所传页码超过总页码数,总页数:" + pageNums + ",所传页码数:" + cur);
                res.put("isLast", true);
                throw new BusinessException("所传页码超过总页数,无效!");
            }
            if(cur == pageNums){
                qSize = count - startNum;
                res.put("isLast", true);
            }

            List<ComUserExtractApply> list = applyService.findListByUserAndDateAndPageNum(user, start, end, startNum, qSize);
            res.put("resCode", "success");
            res.put("resData", list);
        } catch (Exception e) {
            log.error("" + e.getMessage());
            res.put("resCode", "fail");
            res.put("resMsg", e.getMessage());
        }

        return res.toJSONString();
    }
}
