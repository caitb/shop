package com.masiis.shop.admin.controller.agentsetup;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.product.AgentLevelService;
import com.masiis.shop.admin.service.product.SkuAgentService;
import com.masiis.shop.admin.service.product.SkuService;
import com.masiis.shop.dao.po.ComAgentLevel;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfSkuAgent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String add(Model model){
        List<ComSku> skus = skuService.listByCondition(null);
        List<ComAgentLevel> agentLevels = agentLevelService.listAll();

        model.addAttribute("skus", skus);
        model.addAttribute("agentLevels", agentLevels);
        return "agentsetup/add";
    }

    @RequestMapping("edit.shtml")
    public String edit(Model model, Integer id){

        PfSkuAgent skuAgent = skuAgentService.loadById(id);
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
    public Object add(PfSkuAgent pfSkuAgent) {
        Map<String, Object> resultMap = new HashMap<>();

        try {

            PfSkuAgent oldSkuAgent = skuAgentService.findBySkuIdAndLevelId(pfSkuAgent.getSkuId(), pfSkuAgent.getAgentLevelId());
            if(oldSkuAgent != null && pfSkuAgent.getId() == null){
                resultMap.put("code", "error");
                resultMap.put("msg", "此等级已设置了!");
                return resultMap;
            }

            skuAgentService.save(pfSkuAgent);

            resultMap.put("code", "success");
            resultMap.put("msg", "设置成功!");
        } catch (Exception e) {
            resultMap.put("code", "error");
            resultMap.put("msg", "商品代理等级设置失败!");
            log.error("商品代理等级设置失败![pfSkuAgent="+pfSkuAgent+"]");
            e.printStackTrace();
        }

        return resultMap;
    }
}
