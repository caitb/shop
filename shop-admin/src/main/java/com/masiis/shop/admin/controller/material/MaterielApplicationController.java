package com.masiis.shop.admin.controller.material;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.material.MaterielApplicationService;
import com.masiis.shop.dao.po.ComUserMaterielApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线下素材申请
 * Created by cai_tb on 16/7/7.
 */
@Controller
@RequestMapping("/materielApplication")
public class MaterielApplicationController {

    private final static Log log = LogFactory.getLog(MaterielApplicationController.class);

    @Resource
    private MaterielApplicationService materielApplicationService;

    @RequestMapping("/list.shtml")
    public String list() {
        return "material/materielApplication";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(
            Integer pageNumber,
            Integer pageSize,
            String sortName,
            String sortOrder
    ){
        Map<String, Object> conditionMap = new HashMap<>();
        Map<String, Object> pageMap = new HashMap<>();

        try {
            pageMap = materielApplicationService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取用户线下素材申请列表失败![conditionMap="+conditionMap+"]"+e);
            e.printStackTrace();
        }

        return pageMap;
    }

    /**
     * 确认发送
     * @param comUserMaterielApplication
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String, Object> update(ComUserMaterielApplication comUserMaterielApplication) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            comUserMaterielApplication.setModifyTime(new Date());
            materielApplicationService.updateById(comUserMaterielApplication);
            resultMap.put("code", "success");
            resultMap.put("msg", "更改成功!");
        } catch (Exception e) {
            resultMap.put("code", "fail");
            resultMap.put("msg", "更改失败!");
            log.error("确认发送失败![comUserMaterielApplication="+comUserMaterielApplication+"]");
            e.printStackTrace();
        }

        return resultMap;
    }
}
