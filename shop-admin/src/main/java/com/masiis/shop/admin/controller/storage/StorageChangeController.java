package com.masiis.shop.admin.controller.storage;

import com.masiis.shop.admin.beans.storage.QueryUserByConditionRes;
import com.masiis.shop.admin.service.order.BOrderService;
import com.masiis.shop.admin.service.storage.PbStorageBillItemService;
import com.masiis.shop.admin.service.storage.PbStorageBillService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.PhoneNumUtils;
import com.masiis.shop.dao.po.PbStorageBill;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2016/7/19
 * @Author lzh
 */
@Controller
@RequestMapping("/storagechange")
public class StorageChangeController {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PbStorageBillService billService;
    @Resource
    private PbStorageBillItemService itemService;
    @Resource
    private ComUserService comUserService;

    @RequestMapping("/list.shtml")
    public String toList(){
        return "storage/list";
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public Object list(HttpServletRequest request, HttpServletResponse response,
                       Integer pageNumber, Integer pageSize, String sortName,
                       String sortOrder, Integer orderStatus){
        Map<String, Object> conditionMap = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(request.getParameter("orderCode"))){
                conditionMap.put("orderCode", request.getParameter("orderCode"));
            }
            if(StringUtils.isNotBlank(request.getParameter("startTime"))){
                conditionMap.put("startTime", request.getParameter("startTime"));
            }
            if(StringUtils.isNotBlank(request.getParameter("endTime"))){
                conditionMap.put("endTime", request.getParameter("endTime"));
            }
            Map<String, Object> pageMap = billService.storagechangeList(pageNumber, pageSize, sortName, sortOrder, conditionMap);
            return pageMap;
        } catch (Exception e) {
            log.error("查询库存变更单列表失败![conditionMap=" + conditionMap + "]");
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/create.shtml")
    public String toCreateStorageChangeBill(){
        return "storage/storagechange_create";
    }

    @RequestMapping("/quser.do")
    @ResponseBody
    public QueryUserByConditionRes queryUserByConditions(@RequestParam(required = false) String userPhone,
                                                         @RequestParam(required = false) String userWx){
        QueryUserByConditionRes res = new QueryUserByConditionRes();
        try{
            if(StringUtils.isBlank(userWx)){
                userWx = null;
            }
            if(StringUtils.isBlank(userPhone)){
                userPhone = null;
            }
            if(userPhone != null && !PhoneNumUtils.isPhoneNum(userPhone)){
                res.setResCode("fail");
                res.setResMsg("手机号格式不正确");
                throw new BusinessException("手机号格式不正确");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("mobile", userPhone);
            params.put("userWx", userWx);
            //List<>comUserService.queryByConditions(params);
        } catch (Exception e) {
            if(StringUtils.isBlank(res.getResMsg())){
                res.setResMsg("网络错误");
            }
        }

        return res;
    }
}
