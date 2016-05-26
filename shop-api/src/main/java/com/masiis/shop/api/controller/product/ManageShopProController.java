package com.masiis.shop.api.controller.product;

import com.masiis.shop.api.bean.product.ShopProReq;
import com.masiis.shop.api.bean.product.ShopProRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.product.ManageShopProductService;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by JingHao on 2016/5/26 0026.
 */

@Controller
@RequestMapping("/shopPro")
public class ManageShopProController extends BaseController{

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ManageShopProductService manageShopProductService;

    /**
      * @Author jjh
      * @Date 2016/5/26 0026 下午 3:16
      * 小铺中商品的管理列表
      */
    @RequestMapping("/user")
    @ResponseBody
    @SignValid(paramType = ShopProReq.class)
    public ShopProRes manageShopProduct(HttpServletRequest request, ShopProReq req, ComUser user){
        ShopProRes shopProRe = new ShopProRes();
        try {
            List<SkuInfo> skuInfoList = manageShopProductService.getShopProductsList(req.getShopId(), req.getIsSale(), user.getId());
            shopProRe.setSkuInfoList(skuInfoList);
            shopProRe.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            shopProRe.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            shopProRe.setShopId(req.getShopId());
        }catch (Exception e){
            shopProRe.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            shopProRe.setResMsg(e.getMessage());
        }
      return shopProRe;
    }

}
