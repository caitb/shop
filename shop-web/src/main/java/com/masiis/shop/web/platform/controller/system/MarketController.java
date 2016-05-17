package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
@RequestMapping("/marketGood")
public class MarketController extends BaseController {

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private BOrderService bOrderService;

    @RequestMapping("/market")
    public ModelAndView marketList(HttpServletRequest request)throws Exception{
        ComUser user = getComUser(request);
        if (user == null) {
            user = userService.getUserById(1l);
            request.getSession().setAttribute("comUser", user);
        }
        //获取图片地址常量
        String value = PropertiesUtils.getStringValue("index_banner_url");
        //获取轮播图片
        List<PbBanner> pbBanner = indexShowService.findPbBanner();
        List<String> urls = new ArrayList<>();
        for (PbBanner banner:pbBanner) {
            //图片地址
            banner.setImgUrl(value + banner.getImgUrl());
//            String url = value + banner.getImgUrl();
//            urls.add(url);
        }
        ModelAndView modelAndView = new ModelAndView();
        //封装图片地址集合
        modelAndView.addObject("pbBanner",pbBanner);

        //获取商品图片地址常量
        String skuValue = PropertiesUtils.getStringValue("index_product_800_800_url");
        //获取主页展示商品信息
        List<IndexComSku> indexComS = indexShowService.findIndexComSku(user.getId());
        List<IndexComSku> ComS =new ArrayList<IndexComSku>();
        for (IndexComSku indexCom:indexComS) {
//            ComSpu comSpu = spuService.getSpuById(indexCom.getComSku().getSpuId());
            //获取商品图片地址
            String url = skuValue + indexCom.getImgUrl();
            //重新封装商品图片地址
            indexCom.setImgUrl(url);
//            if(user!=null && user.getIsAgent()==1) {
                //判断会员权限
                indexCom.setIsPartner(1);
                //确定代理权限，显示优惠区间
                indexCom.setMaxDiscount(productService.getMaxDiscount(indexCom.getSkuId()));
                indexCom.setDiscountLevel("最高利润"+productService.getMaxDiscount(indexCom.getSkuId())+"%");
                indexCom.setBailLevel(skuAgentService.getSkuAgentLevel(indexCom.getSkuId()));//保证金
//            }else{
//                indexCom.setDiscountLevel("成为合伙人可查看利润");
//            }
            PfUserSku pfUserSku = bOrderService.findPfUserSku(user.getId(),indexCom.getId());
            if (pfUserSku !=null){
                indexCom.setIsPay(pfUserSku.getIsPay());
            }
            ComS.add(indexCom);
        }
        //封装展示商品信息集合
        modelAndView.addObject("indexComS",ComS);
        modelAndView.addObject("ComSize",ComS.size());
        modelAndView.setViewName("platform/system/haohuo");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
