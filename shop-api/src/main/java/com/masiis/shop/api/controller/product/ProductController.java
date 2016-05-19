package com.masiis.shop.api.controller.product;

import com.masiis.shop.api.bean.product.ProAllListReq;
import com.masiis.shop.api.bean.product.ProAllListRes;
import com.masiis.shop.api.bean.product.ProDetailReq;
import com.masiis.shop.api.bean.product.ProDetailRes;
import com.masiis.shop.api.bean.user.MarketProItem;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.ProductService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.shop.IndexShowService;
import com.masiis.shop.api.service.user.UserSkuService;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2016/5/4
 * @Auther lzh
 */
@Controller
@RequestMapping("/pro")
public class ProductController extends BaseController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private IndexShowService indexShowService;
    @Resource
    private ProductService productService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserSkuService userSkuService;

    @RequestMapping("/alist")
    @ResponseBody
    @SignValid(paramType = ProAllListReq.class)
    public ProAllListRes toAllPros(HttpServletRequest request, ProAllListReq req, ComUser user){
        ProAllListRes res = new ProAllListRes();
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
        //获取商品图片地址常量
        String skuValue = PropertiesUtils.getStringValue("index_product_800_800_url");
        //获取主页展示商品信息
        List<IndexComSku> indexComS = indexShowService.findIndexComSku(user.getId());
        List<MarketProItem> ComS =new ArrayList<MarketProItem>();
        for (IndexComSku indexCom:indexComS) {
            MarketProItem m = new MarketProItem();
            //获取商品图片地址
            String url = skuValue + indexCom.getImgUrl();
            //重新封装商品图片地址
            m.setImgUrl(url);
            //确定代理权限，显示优惠区间
            m.setMaxDiscount(productService.getMaxDiscount(indexCom.getSkuId()));
            m.setDiscountLevel("最高利润"+productService.getMaxDiscount(indexCom.getSkuId())+"%");
            m.setBailLevel(skuAgentService.getSkuAgentLevel(indexCom.getSkuId()));
            PfUserSku pfUserSku = bOrderService.findPfUserSku(user.getId(),indexCom.getSkuId());
            if (pfUserSku !=null){
                //判断会员权限
                m.setIsPartner(1);
            } else {
                m.setIsPartner(0);
            }
            m.setSkuId(indexCom.getSkuId());
            m.setSkuName(indexCom.getComSku().getName());
            m.setSkuPriceRetail(indexCom.getComSku().getPriceRetail().toString());
            m.setAgentNum(indexCom.getAgentNum());
            m.setIsTrial(indexCom.getIsTrial());
            m.setShipAmount(indexCom.getShipAmount());
            ComS.add(m);
        }

        res.setPros(ComS);
        res.setProNums(ComS.size());
        res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
        res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);

        return res;
    }

    /**
      * @Author jjh
      * @Date 2016/5/19 0019 下午 2:46
      *
      */
    @RequestMapping(value = "detail/{skuId}",method = RequestMethod.GET)
    @ResponseBody
    @SignValid(paramType = ProDetailReq.class)
    public ProDetailRes getProDetail(HttpServletRequest request, ProDetailReq req,
                                     @PathVariable("skuId") Integer skuId,
                                     ComUser user) {
        ProDetailRes proDetailRes = new ProDetailRes();
        ComSku comSku = skuService.getSkuById(skuId);
        if (comSku == null) {
            proDetailRes.setResCode("1");
            proDetailRes.setResMsg("sku不合法,系统不存在该sku");
            return proDetailRes;
        }
        try {
            Product product = productService.getSkuDetails(skuId);
            PfUserSku pfUserSku = userSkuService.getUserSkuByUserIdAndSkuId(user.getId(), skuId);
            PfBorder pfBorder = bOrderService.getPfBorderBySkuAndUserId(skuId, user.getId());
            proDetailRes.setProduct(product);
            proDetailRes.setPfUserSku(pfUserSku);
            proDetailRes.setOrderStatus(pfBorder.getOrderStatus());
            proDetailRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            proDetailRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            e.printStackTrace();
            proDetailRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            proDetailRes.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        return proDetailRes;
    }

}
