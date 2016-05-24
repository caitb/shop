package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.user.AgentSku;
import com.masiis.shop.api.bean.user.AgentSkuRes;
import com.masiis.shop.api.bean.user.PopularizeReq;
import com.masiis.shop.api.bean.user.PopularizeRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.api.service.product.BrandService;
import com.masiis.shop.api.service.product.SkuAgentService;
import com.masiis.shop.api.service.product.SkuService;
import com.masiis.shop.api.service.product.SpuService;
import com.masiis.shop.api.service.qrcode.WeiXinQRCodeService;
import com.masiis.shop.api.service.user.*;
import com.masiis.shop.api.utils.image.DownloadImage;
import com.masiis.shop.api.utils.image.DrawImageUtil;
import com.masiis.shop.api.utils.image.Element;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.po.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cai_tb on 16/5/19.
 */
@Controller
@RequestMapping("/develop")
public class DevelopController {

    private final static Log log = LogFactory.getLog(DevelopController.class);

    @Resource
    private UserSkuService userSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private SpuService spuService;
    @Resource
    private AgentLevelService agentLevelService;
    @Resource
    private BrandService brandService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private CertificateService certificateService;
    @Resource
    private WeiXinQRCodeService weiXinQRCodeService;
    @Resource
    private SkuExtensionService skuExtensionService;

    @Resource
    private ComUserService comUserService;

    @RequestMapping("/listAgentSku")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public AgentSkuRes listAgentSku(HttpServletRequest request, BaseBusinessReq bbq, ComUser comUser){
        AgentSkuRes agentSkuRes = new AgentSkuRes();

        try {
            List<PfUserSku> userSkus = userSkuService.listByUserId(comUser.getId());

            for(PfUserSku userSku : userSkus){
                ComSku comSku = skuService.getSkuById(userSku.getSkuId());
                ComSpu comSpu = spuService.getById(comSku.getSpuId());
                ComBrand comBrand = brandService.getById(comSpu.getBrandId());
                ComAgentLevel comAgentLevel = agentLevelService.getById(userSku.getAgentLevelId());
                List<PfSkuAgent> skuAgents = skuAgentService.getAllBySkuId(comSku.getId());

                AgentSku agentSku = new AgentSku();
                agentSku.setSkuId(comSku.getId());
                agentSku.setSkuName(comSku.geteName());
                agentSku.setLevelId(comAgentLevel.getId());
                agentSku.setLevelName(comAgentLevel.getName());
                agentSku.setBrandLogo(comBrand.getLogoUrl());
                agentSku.setUserSkuId(userSku.getId());
                agentSku.setDevelop(comAgentLevel.getId().intValue()==skuAgents.size() ? false:true);

                agentSkuRes.getAgentSkus().add(agentSku);
            }

        } catch (Exception e) {
            log.error("获取用户代理产品失败![bbs="+bbq+"][comUser="+comUser+"]");
            e.printStackTrace();

            agentSkuRes.setResCode(SysResCodeCons.RES_CODE_REQ_STRUCT_INVALID);
            agentSkuRes.setResMsg("获取用户代理产品失败!");
        }

        return agentSkuRes;
    }

    @RequestMapping("/popularize")
    @ResponseBody
    @SignValid(paramType = PopularizeReq.class, hasToken = false)
    public PopularizeRes popularize(HttpServletRequest request, PopularizeReq popularizeReq, ComUser comUser){
        PopularizeRes popularizeRes = new PopularizeRes();

        try {
            PfUserSku userSku = userSkuService.getUserSkuById(popularizeReq.getUserSkuId());
            ComSku comSku = skuService.getSkuById(userSku.getSkuId());
            ComAgentLevel comAgentLevel = agentLevelService.getById(userSku.getAgentLevelId());
            PfUserCertificate certificate = certificateService.getByCode(userSku.getCode());
            ComSkuExtension skuExtension = skuExtensionService.getBySkuId(userSku.getSkuId());

            //下载海报和二维码
            //comUser = comUserService.getUserById(324L);
            String headImg = "h-"+comUser.getId()+".png";
            String qrcodeName = "qrcode-"+comUser.getId()+"-"+userSku.getId()+".png";
            String bgPoster = "bg-"+skuExtension.getPoster();
            String posterDirPath = request.getServletContext().getRealPath("/")+"static/images/poster";
            File  posterDir = new File(posterDirPath);
            if(!posterDir.exists()){
                posterDir.mkdirs();
            }
            DownloadImage.download(comUser.getWxHeadImg(), headImg, posterDirPath);
            DownloadImage.download(weiXinQRCodeService.createAgentQRCode(userSku.getSkuId()), qrcodeName, posterDirPath);
            OSSObjectUtils.downloadFile("static/user/background_poster/kangyinli.png", posterDirPath+"/"+bgPoster);

            //画图
            Element headImgElement = new Element(88, 619, 132, 132, ImageIO.read(new File(posterDirPath+"/"+headImg)));
            Element bgPosterImgElement = new Element(0, 0, 904, 1200, ImageIO.read(new File(posterDirPath+"/"+bgPoster)));
            Element qrcodeImgElement = new Element(566, 776, 220, 200, ImageIO.read(new File(posterDirPath+"/"+qrcodeName)));

            String fontPath = request.getServletContext().getRealPath("/")+"static/font";
            //Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath+"/msyh.ttc"));
            //font1.deriveFont(Font.PLAIN, 32);
            Font font1 = new Font("华文细黑", Font.PLAIN, 32);
            Date curDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String startTime = sdf.format(curDate);
            curDate.setDate(curDate.getDate()+30);
            String endDate = sdf.format(curDate);
            Element text1Element = new Element(92, 785,   font1, new Color(51, 51, 51), "Hi,我是"+(comUser.getRealName()==null?comUser.getWxNkName():comUser.getRealName()));
            Element text2Element = new Element(92, 825,   font1, new Color(51, 51, 51), "我在麦链合伙人做"+comSku.getName()+comAgentLevel.getName()+"，赚了不少钱，邀请你也来，长按二维码识别即可。");
            Element text3Element = new Element(598, 970, font1, new Color(51, 51, 51), "该二维码有效期为");
            Element text4Element = new Element(575, 1046, font1, new Color(51, 51, 51), startTime+"-"+endDate);
            text4Element.setLineStyle(0);
            List<Element> drawElements = new ArrayList<>();
            drawElements.add(headImgElement);
            drawElements.add(bgPosterImgElement);
            drawElements.add(qrcodeImgElement);
            drawElements.add(text1Element);
            drawElements.add(text2Element);
            drawElements.add(text3Element);
            drawElements.add(text4Element);

            DrawImageUtil.drawImage(904, 1200, drawElements, "static/user/poster/agent-"+comUser.getId()+"-"+userSku.getId()+".png");

            popularizeRes.setPosterUrl("http://file.masiis.com/static/user/poster/agent-"+comUser.getId()+"-"+userSku.getId()+".png");
            popularizeRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            popularizeRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("获取海报失败![popularizeReq="+popularizeReq+"][comUser="+comUser+"]");
            e.printStackTrace();
        }

        return popularizeRes;
    }
}
