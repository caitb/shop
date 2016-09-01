package com.masiis.shop.api.controller.base;

import com.github.pagehelper.StringUtil;
import com.masiis.shop.api.bean.base.UploadFileReq;
import com.masiis.shop.api.bean.base.UploadFileRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.util.OSSObjectUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/uploadFile")
public class UploadFileController {

    @RequestMapping("")
    @ResponseBody
    @SignValid(paramType = UploadFileReq.class)
    public UploadFileRes upload(UploadFileReq req, MultipartFile file, HttpServletRequest request) {

        UploadFileRes res = new UploadFileRes();
        String rootDir = req.getRootDir();

        if(StringUtils.isBlank(rootDir)) {
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg("缺少参数：rootDir");
            res.setFilename(null);
        }

        if(file == null || file.getSize()<1) {
            res.setResCode(SysResCodeCons.RES_CODE_REQ_PARAMETER_MISTAKEN);
            res.setResMsg("无上传的文件：file");
            res.setFilename(null);
        }

        String fileName = RandomStringUtils.random(20, true, true);

        String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        if(StringUtils.isBlank(suffix)) {
            suffix = "png";
        }
        fileName = fileName+"."+suffix;


        try {
            OSSObjectUtils.uploadFile(fileName, file.getSize(), file.getInputStream(), rootDir);

            res.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
            res.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
            res.setFilename(fileName);
        } catch (IOException e) {
            e.printStackTrace();

            res.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
            res.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);
            res.setFilename(null);
        }

        return res;

    }

}
