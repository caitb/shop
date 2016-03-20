package com.masiis.shop.web.platform.controller.user;

import com.masiis.shop.dao.po.ComDictionary;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.system.ComDictionaryService;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import com.masiis.shop.web.platform.service.user.UserExtractwayInfoService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
        String extractway = cd.getValue();

        model.addAttribute("extractMoney", extractMoney);
        model.addAttribute("extractwayInfo", extractwayInfo);
        model.addAttribute("hasCard", hasCard);
        model.addAttribute("extractway", extractway);
        return "platform/user/extract_apply";
    }
}
