package com.masiis.shop.admin.controller.storage;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.admin.beans.storage.QueryUserByConditionRes;
import com.masiis.shop.admin.beans.storage.QueryUserSkuListRes;
import com.masiis.shop.admin.beans.storage.StorageBillAuditRes;
import com.masiis.shop.admin.beans.storage.StorageBillCreateRes;
import com.masiis.shop.admin.service.system.PbOperationLogService;
import com.masiis.shop.dao.beans.user.UserSkuInfo;
import com.masiis.shop.admin.service.storage.PbStorageBillItemService;
import com.masiis.shop.admin.service.storage.PbStorageBillService;
import com.masiis.shop.admin.service.user.ComUserService;
import com.masiis.shop.admin.service.user.PfUserSkuService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.po.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.org.mozilla.javascript.internal.EcmaError;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
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
    @Resource
    private PbOperationLogService pbOperationLogService;

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
            System.out.println(request.getParameter("state"));
            Map<String, Object> pageMap = billService.getStoragechangeList(pageNumber, pageSize, sortName, sortOrder, conditionMap);
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
    public StorageBillCreateRes createBill(@RequestParam("userId") Long userId,
                                           @RequestParam("billType") Integer billType,
                                           @RequestParam("skuIds") Integer[] skuIds,
                                           @RequestParam("skuNums") Integer[] skuNums,
                                           @RequestParam("skuRemarks") String[] skuRemarks,
                                           @RequestParam(name = "reason", required = false) String reason,
                                           HttpSession session,
                                           HttpServletRequest request){
        StorageBillCreateRes res = new StorageBillCreateRes();
        try{
            PbUser pbUser = (PbUser) session.getAttribute("pbUser");
            if(session.isNew() || pbUser == null){
                res.setResCode("unsign");
                res.setResMsg("用户未登录");
                throw new BusinessException("用户未登录");
            }
            if(userId == null || userId <= 0){
                log.error("变更人选择异常");
                res.setResMsg("变更人选择异常");
                throw new BusinessException("变更人选择异常");
            }
            if(billType == null || billType <= 0){
                log.error("单据类型异常");
                res.setResMsg("单据类型异常");
                throw new BusinessException("单据类型异常");
            }
            if(skuIds == null || skuNums == null
                    || skuIds.length <= 0 || skuNums.length <= 0 || skuIds.length != skuNums.length){
                log.error("商品选择异常");
                res.setResMsg("商品选择异常");
                throw new BusinessException("商品选择异常");
            }
            for(int i = 0; i < skuIds.length; i++){
                if(skuIds[i] == null || skuIds[i] <= 0
                        || skuNums[i] == null || skuNums[i] <= 0){
                    log.error("商品选择异常");
                    res.setResMsg("商品选择异常");
                    throw new BusinessException("商品选择异常");
                }
            }

            if(StringUtils.isBlank(reason) && reason.length() > 150){
                log.error("单据说明不能超过150字");
                res.setResMsg("单据说明不能超过150字");
                throw new BusinessException("单据说明不能超过150字");
            }

            PbStorageBill bill = billService
                    .createBillByUserAndTypeAndSkusAndReason(userId, billType, skuIds, reason, skuNums, skuRemarks, pbUser);
            // 增加后台管理系统操作日志
            PbOperationLog oLog = new PbOperationLog();
            oLog.setCreateTime(new Date());
            oLog.setOperateIp(request.getRemoteAddr());
            oLog.setOperateType(0);
            oLog.setPbUserId(pbUser.getId());
            oLog.setPbUserName(pbUser.getUserName());
            oLog.setOperateContent(bill.toString());
            oLog.setRemark("创建库存入库变更单");
            pbOperationLogService.add(oLog);

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
            log.error("获取会员信息详情失败![id= "+id+"]");
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }
        return json.toString();
    }

    @RequestMapping("/detailItem.do")
    @ResponseBody
    public Object detailItem(Long id){
        if (id == null) {
            throw new BusinessException("库存id不能为空");
        }
        JSONObject json = new JSONObject();
        try {
            List<Map<String, Object>> rows = billService.getStorageItemDetailList(id);
            json.put("state", "success");
            json.put("rows", rows);
        } catch (Exception e) {
            log.error("获取库存详情失败![id="+id+"]");
            e.printStackTrace();
            throw new BusinessException("网络错误", e);
        }
        return json.toString();
    }

    @RequestMapping("/audit.do")
    @ResponseBody
    public StorageBillAuditRes auditBill(@RequestParam("billId") Integer billId,
                                         @RequestParam(name = "auditRemark", required = false) String auditRemark,
                                         HttpServletRequest request,
                                         HttpSession session){
        StorageBillAuditRes res = new StorageBillAuditRes();
        try{
            PbUser pbUser = (PbUser) session.getAttribute("pbUser");
            if(session.isNew() || pbUser == null){
                res.setResCode("unsign");
                res.setResMsg("用户未登录");
                throw new BusinessException("用户未登录");
            }
            if(billId == null || billId <= 0){
                res.setResMsg("单据id错误");
                log.error("单据id错误");
                throw new BusinessException("单据id错误");
            }
            PbStorageBill bill = billService.findById(billId);
            if(bill == null){
                res.setResMsg("该单据不存在");
                log.error("该单据不存在");
                throw new BusinessException("该单据不存在");
            }
            billService.auditBill(pbUser, bill, auditRemark, request.getRemoteAddr());

        } catch (Exception e){
            res.setResCode("fail");
            if(StringUtils.isBlank(res.getResMsg())){
                res.setResMsg("网络错误");
            }
        }

        return res;
    }

    @RequestMapping("/handle.do")
    @ResponseBody
    public StorageBillAuditRes handleBill(@RequestParam("billId") Integer billId,
                                         @RequestParam(name = "handleRemark", required = false) String handleRemark,
                                         HttpServletRequest request,
                                         HttpSession session){
        StorageBillAuditRes res = new StorageBillAuditRes();
        try{
            PbUser pbUser = (PbUser) session.getAttribute("pbUser");
            if(session.isNew() || pbUser == null){
                res.setResCode("unsign");
                res.setResMsg("用户未登录");
                throw new BusinessException("用户未登录");
            }
            if(billId == null || billId <= 0){
                res.setResMsg("单据id错误");
                log.error("单据id错误");
                throw new BusinessException("单据id错误");
            }
            PbStorageBill bill = billService.findById(billId);
            if(bill == null){
                res.setResMsg("该单据不存在");
                log.error("该单据不存在");
                throw new BusinessException("该单据不存在");
            }

            billService.handleSubtractBill(pbUser, bill, handleRemark, request.getRemoteAddr());
        } catch (Exception e){
            res.setResCode("fail");
            if(e instanceof BusinessException){
                res.setResMsg(e.getMessage());
            }
            if(StringUtils.isBlank(res.getResMsg())){
                res.setResMsg("网络错误");
            }
        }

        return res;
    }

}
