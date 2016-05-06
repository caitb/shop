package com.masiis.shop.web.platform.controller.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserCertificateMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.constant.wx.WxConsPF;
import com.masiis.shop.web.platform.controller.base.BaseController;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.qrcode.WeiXinQRCodeService;
import com.masiis.shop.web.platform.service.shop.JSSDKService;
import com.masiis.shop.web.platform.service.user.UserCertificateService;
import com.masiis.shop.web.platform.utils.DownloadImage;
import com.masiis.shop.web.platform.utils.qrcode.QRCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 发展合伙人
 * Created by cai_tb on 16/3/17.
 */
@Controller
@RequestMapping("/developing")
public class DevelopingController extends BaseController {

    private final static Log log = LogFactory.getLog(DevelopingController.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PfSkuAgentMapper pfSkuAgentMapper;
    @Resource
    private ComSpuMapper comSpuMapper;
    @Resource
    private SkuService skuService;
    @Resource
    private ComBrandMapper comBrandMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private UserCertificateService userCertificateService;
    @Resource
    private JSSDKService jssdkService;
    @Resource
    private WeiXinQRCodeService weiXinQRCodeService;

    @RequestMapping("/ui")
    public ModelAndView ui(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView("platform/user/developing");
        ComUser comUser = null;

        try {
            comUser = getComUser(request);
            PfUserSku userSkuC = new PfUserSku();
            userSkuC.setUserId(comUser.getId());
            List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(userSkuC);


            if(pfUserSkus != null && pfUserSkus.size() > 0){

                List<Map<String, Object>> agentMaps = new ArrayList<>();
                for(PfUserSku pus : pfUserSkus){

                    ComAgentLevel comAgentLevel = comAgentLevelMapper.selectByPrimaryKey(pus.getAgentLevelId());
                    ComSku comSku = comSkuMapper.selectById(pus.getSkuId());
                    ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
                    ComBrand comBrand = comBrandMapper.selectById(comSpu.getBrandId());
                    /* 统计商品代理等级数 */
                    Integer skuAgentLevels = pfSkuAgentMapper.countSkuAgentLevel(pus.getSkuId());

                    Map<String, Object> agentMap = new HashMap<>();
                    agentMap.put("levelId", comAgentLevel.getId());
                    agentMap.put("levelName", comAgentLevel.getName());
                    agentMap.put("skuName", comSku.getName());
                    agentMap.put("skuId", comSku.getId());
                    agentMap.put("brandLogo", comBrand.getLogoUrl());
                    agentMap.put("userSkuId", pus.getId());
                    agentMap.put("canDeveloping", pus.getAgentLevelId()==skuAgentLevels?"no":"yes");

                    agentMaps.add(agentMap);
                }

                mav.addObject("agentMaps", agentMaps);

                return mav;
            }
        } catch (Exception e) {
            log.error("获取我代理的产品失败![comUser="+comUser+"]");
            e.printStackTrace();
        }

        return mav;
    }

    @RequestMapping("/isAudit")
    @ResponseBody
    public Object isAudit(HttpServletRequest request, HttpServletResponse response, Integer userSkuId){
        PfUserCertificate pfUserCertificate = userCertificateService.getCertificateBypfuId(userSkuId);
        if(pfUserCertificate != null && pfUserCertificate.getStatus().intValue() == 1){
            return "yes";
        }else{
            return "no";
        }
    }

    @RequestMapping("/sharelink")
    public ModelAndView shareLink(HttpServletRequest request, HttpServletResponse response, Integer skuId){

        ModelAndView mav = new ModelAndView("platform/user/sharePage");

        try {
            String curUrl = request.getRequestURL().toString()+"?skuId="+skuId;

            /** 获取调用JSSDK所需要的数据 **/
            Map<String, String> resultMap = jssdkService.requestJSSDKData(curUrl);

            ComUser comUser = getComUser(request);
            log.info("发展合伙人[comUser="+comUser+"]");
            ComSku comSku = comSkuMapper.selectById(skuId);
            ComSkuExtension comSkuExtension = skuService.findSkuExteBySkuId(skuId);
            ComSpu comSpu = comSpuMapper.selectById(comSku.getSpuId());
            ComBrand comBrand = comBrandMapper.selectById(comSpu.getBrandId());
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
            String shareLink = basePath + "product/skuDetails.shtml?skuId="+skuId+"&pUserId="+comUser.getId();

            PfUserCertificate puc = new PfUserCertificate();
            puc.setUserId(comUser.getId());
            puc.setSkuId(comSku.getId());
            List<PfUserCertificate> pfUserCertificates = pfUserCertificateMapper.selectByCondition(puc);
            String[] contents = new String[2];
            if(pfUserCertificates != null && pfUserCertificates.size() > 0){
                PfUserCertificate pfUserCertificate = pfUserCertificates.get(0);
                //if(pfUserCertificate.getPoster() == null){
                    String headImgName = "headimg.png";
                    String headImgPath = request.getServletContext().getRealPath("/")+"static" + File.separator + "images" + File.separator + "poster";
                    String qrcodeName = "qrcode"+pfUserCertificate.getPfUserSkuId()+".png";
                    String qrcodePath = request.getServletContext().getRealPath("/")+"static";
                    //下载用户微信头像
                    if(comUser.getWxHeadImg() != null){
                        String headImgHttpUrl = comUser.getWxHeadImg();
                        DownloadImage.download(headImgHttpUrl, headImgName, headImgPath);
                        headImgPath += File.separator+headImgName;
                    }else{//没有微信头像,用默认头像
                        headImgPath += File.separator+"default.png";
                    }
                    //生成二维码
                    log.info("发展合伙人[headImgPath="+headImgPath+"]");
                    //QRCodeUtil.createLogoQrCode(220 ,shareLink, headImgPath, qrcodePath, true);
                    DownloadImage.download(weiXinQRCodeService.createAgentQRCode(pfUserCertificate.getPfUserSkuId()), qrcodeName, qrcodePath);
                    qrcodePath += File.separator+qrcodeName;
                    //生成海报并上传到OSS
                    String posterBGImgPath = request.getServletContext().getRealPath("/")+"static"+File.separator+"images"+File.separator+"poster"+File.separator+comSkuExtension.getPoster();
                    contents[0] = "Hi,我是"+(comUser.getRealName()==null?comUser.getWxNkName():comUser.getRealName());
                    contents[1] = "我在麦链合伙人做抗引力-瘦脸精华执行董事级合伙人，赚了不少钱，邀请你也来，长按二维码识别即可";
                    drawPost(posterBGImgPath, qrcodePath, headImgPath, pfUserCertificate.getCode()+".png", contents);
                    //删除本地二维码图片
                    new File(qrcodePath).delete();
                    //删除本地头像
                    new File(headImgPath).delete();
                    //保存二维码海报图片地址
                    pfUserCertificate.setPoster(PropertiesUtils.getStringValue("index_user_poster_url")+pfUserCertificate.getCode()+".png");
                    pfUserCertificateMapper.updateById(pfUserCertificate);
                //}
                resultMap.put("poster", pfUserCertificate.getPoster());
            }


            resultMap.put("appId", WxConsPF.APPID);
            resultMap.put("shareTitle", contents[0]);
            resultMap.put("shareDesc", contents[1]);
            resultMap.put("shareLink", shareLink);
            resultMap.put("shareImg", comBrand.getLogoUrl());

            //TODO

            mav.addObject("shareMap", resultMap);

            return mav;
        } catch (Exception e) {
            log.error("获取分享链接失败![skuId="+skuId+"]");
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        System.out.println("test%: " + (18/10));
    }

    /**
     * 画海报
     * @param bPath        海报背景图路径
     * @param qrcodePath   二维码背景图路径
     * @param saveFileName 保存名字
     */
    public void drawPost(String bPath, String qrcodePath, String headImgPath, String saveFileName, String[] contents) {
        ImageIcon bImgIcon = new ImageIcon(bPath);
        ImageIcon qrcodeImgIcon = new ImageIcon(qrcodePath);
        ImageIcon headImgIcon = new ImageIcon(headImgPath);
        Image bImage = bImgIcon.getImage();
        Image qrcodeImage = qrcodeImgIcon.getImage();
        Image headImage = headImgIcon.getImage();

        int width = bImage.getWidth(null) == -1 ? 904 : bImage.getWidth(null);
        int height = bImage.getHeight(null) == -1 ? 1200 : bImage.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        g.drawImage(headImage, 88, 619, 132, 132, null);
        g.drawImage(bImage, 0, 0, null);
        g.drawImage(qrcodeImage, 566, 776, 220, 220, null);

        g.setFont(new Font("华文细黑", Font.PLAIN, 32));
        g.setColor(new Color(51,51,51));
        g.drawString(contents[0], 92, 785);
        for(int i=1; i<contents.length; i++){
            int length = contents[i].length()/10;
            int more = contents[i].length() - length*10;
            for(int j=0; j<length; j++){
                if((j+1)<=length) g.drawString(contents[i].substring(j*10, (j+1)*10), 92, 780+(i*50)+j*50);
                if((j+1)>length) g.drawString(contents[i].substring(j*10), 92, 780+(i*50)+j*50);
            }
            if(more>0) g.drawString(contents[i].substring(length*10), 92, 780+(i*45)+(length+1)*40);
        }
        g.dispose();

        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufferedImage, "png", imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            OSSObjectUtils.uploadFile( "static/user/poster/" + saveFileName, is);
        } catch (Exception e) {
            log.error("画海报出错了!");
            e.printStackTrace();
            return;
        }
    }

}
