package com.masiis.shop.web.platform.controller.system;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.BOrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.system.IndexShowService;
import com.masiis.shop.web.platform.service.system.SpuService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Controller
//@RequestMapping("/index")
public class IndexController extends BaseController {

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;
    @Resource
    private SpuService spuService;
    @Resource
    private BOrderService bOrderService;

    @RequestMapping("/index")
    public ModelAndView indexList(HttpServletRequest request)throws Exception{
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
            String url = value + banner.getImgUrl();
            urls.add(url);
        }
        ModelAndView modelAndView = new ModelAndView();
        //封装图片地址集合
        modelAndView.addObject("urls",urls);

        //获取商品图片地址常量
        String skuValue = PropertiesUtils.getStringValue("index_product_800_800_url");
        //获取主页展示商品信息
        List<IndexComSku> indexComS = indexShowService.findIndexComSku();
        List<IndexComSku> ComS =new ArrayList<IndexComSku>();
        for (IndexComSku indexCom:indexComS) {
//            ComSpu comSpu = spuService.getSpuById(indexCom.getComSku().getSpuId());
            //获取商品图片地址
            String url = skuValue + indexCom.getImgUrl();
            //重新封装商品图片地址
            indexCom.setImgUrl(url);
            if(user!=null && user.getIsAgent()==1){
                //判断会员权限
                indexCom.setIsPartner(1);
                //确定代理权限，显示优惠区间
                indexCom.setDiscountLevel(productService.getDiscountByAgentLevel(indexCom.getComSku().getPriceRetail()));
            }else{
                indexCom.setDiscountLevel("成为合伙人可查看利润");
            }
            PfUserSku pfUserSku = bOrderService.findPfUserSku(user.getId(),indexCom.getId());
            if (pfUserSku !=null){
                indexCom.setIsPay(pfUserSku.getIsPay());
            }
            ComS.add(indexCom);
        }
        //封装展示商品信息集合
        modelAndView.addObject("indexComS",ComS);
        modelAndView.addObject("ComSize",ComS.size());
        modelAndView.setViewName("index");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
