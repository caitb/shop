package com.masiis.shop.web.mall.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.product.SkuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@RequestMapping("/shop")
public class SfShopController extends BaseController {

    private Log log = LogFactory.getLog(SfShopController.class);

    @Resource
    private SfShopService sfShopService;

    @Resource
    private SkuService skuService;

    /**
     * 呐喊
     * @param request
     * @param response
     * @param shopId
     * @return
     */
    @RequestMapping("/shout")
    @ResponseBody
    public Object shout(HttpServletRequest request, HttpServletResponse response, Long shopId){

        try {
            ComUser comUser = getComUser(request);

            boolean result = sfShopService.shout(shopId, comUser.getId());

            return result;
        } catch (Exception e) {
            log.error("呐喊失败![shopId="+shopId+"][comUser="+getComUser(request)+"]");
            e.printStackTrace();
        }

        return "error";
    }

    /**
     * @Author jjh
     * @Date 2016/4/8 0008 下午 5:50
     * 商品详情
     */
    @RequestMapping("/detail.htmls")
    public ModelAndView getSkuDetail(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam("skuId") Integer skuId,
                                     @RequestParam("shopId") Long shopId) throws Exception {
        ModelAndView mav = new ModelAndView("/mall/shop/shop_product");
        HttpSession session = request.getSession();
        SkuInfo skuInfo = skuService.getSkuInfoBySkuId(1L, skuId);
        List<ComSkuImage> comSkuImageList =  skuService.findComSkuImages(skuId);
        ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(skuId);
        mav.addObject("skuInfo", skuInfo);//商品信息
        mav.addObject("SkuImageList", comSkuImageList);//图片列表
        mav.addObject("defaultSkuImage", comSkuImage);//默认图片
        return mav;
    }
}
