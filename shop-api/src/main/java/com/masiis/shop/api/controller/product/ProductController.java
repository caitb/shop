package com.masiis.shop.api.controller.product;

import com.masiis.shop.api.bean.base.BaseReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.api.bean.product.ProAllListReq;
import com.masiis.shop.api.bean.product.ProAllListRes;
import com.masiis.shop.api.bean.user.MarketIndexRes;
import com.masiis.shop.api.bean.user.MarketProItem;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.order.BOrderService;
import com.masiis.shop.api.service.product.ProductService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.shop.IndexShowService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PbBanner;
import com.masiis.shop.dao.po.PfUserSku;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
