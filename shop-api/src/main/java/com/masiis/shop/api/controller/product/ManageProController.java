package com.masiis.shop.api.controller.product;

import com.masiis.shop.api.bean.product.ProAllListReq;
import com.masiis.shop.api.bean.product.ProDetailReq;
import com.masiis.shop.api.bean.product.ProDetailRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.controller.base.BaseController;
import com.masiis.shop.api.service.product.ProductService;
import com.masiis.shop.dao.beans.product.Product;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by JingHao on 2016/5/19 0019.
 */


@Controller
@RequestMapping("/managePro")
public class ManageProController extends BaseController {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ProductService productService;

    @RequestMapping(value = "user",method = RequestMethod.GET)
    @ResponseBody
    @SignValid(paramType = ProAllListReq.class)
    public ProDetailRes proListForUser(HttpServletRequest request, ProDetailReq req,ComUser user){
        ProDetailRes proDetailRes = new ProDetailRes();
        try{
            List<Product> userProducts = productService.productListByUser(user.getId());
            if (userProducts != null) {
                for (Product product : userProducts) {
                    product.setUpperStock(productService.getUpperStock(user.getId(), product.getId()));
                }
            }
            proDetailRes.setProductList(userProducts);
            proDetailRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            proDetailRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            proDetailRes.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            proDetailRes.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }
        return proDetailRes;
    }
}
