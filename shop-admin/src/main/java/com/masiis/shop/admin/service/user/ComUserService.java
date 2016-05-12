package com.masiis.shop.admin.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.user.User;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.*;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ComUser toAudit(Long id){
        ComUser comUser = comUserMapper.selectByPrimaryKey(id);
        String idCardImgUrl = PropertiesUtils.getStringValue("index_user_idCard_url");

        comUser.setIdCardFrontUrl(idCardImgUrl+comUser.getIdCardFrontUrl());
        comUser.setIdCardBackUrl(idCardImgUrl+comUser.getIdCardBackUrl());

        return comUser;
    }

    /**
     * 审核会员信息
     * @param comUser
     */
    public void audit(ComUser comUser){
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
        }
    }

    public String findByPid(Integer pid) {
        return comUserMapper.findByPid(pid);
    }
}
