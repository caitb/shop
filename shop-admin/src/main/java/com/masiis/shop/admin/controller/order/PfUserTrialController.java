package com.masiis.shop.admin.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.order.PfUserTrialService;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserTrial;
import com.masiis.shop.dao.po.SfUserRelation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trial")
public class PfUserTrialController extends BaseController{

    @Resource
    private PfUserTrialService trialService;

    /**
     * 用户列表页面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "order/list";
    }

    /**
     * 添加用户页面
     *
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping("/add.shtml")
    public String add(HttpServletRequest request, HttpServletResponse response) {
        return "user/addUser";
    }

    /**
     * 条件分页查询用户数据
     * @param request
     * @param response
     * @param search
     * @param sort
     * @param order
     * @param offset
     * @param limit
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String search,
                       String sort,
                       String order,
                       Integer offset,
                       Integer limit
    ) throws JsonProcessingException {

        Map<String, Object> map = new HashMap<>();


        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 0 : limit;
        Integer pageNo = offset/10 + 1;
//
//        PageHelper.startPage(pageNo, limit);
//        PfUserTrialProduct pfUserTrialProduct = new PfUserTrialProduct();
//
//       List<PfUserTrialProduct> pfUserTrialProducts = trialService.findByCondition(pfUserTrialProduct);
//        for (PfUserTrialProduct userTrialProduct:pfUserTrialProducts) {
//            SfUserRelation sfUserRelation = trialService.findByUserId(userTrialProduct.getId());
//            ComSku comSku = trialService.findBySkuId(userTrialProduct.getSkuId());
//            if (comSku!=null){
//                userTrialProduct.setSkuName(comSku.getName());
//            }
////            if (sfUserRelation!=null) {
////                ComUser comUser = trialService.findByParentId(sfUserRelation.getParentUserId());
////                if (comUser!=null){
////                    userTrialProduct.setReferrer(comUser.getRealName());
////                }
////            }
//        }
//        PageInfo<PfUserTrialProduct> pageInfo = new PageInfo<PfUserTrialProduct>(pfUserTrialProducts);

//        map.put("total", pageInfo.getTotal());
//        map.put("rows", pfUserTrialProducts);

        return map;
    }
/*
    @RequestMapping("/load.shtml")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response, Long id){
        ModelAndView mav = new ModelAndView("user/edit");

        PbUser pbUser = null;
        if(id != null){
            pbUser = trialService.findByid(id);
        }

        mav.addObject("pbUser", pbUser);

        return mav;
    }

    /**
     * 添加用户
     *
     * @param request
     * @param response
     * @param pbUser     新添用户数据
     *//*
    @RequestMapping("/add")
    @ResponseBody
    public String add(HttpServletRequest request, HttpServletResponse response, PbUser pbUser) {
        if (StringUtil.isNotEmpty(pbUser.getPassword())) {
            pbUser.setPassword(KeysUtil.md5Encrypt(pbUser.getPassword()));
        }

        if (pbUser.getId() == null) {
            this.pbUserService.add(pbUser);
        } else {
            this.pbUserService.updateById(pbUser);
        }

        return "保存成功";
    }

    *//**
     * 保存用户菜单权限信息
     * @param request
     * @param response
     * @param userId
     * @param pbMenuIds
     * @return
     *//*
    @RequestMapping("/updateUserMenu.do")
    @ResponseBody
    public String updateUserMenu(HttpServletRequest request, HttpServletResponse response,
                                 Long userId,
                                 @RequestParam(value = "pbMenuIds[]") Long[] pbMenuIds){

        pbUserMenuService.deleteByPbUserId(userId);

        PbUserMenu pbUserMenu = new PbUserMenu();
        for(Long pbMenuId : pbMenuIds){
            pbUserMenu.setPbUserId(userId);
            pbUserMenu.setPbMenuId(pbMenuId);
            pbUserMenu.setCreateTime(new Date());
            pbUserMenu.setUpdateTime(new Date());
            pbUserMenuService.add(pbUserMenu);
        }

        return "success";
    }*/

}
