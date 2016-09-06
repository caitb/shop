package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.*;
import com.masiis.shop.common.util.image.DrawImageUtil;
import com.masiis.shop.common.util.image.DrawUtil;
import com.masiis.shop.dao.platform.product.ComBrandMapper;
import com.masiis.shop.dao.po.ComBrand;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserOrganization;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;


/**
 * 发展合伙人
 */

@Service
public class DevelopService {

    private static final String developPosterFolder = PropertiesUtils.getStringValue("develop_poster_folder");
    private static final String ossBaseUrl = PropertiesUtils.getStringValue("oss.BASE_URL");

    private static Font font20 = null;
    private static Font font25 = null;
    private static Font font30 = null;
    private static Font font36 = null;
    private static Font font40 = null;
    private static Font fontCloud30 = null;

    private static final Log log = LogFactory.getLog(DevelopService.class);

    static {
        //宋体
        Font simsun_font = null;
        //微软雅黑
        Font msyh_font = null;
        // 空心
        Font cloud_font = null;
        try {
            System.out.println(RootPathUtils.getRootPath());
            simsun_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath()+File.separator+"static"+File.separator+"font"+File.separator+"simsun.ttc"));
            msyh_font = Font.createFont(Font.TRUETYPE_FONT, new File(RootPathUtils.getRootPath()+File.separator+"static"+File.separator+"font"+File.separator+"msyh.ttc"));
        } catch (Exception e) {
            log.error("**************  font_file_path : "+RootPathUtils.getRootPath()+"/static/font/simsun.ttc");
            log.error("**************  font_file_path2: "+RootPathUtils.getRootPath()+File.separator+"static"+File.separator+"font"+File.separator+"simsun.ttc");
            throw new BusinessException("创建字体异常");
        }

        font20 = msyh_font.deriveFont(Font.PLAIN, 20);
        font25 = msyh_font.deriveFont(Font.PLAIN, 25);
        font30 = msyh_font.deriveFont(Font.PLAIN, 30);
        font36 = msyh_font.deriveFont(Font.PLAIN, 36);
        font40 = msyh_font.deriveFont(Font.PLAIN, 40);
    }



    private static final String organizationLogoUrlRoot = PropertiesUtils.getStringValue("organization_img_url");
    private static final String brandPosterUrlRoot = PropertiesUtils.getStringValue("brand_poster_url");

    @Resource
    private ComBrandMapper comBrandMapper;

    public BufferedImage createDevelopPoster(PfUserOrganization org, ComUser comUser, HttpServletRequest request) {
        String host = StringUtils.substringBefore(request.getRequestURL().toString(), "/develop");
        return createDevelopPoster(org, comUser, host);
    }

    public BufferedImage createDevelopPoster(PfUserOrganization org, ComUser comUser, String host) {

        BufferedImage bufferedImage = null;
        try {
            ComBrand brand = comBrandMapper.selectById(org.getBrandId());


            BufferedImage brandPosterImage = getBrandPosterBufferedImage(brand.getBrandPosterName());
            BufferedImage bg = ImageIO.read(new File(RootPathUtils.getRootPath()+"/static/images/developPosterBg.png"));
            BufferedImage organizationLogoImage = ImageIO.read(new URL(organizationLogoUrlRoot+org.getLogo()));
            organizationLogoImage = DrawImageUtil.makeCircle(organizationLogoImage);
            BufferedImage qrImage = QRCodeUtil.createImage(host+"/static/html/downloadApp.html?userId="+comUser.getId(), null, true);

            int width = 751;
            int height = 1026;

            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();

            // 设置整体背景色
            g.setColor(new Color(225,225,225));
            g.fillRect(0,0,width, height);

            brandPosterImage = compress(brandPosterImage, 200, 110);
            g.drawImage(brandPosterImage, 0,0,751,434, null);   // 品牌背景图
            g.drawImage(bg, 0,0,751,434, null);     // 蒙板
            g.drawImage(organizationLogoImage, 321, 199, 108, 108, null);   // 组织 logo
            DrawUtil.drawString(g, org.getName(), font36, Color.WHITE, 375-org.getName().length()*18, 370);  // 组织名

            // 组织介绍区
            g.setColor(new Color(239, 239, 239));
            g.fillRoundRect(22, 434, 700, 189, 5, 5);

            // 组织介绍
            String orgIntroduction = org.getIntroduction();
            for(int i=0; i<3 && orgIntroduction != null && orgIntroduction.length()>0; i++) {
                int limit = orgIntroduction.length()>30 ? 30 : orgIntroduction.length();
                String subString = orgIntroduction.substring(0, limit);
                orgIntroduction = orgIntroduction.substring(limit);
                DrawUtil.drawString(g, subString, font20, Color.GRAY, 58,483+(40*i) );
            }

            g.drawImage(qrImage, 277, 647, 193, 193, null);     // 二维码
            DrawUtil.drawString(g, "手机号："+comUser.getMobile(), font25, Color.GRAY, 251, 880);
            DrawUtil.drawString(g, "下载麦链合伙人APP", font25, Color.GRAY, 260, 920);
            DrawUtil.drawString(g,"扫描二维码或在麦链合伙人APP内搜索手机号即可", font20, Color.GRAY, 150, 980);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    private static BufferedImage getBrandPosterBufferedImage(String posterName) {
        try {
            File file = new File(RootPathUtils.getRootPath()+"/static/images/brandPoster/"+posterName);
            if(!file.exists()) {
                downlaodBrandPoster(posterName);
            }

            return ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void downlaodBrandPoster(String posterName) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new URL(brandPosterUrlRoot + posterName));
            File file = new File(RootPathUtils.getRootPath()+"/static/images/brandPoster/"+posterName);
            ImageIO.write(bufferedImage, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage compress(BufferedImage bf, int w, int h) {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(bf, 0, 0, w, h, null);
        return bufferedImage;
    }

}

