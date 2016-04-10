package com.masiis.shop.web.mall.controller.shop;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.mallBeans.SkuInfo;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComSkuImage;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.web.mall.controller.base.BaseController;
import com.masiis.shop.web.mall.service.product.SkuService;
import com.masiis.shop.web.mall.service.shop.SfShopService;
import com.masiis.shop.web.mall.service.user.UserService;
import com.masiis.shop.web.mall.utils.DownloadImage;
import com.masiis.shop.web.mall.utils.qrcode.CreateParseCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

/**
 * @Date:2016/4/7
 * @auth:lzh
 */
@Controller
@RequestMapping("/shop")
public class SfShopController extends BaseController {

    private Log log = LogFactory.getLog(SfShopController.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopService sfShopService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("mall/shop/index");

        return mav;
    }

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

        return false;
    }

    @RequestMapping("/getPoster")
    public ModelAndView getPoster(HttpServletRequest request, HttpServletResponse response, Long shopId){
        ModelAndView mav = new ModelAndView("mall/shop/exclusivePoster");

        try {
            ComUser comUser = getComUser(request);
                    comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            String realPath = request.getServletContext().getRealPath("/");
            String posterName = comUser.getId() + ".jpg";

            File posterDir = new File(realPath + "static/images/shop/poster/");
            if(!posterDir.exists()) posterDir.mkdirs();

            CreateParseCode.createCode(200, 200, "https://www.tmall.com/", posterDir.getAbsolutePath()+"/"+posterName);
            DownloadImage.download(comUser.getWxHeadImg(), "h-"+comUser.getId()+".jpg", posterDir.getAbsolutePath());

            mav.addObject("shopQRCode", "static/images/shop/poster/"+posterName);
            mav.addObject("userImg", "static/images/shop/poster/h-"+comUser.getId()+".jpg");
            mav.addObject("userName", comUser.getWxNkName());
            mav.addObject("bgShop", "static/images/shop/background-img/bg-shop.png");
            return mav;
        } catch (Exception e) {
            log.error("获取专属海报失败![shopId=" + shopId + "][comUser=" + getComUser(request) + "]");
            e.printStackTrace();
        }

        mav.setViewName("error");
        return mav;
    }

    /**
     * @Author jjh
     * @Date 2016/4/8 0008 下午 5:50
     * 商品详情
     */
    @RequestMapping("/detail.shtml")
    public ModelAndView getSkuDetail(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(value="skuId",required = true) Integer skuId,
                                     @RequestParam(value="shopId",required = true) Long shopId,
                                     @RequestParam(value="fromUserId",required = false) Long fromUserId) throws Exception {
        SkuInfo skuInfo = skuService.getSkuInfoBySkuId(1L, skuId);
        List<ComSkuImage> comSkuImageList =  skuService.findComSkuImages(skuId);
        ComSkuImage comSkuImage = skuService.findDefaultComSkuImage(skuId);
        ComUser fromUser = userService.getShareUser(fromUserId);//来自分享人的信息
        ModelAndView mav = new ModelAndView("/mall/shop/shop_product");
        mav.addObject("skuInfo", skuInfo);//商品信息
        mav.addObject("SkuImageList", comSkuImageList);//图片列表
        mav.addObject("defaultSkuImage", comSkuImage);//默认图片
        mav.addObject("shopId", shopId);
        mav.addObject("fromUser", fromUser);//分享链接人信息
        return mav;
    }

    /**
     * @Author jjh
     * @Date 2016/4/9 0009 下午 1:45
     * 立即购买
     */
    @RequestMapping("/addCart.do")
    @ResponseBody
    public String addProductToCart(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(required = true) Long shopId,
                                   @RequestParam(required = true) Integer skuId,
                                   @RequestParam(required = true) Integer quantity){
        JSONObject object = new JSONObject();
        try{
            HttpSession session = request.getSession();
            ComUser comUser = (ComUser) session.getAttribute("comUser");
            skuService.addProductToCart(1L,1L,skuId,quantity);
            object.put("isError", false);
        }
        catch (Exception ex){
            object.put("isError", true);
            object.put("message", ex.getMessage());
            log.error(ex.getMessage());
        }
        return object.toJSONString();
    }
}
