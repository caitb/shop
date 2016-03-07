package com.masiis.shop.admin.controller.certificate;

import com.masiis.shop.admin.service.certificate.CertificateService;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by JingHao on 2016/3/7 0007.
 */
@Controller
@RequestMapping("/certificate")
public class CertificateController {

    @Resource
    private CertificateService certificateService;


    @RequestMapping("/certificate_list.shtml")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        return "certificate/certificate_list";
    }
    @RequestMapping(value = "/certificate_list", method = RequestMethod.GET)
    public Object getCertificateInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = new ModelAndView("/certificate/certificate_list");
        List<CertificateInfo> certificateInfoList = certificateService.getCertificates();
        mav.addObject("certificateInfoList",certificateInfoList);
        return mav;
     }
    }
