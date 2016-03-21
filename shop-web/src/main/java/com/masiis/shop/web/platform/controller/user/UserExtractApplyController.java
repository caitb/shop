package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.SysBeanUtils;
import com.masiis.shop.dao.po.ComDictionary;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
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
import java.util.List;

/**
 * Created by lzh on 2016/3/19.
 */
@Controller
@RequestMapping("/extractapply")
public class UserExtractApplyController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

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
        ComUserExtractwayInfo extractwayInfo = null;
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
        }

        String extractMoney = account.getExtractableFee().toString();
        ComDictionary cd = dictionaryService.findByCodeAndKey("COM_USER_EXTRACT_WAY", extractwayInfo.getExtractWay().intValue());
        if(cd == null){
            log.error("系统错误,字典表没查到提现方式!");
        }
        String extractWay = cd.getValue();

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
            res.put("resUrl", "extractapply/success");
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
}
