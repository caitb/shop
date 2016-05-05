package com.masiis.shop.api.controller.order;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.order.OManagementIndexReq;
import com.masiis.shop.api.bean.order.OManagementIndexRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfBorder;
import com.sun.org.glassfish.external.statistics.annotations.Reset;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2016/5/5
 * @Auther lzh
 */
@Controller
@RequestMapping("/om")
public class BOrderManagementController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private BOrderService bOrderService;

    @RequestMapping("/index")
    @ResponseBody
    @SignValid(paramType = OManagementIndexReq.class)
    public OManagementIndexRes bOrderManagement(HttpServletRequest request, OManagementIndexReq req, ComUser user){
        OManagementIndexRes res = new OManagementIndexRes();

        List<PfBorder> pfBorders = bOrderService.findByUserId(user.getId(), null, null);
        List<PfBorder> pfBorderps = bOrderService.findByUserPid(user.getId(), null, null);
        List<PfBorder> pfBorders0 = new ArrayList<>();
        List<PfBorder> pfBorders7 = new ArrayList<>();//代发货
        List<PfBorder> pfBorders8 = new ArrayList<>();//待收货
        List<PfBorder> pfBorders6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorders) {
            if (pfBord.getOrderStatus() == 0) {
                pfBorders0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 7 ) {
                pfBorders7.add(pfBord);//代发货
            } else if (pfBord.getOrderStatus() == 8 ) {
                pfBorders8.add(pfBord);//待收货
            }  else if (pfBord.getOrderStatus() == 6) {
                pfBorders6.add(pfBord);//排单中
            }
        }
        List<PfBorder> pfBorderp0 = new ArrayList<>();
        List<PfBorder> pfBorderp7 = new ArrayList<>();//代发货
        List<PfBorder> pfBorderp8 = new ArrayList<>();//待收货
        List<PfBorder> pfBorderp6 = new ArrayList<>();//排单中
        for (PfBorder pfBord : pfBorderps) {
            if (pfBord.getOrderStatus() == 0) {
                pfBorderp0.add(pfBord);//待付款
            } else if (pfBord.getOrderStatus() == 8 ) {
                pfBorderp8.add(pfBord);//待收货
            }  else if (pfBord.getOrderStatus() == 6) {
                pfBorderp6.add(pfBord);//排单中
            }else if (pfBord.getOrderStatus() == 7 ) {
                pfBorderp7.add(pfBord);//代发货
            }
        }
        res.setInWaitShipNum(pfBorders7.size());
        res.setInMPSNum(pfBorders6.size());
        res.setInPayingNum(pfBorders0.size());
        res.setInReceiveNum(pfBorders8.size());
        res.setOutShipNum(pfBorderp7.size());
        res.setOutMPSNum(pfBorderp6.size());
        res.setOutWaitPayNum(pfBorderp0.size());
        res.setOutWaitReceiveNum(pfBorderp8.size());
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        return res;
    }

}
