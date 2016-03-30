package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.po.ComUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by hzz on 2016/3/30.
 */
@Service
public class UserIdentityAuthService {

    @Resource
    private UserService userService;

    /**
     * 提交实名认证审核
     * @author hanzengzhi
     * @date 2016/3/30 15:39
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public int sumbitAudit(HttpServletRequest request, ComUser comUser,String idCardFrontUrl,String idCardBackUrl){
        try{
            String rootPath = request.getServletContext().getRealPath("/");
            String webappPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
            String frontFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardFrontUrl);
            String backFillFullName = uploadFile(webappPath + "/static/upload/user/idCard/" + idCardBackUrl);
            //修改用户数据
            comUser.setIdCardFrontUrl(frontFillFullName);
            comUser.setIdCardBackUrl(backFillFullName);
            comUser.setIsVerified(0);
            comUser.setAuditStatus(1);
            int i = userService.updateComUser(comUser);
            if (i == 1){
                //更新缓存
                request.getSession().removeAttribute("comUser");
                request.getSession().setAttribute("comUser", comUser);
            }
            return i;
        }catch (Exception e){
            throw new BusinessException("");
        }
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
}
