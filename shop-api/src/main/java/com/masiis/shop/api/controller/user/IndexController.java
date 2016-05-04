package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.user.PartnerIndexReq;
import com.masiis.shop.api.bean.user.PartnerIndexRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.shop.IndexShowService;
import com.masiis.shop.api.service.user.ComUserAccountService;
import com.masiis.shop.api.service.user.ComUserService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2016/4/28
 * @Auther lzh
 */
@Controller
@RequestMapping("/user")
public class IndexController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private ComUserAccountService comUserAccountService;
    @Resource
    private ComUserService userService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderService bOrderService;


    @RequestMapping("/index")
    @ResponseBody
    @SignValid(paramType = PartnerIndexReq.class)
    public PartnerIndexRes toIndex(HttpServletRequest request, PartnerIndexReq req, ComUser user){
        PartnerIndexRes res = new PartnerIndexRes();
        try {
            List<String> urls = new ArrayList<>();
            String value = PropertiesUtils.getStringValue("index_banner_url");//获取图片地址常量
            List<PbBanner> pbBanner = indexShowService.findPbBanner();//获取轮播图片
            for (PbBanner banner : pbBanner) {
                String url = value + banner.getImgUrl();//图片地址
                urls.add(url);
            }

            if(user.getIsAgent().intValue() == 1){
                // 是合伙人
                ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(user.getId());
                if (comUserAccount == null) {
                    throw new BusinessException("comUserAccount 统计为空");
                }
                Long num = 0l;
                List<PfUserSku> agentNum = userSkuService.getAgentNumByUserId(user.getId());
                if (agentNum != null) {
                    for (PfUserSku pfUserSku : agentNum) {
                        if (pfUserSku != null && pfUserSku.getAgentNum() != null) {
                            num = num + pfUserSku.getAgentNum();
                        }
                    }
                }

                res.setPartnerNums(num.intValue());
                res.setTotalIncomeFee(comUserAccount.getTotalIncomeFee()
                        .setScale(2, RoundingMode.HALF_UP).toString());
                res.setProfitFee(comUserAccount.getProfitFee().setScale(2, RoundingMode.HALF_UP).toString());
            }
            List<PfBorder> pfBorders = bOrderService.findByUserPid(user.getId(), null, null);
            List<PfBorder> pfBorders10 = new ArrayList<>();//代发货
            List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
            for (PfBorder pfBord : pfBorders) {
                if (pfBord.getOrderStatus() == 7) {
                    pfBorders10.add(pfBord);//代发货
                } else if (pfBord.getOrderStatus() == 6) {
                    pfBorders6.add(pfBord);//排单中
                }
            }
            Integer borderNum = pfBorders10.size() + pfBorders6.size();
            // 绑定数据
            res.setbOrderNums(borderNum);
            res.setImgs(urls);
            res.setIsPartner(user.getIsAgent());
            res.setIsBind(user.getIsBinding());
            res.setNkName(user.getWxNkName());
            res.setUserHeadImg(user.getWxHeadImg());
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }
}
