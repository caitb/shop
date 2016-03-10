package com.masiis.shop.web.platform.controller.order;

import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.PfCorder;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Controller
@RequestMapping("/corder")
public class COrderController extends BaseController {

    @Resource
    private COrderService cOrderService;
    @Resource
    private ProductService productService;

    /**
     *
     *@return
     */
    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("userId","1");
        return "index";
    }
    /**
     * 跳转到使用申请成功界面
     * @author  hanzengzhi
     * @date  2016/3/7 19:34
     */
    @RequestMapping("/continueStroll")
    public String toContinueStroll(HttpServletRequest request, HttpServletResponse response){
        return "platform/order/jixuguangguang";
    }
    /**
     * 验证商品是否使用过
     * @author  hanzengzhi
     * @date  2016/3/9 11:10
     */
    @RequestMapping("/isApplyTrial.do")
    @ResponseBody
    public Boolean isApplyTrial(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "skuId", required = true) Integer skuId) {
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser != null) {
            userId = comUser.getId();
        } else {
            userId = 1L;
        }
        if (StringUtils.isEmpty(userId)) {
            userId = 1L;
        }
        if (StringUtils.isEmpty(skuId)) {
            skuId = 1;
        }
        Boolean bl = cOrderService.isApplyTrial(userId, skuId);
        return bl;
    }
    /**
     * 跳转到试用申请界面
     * @author  hanzengzhi
     * @date  2016/3/5 13:45
     */
    @RequestMapping("/applyTrialToPage.json")
    public String applyTrialToPage(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(value = "skuId", required = true) Integer skuId,
                                   Model model)throws Exception{
        if (StringUtils.isEmpty(skuId)){
            skuId = 1;
        }
        Product productDetails = productService.applyTrialToPageService(skuId);
        String skuImg = PropertiesUtils.getStringValue("index_product_220_220_url");
        model.addAttribute("skuName", productDetails.getName());
        if (productDetails.getComSkuImages()!=null&&productDetails.getComSkuImages().size()>0){
            model.addAttribute("skuDefaultImg",skuImg + productDetails.getComSkuImages().get(0).getImgUrl());
            model.addAttribute("skuImgAlt", productDetails.getComSkuImages().get(0).getImgName());
        }
        model.addAttribute("product",productDetails);
        return "platform/order/shiyong";
    }
    /**
     * 试用申请
     * @author  hanzengzhi
     * @date  2016/3/5 13:46
     */
    @RequestMapping("/trialApply.do")
    @ResponseBody
    public String trialApply(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "skuId", required = true) Integer skuId,
            @RequestParam(value = "spuId", required = true) Integer spuId,
            @RequestParam(value = "applyReason", required = false) String applyReason,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "wechat", required = true) String wechat
    ) {
        if (StringUtils.isEmpty(skuId)){
            skuId = 111;
        }
        if (StringUtils.isEmpty(spuId)){
            spuId = 222;
        }
        if (StringUtils.isEmpty(name)){

        }
        if (StringUtils.isEmpty(phone)){

        }
        if (StringUtils.isEmpty(wechat)){

        }
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        if (comUser==null){
            comUser = new ComUser();
            comUser.setId(1L);
        }
        comUser.setWxId(wechat);
        comUser.setRealName(name);
        comUser.setMobile(phone);

        if (comUser.getCreateTime()==null){
            comUser.setCreateTime(new Date());
        }
        if (StringUtils.isEmpty(comUser.getOpenid())){
            comUser.setOpenid("openid");
        }
        if (StringUtils.isEmpty(comUser.getAccessToken())){
            comUser.setAccessToken("accessToken");
        }
        if (StringUtils.isEmpty(comUser.getRefreshToken())){
            comUser.setRefreshToken("refreshToken");
        }
        if (StringUtils.isEmpty(comUser.getAtokenExpire())){
            comUser.setAtokenExpire(new Date());
        }
        if (StringUtils.isEmpty(comUser.getRtokenExpire())){
            comUser.setRtokenExpire(new Date());
        }

        PfUserTrial pfUserTrial = new PfUserTrial();
        pfUserTrial.setUserId(comUser.getId());
        pfUserTrial.setSkuId(skuId);
        pfUserTrial.setSpuId(spuId);
        pfUserTrial.setStatus(0);
        pfUserTrial.setReason(applyReason);
        pfUserTrial.setName(name);
        pfUserTrial.setMobile(phone);
        pfUserTrial.setWeixinId(wechat);
        pfUserTrial.setCreateTime(new Date());
        cOrderService.trialApplyService(comUser,pfUserTrial);
        return "success";
    }

    /**
     * 确认订单
     * @author  hanzengzhi
     * @date  2016/3/8 10:16
     */
    @RequestMapping("/confirmOrder.do")
    public String confirmOrder(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "orderId", required = false) Long orderId,
                                @RequestParam(value = "selectedAddressId", required = false) Integer selectedAddressId,
                                Model model){
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser!=null){
            userId = comUser.getId();
        }else{
            userId = 1L;
        }
        if (orderId==null){
            orderId = 1L;
        }
        Map<String,Object> pfCorderMap = cOrderService.confirmOrder(orderId,userId,selectedAddressId);
        List<ComUserAddress> comuserAddressList = (List<ComUserAddress>)pfCorderMap.get("address");
        //地址
        if (comuserAddressList!=null&&comuserAddressList.size()>0){
            model.addAttribute("comUserAddress",comuserAddressList.get(0));
        }
        model.addAttribute("pfCorderId",1L);
/*        List<PfCorder> pfCorders = (List<PfCorder>)pfCorderMap.get("pfCorder");
        if (pfCorders!=null&&pfCorders.size()>0){
            model.addAttribute("pfCorder",pfCorders.get(0));
        }
        //图片
        Product product = (Product)pfCorderMap.get("product");
        String skuImg = PropertiesUtils.getStringValue("index_product_100_100_url");
        model.addAttribute("skuName", product.getName());
        if (product.getComSkuImages()!=null&&product.getComSkuImages().size()>0){
            model.addAttribute("skuDefaultImg",skuImg + product.getComSkuImages().get(0).getImgUrl());
            model.addAttribute("skuImgAlt", product.getComSkuImages().get(0).getImgName());
        }
        model.addAttribute("product",product);*/
        return "platform/order/zhifushiyong";
    }

}
