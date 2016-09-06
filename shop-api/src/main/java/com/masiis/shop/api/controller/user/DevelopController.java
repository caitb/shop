package com.masiis.shop.api.controller.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseBusinessRes;
import com.masiis.shop.api.bean.family.DevelopOrganizationRes;
import com.masiis.shop.api.bean.user.*;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.util.*;
import com.masiis.shop.dao.platform.user.PfUserOrganizationMapper;
import com.masiis.shop.web.common.utils.NetFileUtils;
import com.masiis.shop.web.common.service.SkuService;
import com.masiis.shop.web.platform.service.qrcode.WeiXinPFQRCodeService;
import com.masiis.shop.api.utils.image.DownloadImage;
import com.masiis.shop.api.utils.image.DrawImageUtil;
import com.masiis.shop.api.utils.image.Element;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.user.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by cai_tb on 16/5/19.
 */
@Controller
@RequestMapping("/develop")
public class DevelopController {

    private final static Log log = LogFactory.getLog(DevelopController.class);

    private static final String organizationLogoUrlRoot = PropertiesUtils.getStringValue("organization_img_url");
    private static final String developPosterDefault = PropertiesUtils.getStringValue("develop_poster_default");

    @Resource
    private UserSkuService userSkuService;
    @Resource
    private SkuService skuService;
    @Resource
    private AgentLevelService agentLevelService;
    @Resource
    private CertificateService certificateService;
    @Resource
    private WeiXinPFQRCodeService weiXinPFQRCodeService;
    @Resource
    private SkuExtensionService skuExtensionService;
    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;
    @Resource
    private DevelopService developService;


    @RequestMapping("/listAgentSku")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public AgentSkuRes listAgentSku(HttpServletRequest request, BaseBusinessReq bbq, ComUser comUser){
        AgentSkuRes agentSkuRes = new AgentSkuRes();

        try {
           /* List<PfUserSku> userSkus = userSkuService.listByUserId(comUser.getId());

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
            }*/

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
            DownloadImage.download(weiXinPFQRCodeService.createAgentQRCode(comUser.getId(), userSku.getSkuId(), "")[1], qrcodeName, posterDirPath);
            OSSObjectUtils.downloadFile("static/user/background_poster/"+skuExtension.getPoster(), posterDirPath+"/"+bgPoster);

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

            popularizeRes.setPosterUrl(PropertiesUtils.getStringValue("oss.BASE_URL") + "/static/user/poster/agent-"+comUser.getId()+"-"+userSku.getId()+".png");
            popularizeRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            popularizeRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e) {
            log.error("获取海报失败![popularizeReq="+popularizeReq+"][comUser="+comUser+"]");
            e.printStackTrace();
        }

        return popularizeRes;
    }


    @RequestMapping("/listOrganization")
    @ResponseBody
    @SignValid(paramType = BaseBusinessReq.class)
    public DevelopOrganizationRes listOrganization(HttpServletRequest request, ComUser comUser) {
        DevelopOrganizationRes res = new DevelopOrganizationRes();

        try {
            List<Map<String,Object>> orgs = pfUserOrganizationMapper.listCreateAndJoinOrganization(comUser.getId());

            for(Map<String,Object> map : orgs) {
                String orgLogo = (String) map.get("orgLogo");
                if(StringUtils.isNotBlank(orgLogo)) {
                    map.put("orgLogo",organizationLogoUrlRoot+orgLogo);
                }
            }

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setOrganWrapList(orgs);
        } catch(Exception e) {
            e.printStackTrace();
            res.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            res.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);
        }

        return res;
    }

    @RequestMapping("/poster")
    @ResponseBody
    @SignValid(paramType = DevelopPosterReq.class)
    public BaseBusinessRes poster(HttpServletRequest request,
                     HttpServletResponse response,
                     DevelopPosterReq req,
                     ComUser comUser) throws Exception {

        BaseBusinessRes bbr = new BaseBusinessRes();

        try{
            List<Map<String,Object>> orgMaps = pfUserOrganizationMapper.listCreateAndJoinOrganization(comUser.getId());

            if(orgMaps==null || orgMaps.size()==0) {
                new RuntimeException("没有代理任何品牌");
            }

            // 如果没有传 brandId, 则随机选一个
            Integer orgId = null;
            PfUserOrganization org = null;

            if(req.getBrandId() == null) {
                for(Map<String,Object> map : orgMaps) {
                    orgId = (Integer) map.get("orgId");
                    if(orgId != null) {
                        break;
                    }
                }
            } else {
                for(Map<String,Object> map : orgMaps) {
                    if(req.getBrandId().equals(map.get("brandId"))) {
                        orgId = (Integer) map.get("orgId");
                    }
                }
            }

            org = pfUserOrganizationMapper.selectByPrimaryKey(orgId);

            BufferedImage bufferedImage = null;
            if(org == null) {
                bufferedImage = NetFileUtils.getBufferedImage(developPosterDefault);        // 如果还没有设置组织（家族，团队），则返回默认图片
            } else  {
                bufferedImage = developService.createDevelopPoster(org, comUser, request);  // 生成海报图片
            }

            ByteArrayOutputStream drawByteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", drawByteArrayOutputStream);
            byte[] bytes = drawByteArrayOutputStream.toByteArray();

            response.setContentType("image/gif"); //设置返回的文件类型
            OutputStream os = response.getOutputStream();
            os.write(bytes);

            os.flush();
            os.close();

            bbr.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            bbr.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
        } catch (Exception e){
            bbr.setResCode(SysResCodeCons.RES_CODE_NOT_KNOWN);
            bbr.setResMsg(SysResCodeCons.RES_CODE_NOT_KNOWN_MSG);

            log.error("获取海报出错了[req="+req+"]");
            e.printStackTrace();
        }

        return bbr;
    }


}

