package com.masiis.shop.admin.controller.storage;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.admin.beans.storage.QueryUserByConditionRes;
import com.masiis.shop.admin.beans.storage.QueryUserSkuListRes;
import com.masiis.shop.admin.beans.storage.StorageBillCreateRes;
import com.masiis.shop.dao.beans.user.UserSkuInfo;
import com.masiis.shop.admin.service.storage.PbStorageBillItemService;
import com.masiis.shop.admin.service.storage.PbStorageBillService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.admin.service.user.PfUserSkuService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.ComUser;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private PfUserSkuService pfUserSkuService;

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
            if(StringUtils.isNotBlank(request.getParameter("code"))){
                conditionMap.put("code", request.getParameter("code"));
            }
            if(StringUtils.isNotBlank(request.getParameter("realNamelike"))){
                conditionMap.put("realNamelike", "%" + request.getParameter("realNamelike") + "%");
            }
            if(StringUtils.isNotBlank(request.getParameter("mobilelike"))){
                conditionMap.put("mobilelike", "%" + request.getParameter("mobilelike") + "%");
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
                                                         @RequestParam(required = false) String userName){
        QueryUserByConditionRes res = new QueryUserByConditionRes();
        try{
            if(StringUtils.isBlank(userName)){
                userName = null;
            }
            if(StringUtils.isBlank(userPhone)){
                userPhone = null;
            }
            if(userPhone != null && !NumberUtils.isNumber(userPhone)){
                res.setResCode("fail");
                res.setResMsg("手机号格式不正确");
                throw new BusinessException("手机号格式不正确");
            }

            Map<String, Object> params = new HashMap<>();
            params.put("mobile", userPhone);
            params.put("userName", userName);
            List<ComUser> users = comUserService.queryByConditions(params);
            res.setResCode("success");
            res.setUsers(users);
        } catch (Exception e) {
            res.setResCode("fail");
            if(StringUtils.isBlank(res.getResMsg())){
                res.setResMsg("网络错误");
            }
        }

        return res;
    }

    @RequestMapping("/skulist.do")
    @ResponseBody
    public QueryUserSkuListRes queryUserSkuList(@RequestParam(required = true) Long userId){
        QueryUserSkuListRes res = new QueryUserSkuListRes();
        List<UserSkuInfo> skus = null;
        try{
            if(userId == null || userId.intValue() <= 0){
                res.setResCode("fail");
                res.setResMsg("用户id不正确");
                throw new BusinessException("用户id不正确");
            }
            ComUser user = comUserService.getUserById(userId);
            if(user == null){
                res.setResCode("fail");
                res.setResMsg("用户id不正确");
                throw new BusinessException("用户id不正确");
            }
            if(user.getIsAgent().intValue() != 1){
                res.setResCode("fail");
                res.setResMsg("该用户还不是合伙人");
                throw new BusinessException("该用户还不是合伙人");
            }

            skus = pfUserSkuService.findSkusByUserId(user.getId());

            res.setResCode("success");
            res.setSkus(skus);
        } catch (Exception e) {
            res.setResCode("fail");
            if(StringUtils.isBlank(res.getResMsg())){
                res.setResMsg("网络错误");
            }
        }

        return res;
    }

    @RequestMapping("/create.do")
    @ResponseBody
    public StorageBillCreateRes createBill(){
        StorageBillCreateRes res = new StorageBillCreateRes();
        try{


            res.setResCode("success");
        } catch (Exception e) {
            res.setResCode("fail");
            if(StringUtils.isBlank(res.getResMsg())){
                res.setResMsg("网络错误");
            }
        }

        return res;
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public Object detail(Long id){
        if (id == null) {
            throw new BusinessException("用户id不能为空");
        }
        JSONObject json = new JSONObject();
        try {
            ComUser user = comUserService.getUserById(id);
            if (user == null) {
                throw new BusinessException(id + ":没有该用户");
            }
            json.put("state", "success");
            json.put("user", user);
        } catch (Exception e) {
            log.error("获取会员信息详情失败![id="+id+"]");
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }
        return json.toString();
    }
}
