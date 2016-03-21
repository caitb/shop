package com.masiis.shop.web.platform.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.beans.certificate.CertificateInfo;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserCertificate;
import com.masiis.shop.dao.po.PfUserSku;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.UserCertificateService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import com.masiis.shop.web.platform.utils.UploadImage;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * UserCertificateController
 *
 * @author ZhaoLiang
 * @date 2016/3/10
 */
@Controller
@RequestMapping("/userCertificate")
public class UserCertificateController {

    @Resource
    private UserSkuService userSkuService;
    @Resource
    private UserService userService;
    @Resource
    private SkuService skuService;
    @Resource
    private UserCertificateService userCertificateService;

    /**
     * @author ZhaoLiang
     * @date 2016/3/10 18:37
     */
    @RequestMapping("/setUserCertificate.shtml")
    public ModelAndView setUserCertificate(HttpServletRequest request,
                                           @RequestParam(value = "userSkuId", required = true) Integer userSkuId
    ) {
        ModelAndView modelAndView = new ModelAndView();
        String url = PropertiesUtils.getStringValue("index_user_idCard_url");
        try {
            PfUserSku pfUserSku = userSkuService.getUserSkuById(userSkuId);
            ComUser comUser = userService.getUserById(pfUserSku.getUserId());
            ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
            modelAndView.addObject("userSkuId", pfUserSku.getId());
            modelAndView.addObject("skuName", comSku.getName());
            modelAndView.addObject("name", comUser.getRealName());
            modelAndView.addObject("idCard", comUser.getIdCard());
            modelAndView.addObject("idCardFrontUrl", StringUtils.isBlank(comUser.getIdCardFrontUrl()) ? "" : url + comUser.getIdCardFrontUrl());
            modelAndView.addObject("idCardBackUrl", StringUtils.isBlank(comUser.getIdCardBackUrl()) ? "" : url + comUser.getIdCardBackUrl());
            modelAndView.setViewName("platform/user/tijiaosq");
        } catch (Exception ex) {

        }
        return modelAndView;
    }

    /**
     * 上传身份证正反面
     *
     * @author ZhaoLiang
     * @date 2016/3/11 11:54
     */
    @ResponseBody
    @RequestMapping("/idCardImgUpload.do")
    public String imgUpload(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(value = "idCardImg", required = true) MultipartFile idCardImg
    ) throws IOException {

        JSONObject object = new JSONObject();
        try {
            String rootPath = request.getServletContext().getRealPath("/");
            String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
            String savepath = "/static/upload/user/idCard/";
            String realpath = webappPath + savepath;
            String imgPath = UploadImage.upload(idCardImg, realpath);
            if (StringUtils.isBlank(imgPath)) {
                object.put("code", "0");
                object.put("msg", "");
                object.put("imgPath", "");
            } else {
                object.put("code", "1");
                object.put("msg", "上传成功");
                object.put("imgPath", savepath + imgPath);// java.net.URLEncoder.encode(savepath + imgPath, "UTF-8")
            }
        } catch (Exception e) {
            object.put("code", "99");
            object.put("msg", e.toString());
            object.put("imgPath", "");
        }
        return object.toJSONString();
    }

