package com.masiis.shop.api.controller.shop;

import com.masiis.shop.api.bean.shop.DistributionRecordReq;
import com.masiis.shop.api.bean.shop.IndexShopRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.order.SfOrderMapper;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserShopViewMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by cai_tb on 16/4/13.
 */
@Controller
@RequestMapping("shop/manage")
public class SfShopManageController extends BaseController {

    private final static Log log = LogFactory.getLog(SfShopManageController.class);

    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfOrderMapper sfOrderMapper;
    @Resource
    private SfUserShopViewMapper sfUserShopViewMapper;

    /**
     * 店铺管理首页
     * @param request
     * @param req
     * @return
     */
    @RequestMapping("/index")
    @ResponseBody
    @SignValid(paramType=DistributionRecordReq.class)
    public IndexShopRes index(HttpServletRequest request, DistributionRecordReq req, ComUser comUser){
        IndexShopRes res = new IndexShopRes();
        SfShop sfShop = null;
        try {
            sfShop = sfShopMapper.selectByUserId(comUser.getId());
            res.setSfShop(sfShop);
            Integer orderCount = sfOrderMapper.countByShopId(sfShop.getId()); //总订单数
            res.setOrderCount(orderCount);
            Integer shopView = sfUserShopViewMapper.countByShopId(sfShop.getId()); //店铺浏览量
            res.setShopView(shopView);
            String shopUrl = PropertiesUtils.getStringValue("mall.domain.name.address") + "/" + sfShop.getId()+"/"+comUser.getId()+"/shop.shtml";
            res.setShopUrl(shopUrl);
            res.setComUser(comUser);
            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception ex) {
            log.error("加载店铺首页数据失败![comUser="+comUser+"][sfShop="+sfShop+"]");
            if (StringUtils.isNotBlank(ex.getMessage())){
                res.setResMsg(ex.getMessage());
            } else {
                res.setResMsg("店铺首页");
            }
        }
        return res;
    }
}
