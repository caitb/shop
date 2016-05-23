package com.masiis.shop.api.controller.product;

import com.masiis.shop.api.bean.common.CommonReq;
import com.masiis.shop.api.bean.product.ApplyProReq;
import com.masiis.shop.api.bean.product.ApplyProRes;
import com.masiis.shop.api.bean.product.ProDetailRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysConstants;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderAddService;
import com.masiis.shop.api.service.product.ProductService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.user.UserAddressService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by JingHao on 2016/5/19 0019.
 */


@Controller
@RequestMapping("/managePro")
public class ManageProController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ProductService productService;
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private BOrderAddService bOrderAddService;
    /**
      * @Author jjh
      * @Date 2016/5/19 0019 下午 5:49
      * 管理商品列表
      */
    @RequestMapping("/user")
    @ResponseBody
    @SignValid(paramType = CommonReq.class)
    public ProDetailRes proListForUser(HttpServletRequest request, CommonReq req, ComUser user) {
        ProDetailRes proDetailRes = new ProDetailRes();
        try {
            List<Product> userProducts = productService.productListByUser(user.getId());
            if (userProducts != null) {
                for (Product product : userProducts) {
                    product.setUpperStock(productService.getUpperStock(user.getId(), product.getId()));
                }
            }
            proDetailRes.setProductList(userProducts);
            proDetailRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            proDetailRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            e.printStackTrace();
            proDetailRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            proDetailRes.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        return proDetailRes;
    }

    /**
      * @Author jjh
      * @Date 2016/5/19 0019 下午 5:49
      * 申请拿货信息展示
      */
    @RequestMapping("/applyInfo")
    @ResponseBody
    @SignValid(paramType = ApplyProReq.class)
    public ApplyProRes applyProInfo(HttpServletRequest request, ApplyProReq req,
                                    ComUser user) {
        ApplyProRes applyProRes = new ApplyProRes();
        try {
            ComUserAddress comUserAddress = userAddressService.getOrderAddress(req.getSelectedAddressId(), user.getId());
            if (comUserAddress != null) {
                applyProRes.setComUserAddress(comUserAddress);
            }
            PfUserSkuStock pfUserSkuStock = productService.getStockByUser(req.getId());
            int userStock = pfUserSkuStock.getStock() - pfUserSkuStock.getFrozenStock();
            if(userStock<0){
                pfUserSkuStock.setStock(0);
            }else{
                pfUserSkuStock.setStock(userStock);//冻结库存
            }
            ComSku comSku = skuService.getSkuById(pfUserSkuStock.getSkuId());
            ComSkuImage comSkuImage = skuService.findComSkuImage(comSku.getId());
            String productImgValue = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), pfUserSkuStock.getSkuId());
            Map<String, Object> objectMap = productService.getLowerCount(pfUserSkuStock.getSkuId(), req.getStock(), pfUserSku.getAgentLevelId());
            applyProRes.setComSku(comSku);
            applyProRes.setComSkuImg(productImgValue + comSkuImage.getImgUrl());
            applyProRes.setPfUserSkuStock(pfUserSkuStock);
            applyProRes.setLowerCount(objectMap.get("countLevel").toString());
            applyProRes.setPriceDiscount(objectMap.get("priceDiscount").toString());
            applyProRes.setLevelStock(objectMap.get("levelStock").toString());
            applyProRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            applyProRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            applyProRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            applyProRes.setResMsg(e.getMessage());

        }
        return applyProRes;
    }


    /**
      * @Author jjh
      * @Date 2016/5/23 0023 下午 4:03
      * 申请拿货生成订单
      */
    @RequestMapping("/applyStock.do")
    @ResponseBody
    @SignValid(paramType = ApplyProReq.class)
    public ApplyProRes applySkuStock(HttpServletRequest request, ApplyProReq req,
                                     ComUser user){
        ApplyProRes applyProRes = new ApplyProRes();
        try{
            PfUserSkuStock product = productService.getStockByUser(req.getId());
            Long orderCode = bOrderAddService.addProductTake(user.getId(), product.getSkuId(), req.getStock(), req.getMessage(), req.getSelectedAddressId());
            applyProRes.setOrderCode(orderCode);
            applyProRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            applyProRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            applyProRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            applyProRes.setResMsg(e.getMessage());
        }
         return applyProRes;
    }

}
