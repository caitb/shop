package com.masiis.shop.admin.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.user.User;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.enums.BOrder.OperationType;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ComAgentLevelMapper;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by cai_tb on 16/3/5.
 */
@Service
public class ComUserService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;
    @Resource
    private ComUserAccountRecordMapper comUserAccountRecordMapper;
    @Resource
    private PfUserCertificateMapper pfUserCertificateMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PfUserRelationMapper pfUserRelationMapper;
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;
    @Resource
    private ComAgentLevelMapper comAgentLevelMapper;

    /**
     * 根据id查找合伙人
     * @param id
     * @return
     */
    public ComUser findById(Long id){
        return comUserMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询合伙人
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页记录数
     * @param comUser    查询条件
     * @return
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, ComUser comUser){

        PageHelper.startPage(pageNumber, pageSize, "create_time desc");
        List<ComUser> comUsers = comUserMapper.selectByCondition(comUser);
        PageInfo<ComUser> pageInfo = new PageInfo<>(comUsers);

        List<User> users = new ArrayList<>(comUsers.size());
        for(ComUser cu : comUsers){
            ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(cu.getId());

            User user = new User();
            user.setComUser(cu);
            user.setComUserAccount(comUserAccount);

            users.add(user);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", users);

        return pageMap;
    }

    /**
     * 获取待审核会员
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页记录数
     * @param comUser    查询条件
     * @return
     */
    public Map<String, Object> auditListByCondition(Integer pageNumber, Integer pageSize, ComUser comUser){
        PageHelper.startPage(pageNumber, pageSize);
        List<ComUser> comUsers = comUserMapper.auditList(comUser);
        PageInfo<ComUser> pageInfo = new PageInfo<>(comUsers);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", comUsers);

        return pageMap;
    }

    /**
     * 会员详细信息
     * @param id
     * @return
     */
    public User detail(Long id){
        ComUser comUser = comUserMapper.selectByPrimaryKey(id);
        ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(comUser.getId());
        //List<ComUserAccountRecord> comUserAccountRecords = comUserAccountRecordMapper.selectByUserId(comUser.getId());
        List<PfUserCertificate> pfUserCertificates = pfUserCertificateMapper.selectByUserId(comUser.getId());

        Map<String, Object> wxAgentPro = new HashMap<>();
        for(PfUserCertificate puc : pfUserCertificates){
            ComSku comSku = comSkuMapper.selectById(puc.getSkuId());
            wxAgentPro.put(puc.getWxId(), comSku.getName());
        }

        String idCardImgUrl = PropertiesUtils.getStringValue("index_user_idCard_url");
        comUser.setIdCardFrontUrl(idCardImgUrl+comUser.getIdCardFrontUrl());
        comUser.setIdCardBackUrl(idCardImgUrl+comUser.getIdCardBackUrl());

        User user = new User();
        user.setComUser(comUser);
        user.setComUserAccount(comUserAccount);
        user.setWxAgentPro(wxAgentPro);

        return user;
    }

    /**
     * 获取待审核人信息
     * @param id
     * @return
     */
    public Map<String, Object> toAudit(Long id){
        Map<String, Object> auditMap = new HashMap<>();

        /* 被审核人信息 */
        ComUser comUser = comUserMapper.selectByPrimaryKey(id);
        String idCardImgUrl = PropertiesUtils.getStringValue("index_user_idCard_url");
        comUser.setIdCardFrontUrl(idCardImgUrl+comUser.getIdCardFrontUrl());
        comUser.setIdCardBackUrl(idCardImgUrl+comUser.getIdCardBackUrl());

        /* 上级人信息 */
        List<Map<String, Object>> pUserMaps = new ArrayList<>();

        Map<String, Object> userRelationMap = new HashMap<>();
        userRelationMap.put("userId", comUser.getId());
        userRelationMap.put("isEnable", 1);
        List<PfUserRelation> pfUserRelations = pfUserRelationMapper.selectByCondition(userRelationMap);
        for(PfUserRelation pfUserRelation : pfUserRelations){
            PfUserCertificate pfUserCertificate = pfUserCertificateMapper.selectByUserIdAndSkuId(pfUserRelation.getUserPid(), pfUserRelation.getSkuId());
            ComUser pUser = comUserMapper.selectByPrimaryKey(pfUserCertificate.getUserId());
            ComAgentLevel agentLevel = comAgentLevelMapper.selectByPrimaryKey(pfUserCertificate.getAgentLevelId());
            ComSku sku = comSkuMapper.selectByPrimaryKey(pfUserCertificate.getSkuId());

            Map<String, Object> pUserMap = new HashMap<>();
            pUserMap.put("userName", pUser.getWxNkName());
            pUserMap.put("mobile", pUser.getMobile());
            pUserMap.put("agentLevelName", agentLevel.getName());
            pUserMap.put("agentSkuName", sku.getName());

            pUserMaps.add(pUserMap);
        }

        auditMap.put("comUser", comUser);
        auditMap.put("pUserMaps", pUserMaps);

        return auditMap;
    }

    /**
     * 审核会员信息
     * @param comUser
     */
    public void audit(ComUser comUser,PbUser pbUser) throws Exception{
        comUserMapper.updateByPrimaryKey(comUser);
        if(comUser.getAuditStatus()==2 || comUser.getAuditStatus()==3){
            MobileMessageUtil.getInitialization("B").certificationVerifyResult(comUser.getMobile(), comUser.getAuditStatus()==2?true:false);

            comUser = comUserMapper.selectByPrimaryKey(comUser.getId());
            String url = PropertiesUtils.getStringValue("web.domain.name.address")+"/index";
            PfUserRelation pfUserRelation = pfUserRelationMapper.selectLastRecordByUserId(comUser.getId());
            if(pfUserRelation != null){
                url = PropertiesUtils.getStringValue("web.domain.name.address")+"/product/skuDetails.shtml?skuId="+pfUserRelation.getSkuId();
            }
            WxPFNoticeUtils.getInstance().partnerRealNameAuthNotice(comUser,
                                                                    comUser.getAuditStatus()==2?true:false,
                                                                    comUser.getAuditStatus()==2?url:PropertiesUtils.getStringValue("web.domain.name.address")+"/identityAuth/toInentityAuthPage.html?defaultValue=3");
            PbOperationLog pbOperationLog = new PbOperationLog();
            pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
            pbOperationLog.setCreateTime(new Date());
            pbOperationLog.setPbUserId(pbUser.getId());
            pbOperationLog.setPbUserName(pbUser.getUserName());
            pbOperationLog.setOperateType(OperationType.Update.getCode());
            pbOperationLog.setRemark("实名认证");
            pbOperationLog.setOperateContent(comUser.toString());
            int updateByPrimaryKey = pbOperationLogMapper.insert(pbOperationLog);
            if(updateByPrimaryKey==0){
                throw new Exception("日志新建实名认证失败!");
            }
        }
    }

    public String findByPid(Integer pid) {
        return comUserMapper.findByPid(pid);
    }
}
