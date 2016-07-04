package com.masiis.shop.web.mall.controller.order;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.order.SfShopCartService;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by JingHao on 2016/4/10 0010.
 */
@Controller
@RequestMapping("/cart")
public class SfShopCartController extends BaseController {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private SfShopCartService sfShopCartService;

    @Resource
    private SkuService skuService;

    @Resource
    private SfShopService sfShopService;


    /**
     * jjh
     * 立即购买
     * @param request
     * @param response
     * @param shopId :店铺
     * @param skuId ：商品ID
     * @param quantity:购买数量
     * @param isOwnShip :是否自己发货
     * @return
     */
    @RequestMapping("/addCart.do")
    @ResponseBody
    public String addProductToCart(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value="shopId",required = true) Long shopId,
                                   @RequestParam(value="skuId",required = true) Integer skuId,
                                   @RequestParam(value="quantity",required = true) Integer quantity,
                                   @RequestParam(value="isOwnShip",required = true) Long isOwnShip){
        JSONObject object = new JSONObject();
        try {
            ComUser user = getComUser(request);
//            int useStock = skuService.checkSkuStock(skuId, quantity, shopId);
            SfShop sfShop = sfShopService.getSfShopById(shopId);
            if(sfShop==null){
                throw new BusinessException("不存在该店铺！");
            }
            sfShopCartService.addProductToCart(shopId, user.getId(), skuId, quantity,isOwnShip);
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            object.put("message", ex.getMessage());
            log.error(ex.getMessage());
        }
        return object.toJSONString();
    }
}
