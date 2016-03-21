package com.masiis.shop.admin.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.controller.base.BaseController;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.admin.service.user.PfUserSkuService;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.user.PfUserSkuCertificate;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 49134 on 2016/3/9.
 */
@Controller
@RequestMapping("/userSku")
public class PfUserSkuController extends BaseController {

    @Resource
    private PfUserSkuService pfUserSkuService;
    @Resource
    private ComUserService comUserService;

    /**
     * 合伙人列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list.shtml")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam(value="pid", required = false)Long pid) {

        ModelAndView mav = new ModelAndView("user/pfUserSku_list");
        mav.addObject("pid", pid);

        return mav;
    }

    @RequestMapping("list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       String beginTime,
                       String endTime,
                       String mobile,
                       @RequestParam(value="pid", required = false) Long pid,
                       String sort,
                       String order,
                       Integer offset,
                       Integer limit
    )throws Exception {
        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 10 : limit;
        Integer pageNo = offset/limit + 1;
        PageHelper.startPage(pageNo, limit);
        Map<String, Object> searchParam = new HashMap<>();//组合搜索
        searchParam.put("pid", pid);
        searchParam.put("beginTime",beginTime);
        searchParam.put("endTime",endTime);
        searchParam.put("mobile",mobile);
        List<PfUserSkuCertificate> pfUserSkuList = pfUserSkuService.getUserSkuList(searchParam);
        if (pfUserSkuList!=null&&pfUserSkuList.size()!=0){
            for (PfUserSkuCertificate pfUserSkuCertificate:pfUserSkuList) {
                if(pfUserSkuCertificate != null && pfUserSkuCertificate.getComUser()!=null&& pfUserSkuCertificate.getComUser().getId() != null){
                    Integer parentId = pfUserSkuCertificate.getComUser().getId().intValue();
                    Integer lowerCount = pfUserSkuService.findLowerCount(parentId);
                    String pRealName = comUserService.findByPid(pfUserSkuCertificate.getPid());

                    pfUserSkuCertificate.setLowerCount(lowerCount);
                    pfUserSkuCertificate.setpRealName(pRealName);
                }
            }
        }
        PageInfo<PfUserSkuCertificate> pageInfo = new PageInfo<>(pfUserSkuList);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", pfUserSkuList);
        return pageMap;
    }


    @RequestMapping("/person.shtml")
    public ModelAndView person(HttpServletRequest request, HttpServletResponse response, Long id){
        ModelAndView mav = new ModelAndView("user/person");

        ComUser comUser = null;
        if(id != null){
            comUser = comUserService.findById(id);
            if (comUser!=null&&comUser.getIdCardFrontUrl()!=null||comUser.getIdCardBackUrl()!=null){
                String cardImg = PropertiesUtils.getStringValue("index_user_idCard_url");
                comUser.setIdCardFrontUrl(cardImg + comUser.getIdCardFrontUrl());
                comUser.setIdCardBackUrl(cardImg + comUser.getIdCardBackUrl());
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(comUser.getCreateTime());
            mav.addObject("date",format);
        }
        mav.addObject("comUser", comUser);
        return mav;
    }

    /**
     * 合伙信息页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/partner.shtml")
    public ModelAndView partner(HttpServletRequest request, HttpServletResponse response, Integer id) {
        ModelAndView mav = new ModelAndView("user/partner");
        mav.addObject("id", id);

        return mav;
    }

    @RequestMapping("/partner.do")
    @ResponseBody
    public Object partner(HttpServletRequest request, HttpServletResponse response,
                                String sort,
                                String order,
                                Integer offset,
                                Integer limit,
                                Integer id){

        offset = offset==null ? 0 : offset;
        limit  = limit ==null ? 10 : limit;
        Integer pageNo = offset/limit + 1;
        PageHelper.startPage(pageNo, limit);
        List<PfUserSkuCertificate> pfUserSkuList = null;
        if(id != null){
            pfUserSkuList = pfUserSkuService.getUserSkuById(id);
            if (pfUserSkuList.size()!=0){
                for (PfUserSkuCertificate pfUserSkuCertificate:pfUserSkuList) {
                    if (pfUserSkuCertificate!=null){
                        ComUser comUser = pfUserSkuCertificate.getComUser();
                        String pRealName = comUserService.findByPid(pfUserSkuCertificate.getPid());
                        pfUserSkuCertificate.setpRealName(pRealName);
                    }
                }
            }
        }
        PageInfo<PfUserSkuCertificate> pageInfo = new PageInfo<>(pfUserSkuList);
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", pfUserSkuList);
        return pageMap;
    }

}
