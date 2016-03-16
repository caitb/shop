package com.masiis.shop.web.platform.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.order.PfUserTrialService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Resource
    private UserService userService;
    @Resource
    private PfUserTrialService trialService;


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
    public String isApplyTrial(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(value = "skuId", required = true) Integer skuId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
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
        List<PfUserTrial> pfUserTrials= cOrderService.isApplyTrial(userId, skuId);
        String returnJson = objectMapper.writeValueAsString(pfUserTrials);
        return returnJson;
    }
    /**
     * 跳转到试用申请界面
     * @author  hanzengzhi
     * @date  2016/3/5 13:45
     */
    @RequestMapping("/applyTrialToPage.do")
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
    public Long trialApply(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "skuId", required = true) Integer skuId,
            @RequestParam(value = "spuId", required = true) Integer spuId,
            @RequestParam(value = "applyReason", required = false) String applyReason,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "wechat", required = true) String wechat
    ) {
        try{
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
            ComUser comUser = initComUserParamData(request, wechat, name, phone);
            PfCorder pfCorder =  initPfCorderParamData(comUser.getId(),skuId);
            PfCorderOperationLog pfCorderOperationLog = initPfCorderOperationLog(comUser);
            Long orderId = trialService.insert(pfCorder,pfCorderOperationLog,comUser);
            return orderId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成用户初始化数据
     * @author hanzengzhi
     * @date 2016/3/15 10:54
     */
    private ComUser initComUserParamData( HttpServletRequest request,
                                          String wechat,String name,String phone)throws Exception{
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        if (comUser==null){
            comUser = new ComUser();
            comUser.setId(1L);
        }
        comUser =userService.getUserById(comUser.getId());
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
        return comUser;
    }

    /**
     * 初始化订单参数数据
     * @author hanzengzhi
     * @date 2016/3/15 10:47
     */
    private PfCorder initPfCorderParamData(Long userId,Integer skuId){
        SfUserRelation sfUserRelation =  trialService.findPidById(userId);
        //生成试用订单
        PfCorder pfCorder = new PfCorder();
        pfCorder.setCreateTime(new Date());
        pfCorder.setOrderCode("00000");
        pfCorder.setOrderType(0);
        pfCorder.setSkuId(skuId);
        pfCorder.setUserId(userId);
        if (sfUserRelation!=null){
            pfCorder.setUserPid(sfUserRelation.getParentUserId());
        }
        pfCorder.setUserMassage("");
        pfCorder.setSupplierId(0);
        pfCorder.setCreateMan(userId);
        return pfCorder;
    }
    /**
     * 初始化pfcorderOperation日志表
     * @author hanzengzhi
     * @date 2016/3/15 10:38
     */
    private PfCorderOperationLog initPfCorderOperationLog(ComUser comUser){
        //生成试用日志
        PfCorderOperationLog pcol = new PfCorderOperationLog();
        pcol.setCreateTime(new Date());
        pcol.setCreateMan(comUser.getId());
        pcol.setPfCorderStatus(0);
        return pcol;
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
            orderId = 12L;
        }
        Map<String,Object> pfCorderMap = cOrderService.confirmOrder(request,orderId,userId,selectedAddressId);
        ComUserAddress comUserAddress = (ComUserAddress)pfCorderMap.get("comUserAddress");
        //图片
        Product product = (Product)pfCorderMap.get("product");
        if (product!=null){
            String skuImg = PropertiesUtils.getStringValue(SysConstants.INDEX_PRODUCT_IMAGE_MIN);
            model.addAttribute("skuName", product.getName());
            if (product.getComSkuImages()!=null&&product.getComSkuImages().size()>0){
                model.addAttribute("skuDefaultImg",skuImg + product.getComSkuImages().get(0).getImgUrl());
                model.addAttribute("skuImgAlt", product.getComSkuImages().get(0).getImgName());
            }
        }
        model.addAttribute("product",product);
        model.addAttribute("comUserAddress",comUserAddress);
        return "platform/order/zhifushiyong";
    }
}
