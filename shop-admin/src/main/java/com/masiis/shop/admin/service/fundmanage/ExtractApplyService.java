package com.masiis.shop.admin.service.fundmanage;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.fundmanage.ExtractApply;
import com.masiis.shop.admin.utils.WxPFNoticeUtils;
import com.masiis.shop.common.util.MobileMessageUtil;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserExtractApplyMapper;
import com.masiis.shop.dao.platform.user.ComUserExtractwayInfoMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractApply;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 提现申请
 * Created by cai_tb on 16/3/31.
 */
@Service
public class ExtractApplyService {

    private final static Log log = LogFactory.getLog(ExtractApplyService.class);

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;
    @Resource
    private ComUserExtractApplyMapper comUserExtractApplyMapper;
    @Resource
    private ComUserExtractwayInfoMapper comUserExtractwayInfoMapper;


    /**
     * 提现申请记录列表
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> con){
        PageHelper.startPage(pageNumber, pageSize, "apply_time desc");
        List<ComUserExtractApply> comUserExtractApplies = comUserExtractApplyMapper.selectByCondition(con);
        PageInfo<ComUserExtractApply> pageInfo = new PageInfo<>(comUserExtractApplies);

        Map<String, ComUser> userMap = new HashMap<>();
        Map<String, ComUserAccount> accountMap = new HashMap<>();
        List<ExtractApply> extractApplies = new ArrayList<>();
        for(ComUserExtractApply cue : comUserExtractApplies){
            ComUser comUser = userMap.get("id_"+cue.getComUserId());
            ComUserAccount account = accountMap.get("id_"+cue.getComUserId());

            if(comUser == null){
                comUser = comUserMapper.selectByPrimaryKey(cue.getComUserId());
                userMap.put("id_"+cue.getComUserId(), comUser);
            }
            if(account == null){
                account = comUserAccountMapper.findByUserId(cue.getComUserId());
                accountMap.put("id_"+cue.getComUserId(), account);
            }

            ExtractApply extractApply = new ExtractApply();
            extractApply.setComUser(comUser);
            extractApply.setComUserAccount(account);
            extractApply.setComUserExtractApply(cue);

            extractApplies.add(extractApply);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", extractApplies);

        return pageMap;
    }

    /**
     * 合伙人提现申请审核
     * @param comUserExtractApply
     */
    public void audit(ComUserExtractApply comUserExtractApply){
        int auditType = comUserExtractApply.getAuditType();
        String auditCause = comUserExtractApply.getAuditCause();

        comUserExtractApply = comUserExtractApplyMapper.selectByPrimaryKey(comUserExtractApply.getId());
        comUserExtractApply.setAuditType(auditType);
        comUserExtractApply.setAuditCause(auditCause);

        if(comUserExtractApply.getAuditType().intValue()==3){//打款业务
            ComUserAccount comUserAccount = comUserAccountMapper.findByUserId(comUserExtractApply.getComUserId());

            log.info("审核前的账户数据[comUserAccount="+comUserAccount+"]");
            log.info("审核前的申请提现数据[comUserExtractApply="+comUserExtractApply+"]");
            double accountExtractableFee = comUserAccount.getExtractableFee().doubleValue();  //可提现额度
            double appliedExtractableFee = comUserAccount.getAppliedFee().doubleValue();      //已申请提现额度
            double applyExtractableFee = comUserExtractApply.getExtractFee().doubleValue();   //申请提现额度
            if(accountExtractableFee-applyExtractableFee>=0 && appliedExtractableFee>=applyExtractableFee){
                comUserAccount.setExtractableFee(new BigDecimal(accountExtractableFee-applyExtractableFee));
                comUserAccount.setAppliedFee(new BigDecimal(appliedExtractableFee-applyExtractableFee));

                comUserAccountMapper.updateByPrimaryKey(comUserAccount);
                comUserExtractApplyMapper.updateByPrimaryKey(comUserExtractApply);

                log.info("审核后的账户数据[comUserAccount="+comUserAccount+"]");
                log.info("审核后的申请提现数据[comUserExtractApply="+comUserExtractApply+"]");
            }else{
                throw new RuntimeException("申请提现金额数据出错![comUserExtractApply="+comUserExtractApply+"]");
            }
        }else{
            comUserExtractApplyMapper.updateByPrimaryKey(comUserExtractApply);
        }

        //短信和微信通知
        ComUser comUser = comUserMapper.selectByPrimaryKey(comUserExtractApply.getComUserId());
        ComUserExtractwayInfo comUserExtractwayInfo = comUserExtractwayInfoMapper.selectByPrimaryKey(comUserExtractApply.getExtractwayInfoId());
        if(auditType == 1){
            MobileMessageUtil.getInitialization("B").withdrawVerifyRefuseAgent(comUser.getMobile(), auditCause);
        }
        if(auditType == 2){
            MobileMessageUtil.getInitialization("B").withdrawVerifyApproveAgent(comUser.getMobile(), "1", 3);
        }
        if(auditType == 1 || auditType == 2){
            WxPFNoticeUtils.getInstance().pfExtractApplySuccess(comUser,
                    new String[]{
                            comUser.getWxNkName(),
                            "￥"+comUserExtractApply.getExtractFee().toString(),
                            comUserExtractwayInfo.getBankName()+":"+comUserExtractwayInfo.getBankCard(),
                            new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss").format(new Date())
                    }
            );
        }
    }
}