    /**
     * 授权书申请
     *
     * @author ZhaoLiang
     * @date 2016/3/11 13:58
     */
    @ResponseBody
    @RequestMapping("/add.do")
    public String userCertificateAdd(HttpServletRequest request,
                                     @RequestParam(value = "userSkuId", required = true) Integer userSkuId,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "idCard", required = true) String idCard,
                                     @RequestParam(value = "idCardFrontUrl", required = true) String idCardFrontUrl,
                                     @RequestParam(value = "idCardBackUrl", required = true) String idCardBackUrl
    ) {
        JSONObject object = new JSONObject();
        try {
            if (StringUtils.isBlank(name)) {
                throw new BusinessException("姓名不能为空");
            }
            if (StringUtils.isBlank(idCard)) {
                throw new BusinessException("身份证不能为空");
            }
            if (StringUtils.isBlank(idCardFrontUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            if (StringUtils.isBlank(idCardBackUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            PfUserSku pfUserSku = userSkuService.getUserSkuById(userSkuId);
            if (pfUserSku == null) {
                throw new BusinessException("代理信息有误");
            }
            String rootPath = request.getServletContext().getRealPath("/");
            String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
            String frontFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardFrontUrl);
            String backFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardBackUrl);
            //修改用户数据
            ComUser comUser = userService.getUserById(pfUserSku.getUserId());
            comUser.setIdCard(idCard);
            comUser.setIdCardFrontUrl(frontFillFullName);
            comUser.setIdCardBackUrl(backFillFullName);
            //创建证书申请数据
            PfUserCertificate pfUserCertificate = new PfUserCertificate();
            pfUserCertificate.setPfUserSkuId(pfUserSku.getId());
            pfUserCertificate.setCreateTime(new Date());
            pfUserCertificate.setCode("");
            pfUserCertificate.setUserId(comUser.getId());
            ComSku comSku = skuService.getSkuById(pfUserSku.getSkuId());
            pfUserCertificate.setSpuId(comSku.getSpuId());
            pfUserCertificate.setSkuId(pfUserSku.getSkuId());
            pfUserCertificate.setIdCard(idCard);
            pfUserCertificate.setMobile(comUser.getMobile());
            pfUserCertificate.setWxId(comUser.getWxId());
            pfUserCertificate.setBeginTime(new Date());
            Calendar calendar = Calendar.getInstance();
            Date date = new Date(System.currentTimeMillis());
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 2);
            date = calendar.getTime();
            pfUserCertificate.setEndTime(date);
            pfUserCertificate.setAgentLevelId(pfUserSku.getAgentLevelId());
            pfUserCertificate.setStatus(0);
            userService.insertUserCertificate(comUser, pfUserCertificate);
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * 上传文件
     *
     * @author ZhaoLiang
     * @date 2016/3/11 15:12
     */
    private String uploadFile(String filePath) throws FileNotFoundException {
        File frontFile = new File(filePath);
        OSSObjectUtils.uploadFile("mmshop", frontFile, "static/user/idCard/");
        return frontFile.getName();
    }
   
    /**
      * @Author 贾晶豪
      * @Date 2016/3/17 0017 下午 5:12
      * 个人的授权书列表
      */
    @RequestMapping(value = "/userList/{userId}")
    public ModelAndView userCertificate(HttpServletRequest request, HttpServletResponse response,
                                                   @PathVariable("userId") Integer userId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/certificateList");
        List<CertificateInfo> pfUserCertificates = userCertificateService.CertificateByUser(userId);
        mav.addObject("pfUserCertificates",pfUserCertificates);
        return mav;
    }
    /**
      * @Author 贾晶豪
      * @Date 2016/3/17 0017 下午 6:37
      * 个人商品证书详情
      */
    @RequestMapping(value = "/userct/{pfuId}")
    public ModelAndView userCertificateDetail(HttpServletRequest request, HttpServletResponse response,
                                        @PathVariable("pfuId") Integer pfuId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/cdetail");
        HttpSession session = request.getSession();
        ComUser comUser = (ComUser) session.getAttribute("comUser");
        PfUserCertificate cdetail= userCertificateService.CertificateDetailsByUser(pfuId);
        ComSku comSku = skuService.getSkuById(cdetail.getSkuId());
        mav.addObject("cdetail",cdetail);
        mav.addObject("comUser",comUser);
        mav.addObject("comSku",comSku);
        return mav;
    }
    /**
      * @Author 贾晶豪
      * @Date 2016/3/18 0018 下午 1:50
      * 等待申请
      */
    @RequestMapping(value = "/ready/{skuName}")
    @ResponseBody
    public ModelAndView ready(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable("skuName") String skuName) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/cready");
        mav.addObject("skuName",skuName);
        return mav;
    }
    /**
      * @Author 贾晶豪
      * @Date 2016/3/18 0018 下午 2:19
      * 审核失败详情
      */
    @RequestMapping(value = "/fail/{pfuId}")
    public ModelAndView userCertificateFailDetail(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable("pfuId") Integer pfuId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/ctfail");
        PfUserCertificate cdetail= userCertificateService.CertificateDetailsByUser(pfuId);
        ComSku comSku = skuService.getSkuById(cdetail.getSkuId());
        ComUser comuser = userService.getUserById(cdetail.getUserId());
        String ctValue = PropertiesUtils.getStringValue("index_user_idCard_url");
        comuser.setIdCardFrontUrl(ctValue + comuser.getIdCardFrontUrl());
        comuser.setIdCardBackUrl(ctValue + comuser.getIdCardBackUrl());
        mav.addObject("ctfaildetail",cdetail);
        mav.addObject("comUser",comuser);
        mav.addObject("comSku",comSku);
        return mav;
    }

    /**
     * 更新授权书信息
     * Jing Hao
     */
    @ResponseBody
    @RequestMapping("/update.do")
    public String userCertificateUpdate(HttpServletRequest request,
                                     @RequestParam(value = "userSkuId", required = true) Integer userSkuId,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(value = "wxh", required = true) String wxh,
                                     @RequestParam(value = "idCard", required = true) String idCard,
                                     @RequestParam(value = "idCardFrontUrl", required = true) String idCardFrontUrl,
                                     @RequestParam(value = "idCardBackUrl", required = true) String idCardBackUrl
    ) {
        JSONObject object = new JSONObject();
        try {
            if (StringUtils.isBlank(name)) {
                throw new BusinessException("姓名不能为空");
            }
            if (StringUtils.isBlank(idCard)) {
                throw new BusinessException("身份证号不能为空");
            }
            if (StringUtils.isBlank(idCard)) {
                throw new BusinessException("微信号不能为空");
            }
            if (StringUtils.isBlank(idCardFrontUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            if (StringUtils.isBlank(idCardBackUrl)) {
                throw new BusinessException("身份证照片不能为空");
            }
            PfUserSku pfUserSku = userSkuService.getUserSkuById(userSkuId);
            if (pfUserSku == null) {
                throw new BusinessException("代理信息有误");
            }
            String rootPath = request.getServletContext().getRealPath("/");
            String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
            String frontFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardFrontUrl);
            String backFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardBackUrl);
            //修改用户数据
            ComUser comUser = userService.getUserById(pfUserSku.getUserId());
            comUser.setIdCard(idCard);
            comUser.setWxId(wxh);
            comUser.setIdCardFrontUrl(frontFillFullName);
            comUser.setIdCardBackUrl(backFillFullName);
            //更新证书申请数据
            PfUserCertificate pfUserCertificate =userCertificateService.getCertificateBypfuId(pfUserSku.getId());
            pfUserCertificate.setPfUserSkuId(pfUserSku.getId());
            pfUserCertificate.setIdCard(idCard);
            pfUserCertificate.setMobile(comUser.getMobile());
            pfUserCertificate.setWxId(comUser.getWxId());
            pfUserCertificate.setStatus(0);
            userService.updateUserCertificate(comUser, pfUserCertificate);
            object.put("isError", false);
        } catch (Exception ex) {
            object.put("isError", true);
            object.put("message", ex.getMessage());
        }
        return object.toJSONString();
    }

    /**
     * 更新证书
     * Jing Hao
     * param :user_sku_id
     */
    @RequestMapping(value = "/add/{pfuId}")
    public ModelAndView addCertificate(HttpServletRequest request, HttpServletResponse response,
                                              @PathVariable("pfuId") Integer pfuId) throws Exception {
        ModelAndView mav = new ModelAndView("/platform/user/cready");
       try {
           userCertificateService.receiveCertificate(pfuId);
       }catch (Exception e){
          e.printStackTrace();
       }
      return mav;
    }
}
