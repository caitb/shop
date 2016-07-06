package com.masiis.shop.web.platform.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserMaterielApplication;
import com.masiis.shop.web.material.service.ComUserMaterielApplicationService;
import com.masiis.shop.web.platform.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by jiajinghao on 2016/7/6.
 * 添加线下物料controller
 */
@Controller
@RequestMapping("/materielApply")
public class UserMaterielApplicationController extends BaseController {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ComUserMaterielApplicationService comUserMaterielApplicationService;

    /**
     * jjh
     * 申请线下物料
     *
     * @param request
     * @param email
     * @return
     */
    @RequestMapping(value = "/addEmail.do")
    @ResponseBody
    public String addMaterielApplicationToEmail(HttpServletRequest request,
                                                @RequestParam(value = "email", required = true) String email) {
        JSONObject object = new JSONObject();
        try {
            ComUser comUser = getComUser(request);
            ComUserMaterielApplication record = new ComUserMaterielApplication();
            record.setCreateTime(new Date());
            record.setModifyTime(new Date());
            record.setUserMail(email);
            record.setStatus(0);
            record.setUserId(comUser.getId());
            record.setRemark("");
            comUserMaterielApplicationService.addMaterielApplication(record);
            object.put("isError",false);
        } catch (Exception e) {
            log.info(e.getMessage());
            object.put("isError",true);
        }
        return object.toJSONString();
    }
}
