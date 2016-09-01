package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.QRCodeUtil;
import com.masiis.shop.common.util.RootPathUtils;
import com.masiis.shop.common.util.image.DrawImageUtil;
import com.masiis.shop.common.util.image.Element;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserOrganizationMapper;
import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserOrganization;
import com.masiis.shop.web.common.utils.NetFileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.masiis.shop.common.util.PropertiesUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * 发展合伙人
 */

@Service
public class DevelopService {

    private static Font font30 = null;
    private static Font font25 = null;
    private static Font font20 = null;

    static {
        //宋体
        Font simsun_font = null;
        //微软雅黑
        Font msyh_font = null;
        try {
            simsun_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath()+"/static/font/simsun.ttc"));
            msyh_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath()+"/static/font/msyh.ttc"));
        } catch (Exception e) {
            throw new BusinessException("创建字体异常");
        }

        font20 = msyh_font.deriveFont(Font.PLAIN, 20);
        font25 = msyh_font.deriveFont(Font.PLAIN, 25);
        font30 = msyh_font.deriveFont(Font.PLAIN, 30);

//        font20 = simsun_font.deriveFont(Font.PLAIN, 20);
//        font30 = simsun_font.deriveFont(Font.PLAIN, 25);
//        font30 = simsun_font.deriveFont(Font.PLAIN, 30);
    }



    private static final String organizationLogoUrlRoot = PropertiesUtils.getStringValue("organization_img_url");
    private static final String brandLogoUrlRoot = PropertiesUtils.getStringValue("brand_poster_url");

    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;
    @Resource
    private ComBrandMapper comBrandMapper;
    @Resource
    private ComUserMapper comUserMapper;

    public BufferedImage createDevelopPoster(PfUserOrganization org, ComUser comUser, HttpServletRequest request) {
        String host = StringUtils.substringBefore(request.getRequestURL().toString(), "/develop");
        return createDevelopPoster(org, comUser, host);
    }

    public BufferedImage createDevelopPoster(PfUserOrganization org, ComUser comUser, String host) {

        BufferedImage bufferedImage = null;
        try {
            ComBrand brand = comBrandMapper.selectById(org.getBrandId());

            BufferedImage brandPosterImage = NetFileUtils.getBufferedImage(brandLogoUrlRoot + brand.getBrandPosterName());
            BufferedImage organizationLogoImage = NetFileUtils.getBufferedImage(organizationLogoUrlRoot+org.getLogo());
            organizationLogoImage = DrawImageUtil.makeCircle(organizationLogoImage);
            BufferedImage qrImage = QRCodeUtil.createImage(host+"/static/html/downloadApp.html?userId="+comUser.getId(), null, true);

            Element brandPosterElement = new Element(0,0,751,434, brandPosterImage);
            Element orgLogoElement = new Element(321, 199, 108, 108, organizationLogoImage);
            Element orgNameElement = new Element(375-org.getName().length()*15, 320, font30, new Color(77, 72, 76), org.getName());
            Element orgContentAreaElement = new Element(22, 434, 700, 189,null);
            orgContentAreaElement.setColor(new Color(239, 239, 239));

            String orgIntroduction = org.getIntroduction();//"不是每个产品都能赚钱，不是每个团队都有规模，不是每个公司都能给你半门槛！微衷天映团队拥有10年微信营销经验";//，代理多款火爆产品，不定期组织线下活动，让你快速赚钱不是梦。";
            java.util.List<Element> introEleList = new LinkedList<>();
            for(int i=0; i<3 && orgIntroduction != null && orgIntroduction.length()>0; i++) {
                int limit = orgIntroduction.length()>30 ? 30 : orgIntroduction.length();
                String subString = orgIntroduction.substring(0, limit);
                Element orgIntroductionElement = new Element(58,483+(40*i), font20, Color.GRAY, subString);
                orgIntroductionElement.setLineStyle(0);
                orgIntroduction = orgIntroduction.substring(limit);
                introEleList.add(orgIntroductionElement);
            }

            Element qrElement = new Element(277, 647, 193, 193, qrImage);
            Element phoneElement = new Element(251, 880, font25, Color.GRAY, "手机号："+comUser.getMobile(), 0);
            Element downElement = new Element(260, 920, font25, Color.GRAY, "下载麦链合伙人APP", 0);
            Element footerElement = new Element(150, 980, font20, Color.GRAY, "扫描二维码或在麦链合伙人APP内搜索手机号即可", 0);


            java.util.List<Element> elements = new LinkedList<>();
            elements.add(brandPosterElement);
            elements.add(orgLogoElement);
            elements.add(orgNameElement);
            elements.add(orgContentAreaElement);
            elements.addAll(introEleList);
            elements.add(qrElement);
            elements.add(phoneElement);
            elements.add(downElement);
            elements.add(footerElement);

            bufferedImage = DrawImageUtil.drawImage(751, 1026, elements, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

}
