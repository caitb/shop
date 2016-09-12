package com.masiis.shop.admin.controller.agentsetup;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masiis.shop.admin.beans.product.SkuAgentModel;
import com.masiis.shop.admin.service.product.AgentLevelService;
import com.masiis.shop.admin.service.product.SkuAgentService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cai_tb on 16/8/20.
 */
@Controller
@RequestMapping("/skuAgent")
public class SkuAgentController {

    private final static Log log = LogFactory.getLog(SkuAgentController.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private SkuService skuService;
    @Resource
    private AgentLevelService agentLevelService;

    @RequestMapping("/list.shtml")
    public String list(){
        return "agentsetup/skuAgentList";
    }

    @RequestMapping("/add.shtml")
    public String add(Model model, Integer skuId) throws JsonProcessingException {
        ComSku comSku = skuService.findById(skuId);
        List<PfSkuAgent> pfSkuAgents = skuAgentService.listBySkuId(skuId);
        List<ComAgentLevel> agentLevels = agentLevelService.listAll();

        model.addAttribute("comSku", comSku);
        model.addAttribute("pfSkuAgents", pfSkuAgents);
        model.addAttribute("isSetup", pfSkuAgents!=null&&pfSkuAgents.size()>0 ? true : false);
        model.addAttribute("agentLevels", objectMapper.writeValueAsString(agentLevels));
        model.addAttribute("agentLevels2", agentLevels);
        return "agentsetup/add";
    }

    @RequestMapping("edit.shtml")
    public String edit(Model model, Integer skuId){

        PfSkuAgent skuAgent = skuAgentService.loadById(skuId);
        List<ComSku> skus = skuService.listByCondition(null);
        List<ComAgentLevel> agentLevels = agentLevelService.listAll();

        model.addAttribute("skuAgent", skuAgent);
        model.addAttribute("skus", skus);
        model.addAttribute("agentLevels", agentLevels);

        return "agentsetup/add";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(
                       Integer pageNumber,
                       Integer pageSize,
                       String sortName,
                       String sortOrder
    ) {
        Map<String, Object> conditionMap = new HashMap<>();

        Map<String, Object> pageMap = null;
        try {
            pageMap = skuAgentService.listByCondition(pageNumber, pageSize, sortName, sortOrder, conditionMap);
        } catch (Exception e) {
            log.error("获取商品代理列表失败!" + e);
            e.printStackTrace();
        }

        return pageMap;
    }

    @RequestMapping("/add.do")
    @ResponseBody
    public Object add(SkuAgentModel skuAgentModel) {
        Map<String, Object> resultMap = new HashMap<>();

        try {

            skuAgentService.save(skuAgentModel.getPfSkuAgents());

            resultMap.put("code", "success");
            resultMap.put("msg", "设置成功!");
        } catch (Exception e) {
            resultMap.put("code", "error");
            resultMap.put("msg", "商品代理等级设置失败!");
            log.error("商品代理等级设置失败![skuAgentModel="+skuAgentModel+"]");
            e.printStackTrace();
        }

        return resultMap;
    }
}
