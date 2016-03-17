package com.masiis.shop.web.platform.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.order.COrderService;
import com.masiis.shop.web.platform.service.order.PfCorderConsigneeService;
import com.masiis.shop.web.platform.service.order.PfUserTrialService;
import com.masiis.shop.web.platform.service.product.ProductService;
import com.masiis.shop.web.platform.service.user.UserAddressService;
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
    @Resource
    private UserAddressService userAddressService;
    @Resource
    private PfCorderConsigneeService pfCorderConsigneeService;


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
     * 验证商品是否试用过
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
        PfCorder pfCorder = cOrderService.isApplyTrial(userId,skuId);
        String returnJson = objectMapper.writeValueAsString(pfCorder);
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
            @RequestParam(value="addressId",required = true) Integer addressId
    ) {
        ComUser comUser = (ComUser) request.getSession().getAttribute("comUser");
        Long userId =  null;
        try{
            if (StringUtils.isEmpty(skuId)){
                skuId = 111;
            }
            if (comUser!=null){
                userId = comUser.getId();
            }else{
                userId = 1L;
            }
            PfCorder pfCorder =  initPfCorderParamData(userId,skuId);
            PfCorderOperationLog pfCorderOperationLog = initPfCorderOperationLog(comUser);
            Long orderId = trialService.insert(pfCorder,pfCorderOperationLog,comUser);
            ComUserAddress comUserAddress = userAddressService.getUserAddressById(addressId);
            PfCorderConsignee pfCorderConsignee = initPfCorderConsigneeParamData(orderId,comUserAddress);
            pfCorderConsigneeService.insertPfCC(pfCorderConsignee);
            return orderId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
        pfCorder.setOrderCode(OrderMakeUtils.makeOrder("C"));
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
     * 订单收获人信息
     * @author hanzengzhi
     * @date 2016/3/17 13:45
     */
    private PfCorderConsignee initPfCorderConsigneeParamData(Long cOrderId,ComUserAddress comUserAddress)throws Exception{
        PfCorderConsignee pfCorderConsignee = new PfCorderConsignee();
        try{
            if (comUserAddress!=null){
                pfCorderConsignee.setPfCorderId(cOrderId);
                pfCorderConsignee.setUserId(comUserAddress.getUserId());
                pfCorderConsignee.setConsignee(comUserAddress.getName());
                pfCorderConsignee.setMobile(comUserAddress.getMobile());
                pfCorderConsignee.setProvinceId(comUserAddress.getProvinceId());
                pfCorderConsignee.setProvinceName(comUserAddress.getProvinceName());
                pfCorderConsignee.setCityId(comUserAddress.getCityId());
                pfCorderConsignee.setCityName(comUserAddress.getCityName());
                pfCorderConsignee.setRegionId(comUserAddress.getRegionId());
                pfCorderConsignee.setRegionName(comUserAddress.getRegionName());
                pfCorderConsignee.setAddress(comUserAddress.getAddress());
                pfCorderConsignee.setZip(comUserAddress.getZip());
            }
        }catch (Exception e){
            throw  new Exception("生成订单获取用户地址错误");
        }
        return pfCorderConsignee;
    }
    /**
     * 确认订单
     * @author  hanzengzhi
     * @date  2016/3/8 10:16
     */
    @RequestMapping("/confirmOrder.do")
    public String confirmOrder(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value = "skuId", required = false) Integer skuId,
                                @RequestParam(value = "selectedAddressId", required = false) Long selectedAddressId,
                                Model model){
        ComUser comUser = (ComUser)request.getSession().getAttribute("comUser");
        Long userId = null;
        if (comUser!=null){
            userId = comUser.getId();
        }else{
            userId = 1L;
        }
        Map<String,Object> pfCorderMap = cOrderService.confirmOrder(request,skuId,userId,selectedAddressId);
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
