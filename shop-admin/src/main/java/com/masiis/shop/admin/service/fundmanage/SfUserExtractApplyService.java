package com.masiis.shop.admin.service.fundmanage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.fundmanage.ExtractApply;
import com.masiis.shop.admin.service.wx.WxPayUserService;
import com.masiis.shop.admin.utils.WxSFNoticeUtils;
import com.masiis.shop.common.enums.BOrder.OperationType;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractApplyMapper;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

/**
 * Created by cai_tb on 16/4/18.
 */
@Service
@Transactional
public class SfUserExtractApplyService {

    @Resource
    private SfUserExtractApplyMapper sfUserExtractApplyMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfUserAccountMapper sfUserAccountMapper;
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;

    @Resource
    private WxPayUserService wxPayUserService;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> conditionMap){
        PageHelper.startPage(pageNumber, pageSize, "apply_time desc");
        List<SfUserExtractApply> sfUserExtractApplies = sfUserExtractApplyMapper.selectByMap(conditionMap);
        PageInfo<SfUserExtractApply> pageInfo = new PageInfo<>(sfUserExtractApplies);

        Map<String, ComUser> userMap = new HashMap<>();
        Map<String, SfUserAccount> accountMap = new HashMap<>();
        List<ExtractApply> extractApplies = new ArrayList<>();
        for(SfUserExtractApply sue : sfUserExtractApplies){
            ComUser comUser = userMap.get("id_"+sue.getComUserId());
            SfUserAccount account = accountMap.get("id_"+sue.getComUserId());

            if(comUser == null){
                comUser = comUserMapper.selectByPrimaryKey(sue.getComUserId());
                userMap.put("id_"+sue.getComUserId(), comUser);
            }
            if(account == null){
                account = sfUserAccountMapper.selectByUserId(sue.getComUserId());
                accountMap.put("id_"+sue.getComUserId(), account);
            }

            ExtractApply extractApply = new ExtractApply();
            extractApply.setComUser(comUser);
            extractApply.setSfUserAccount(account);
            extractApply.setSfUserExtractApply(sue);

            extractApplies.add(extractApply);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", extractApplies);

        return pageMap;
    }

    public void audit(Long id, Integer auditType, String auditCause, String rootPath,PbUser pbUser)throws Exception{
        SfUserExtractApply sfUserExtractApply = sfUserExtractApplyMapper.selectByPrimaryKey(id);
        SfUserAccount sfUserAccount = sfUserAccountMapper.selectByUserId(sfUserExtractApply.getComUserId());

        if(auditType.intValue() == 1){
            double applied_fee = sfUserAccount.getAppliedFee().doubleValue() - sfUserExtractApply.getExtractFee().doubleValue();
            if(applied_fee >= 0){
                sfUserAccount.setAppliedFee(new BigDecimal(applied_fee));
                sfUserAccountMapper.updateByPrimaryKey(sfUserAccount);
            }
        }

        if(auditType.intValue() == 3){
            wxPayUserService.payUserByExtractApply(sfUserExtractApply, null, rootPath);
            return;
        }

        sfUserExtractApply.setAuditType(auditType);
        sfUserExtractApply.setAuditCause(auditCause);
        sfUserExtractApplyMapper.updateByPrimaryKey(sfUserExtractApply);

        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setPbUserId(pbUser.getId());
        pbOperationLog.setPbUserName(pbUser.getUserName());
        pbOperationLog.setOperateType(OperationType.Update.getCode());
        pbOperationLog.setRemark("消费者提现");
        pbOperationLog.setOperateContent(pbOperationLog.toString());
        int updateByPrimaryKey = pbOperationLogMapper.insert(pbOperationLog);
        if(updateByPrimaryKey==0){
            throw new Exception("日志新建消费者提现失败!");
        }
    }

    public void sendWxNotice(Long id) {
        SfUserExtractApply apply = sfUserExtractApplyMapper.selectByPrimaryKey(id);
        ComUser user = comUserMapper.selectByPrimaryKey(apply.getComUserId());
        String[] params = new String[4];

        params[0] = user.getRealName();
        params[1] = apply.getExtractFee().toString();
        params[3] = DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        if(apply.getExtractWay().intValue() == 1){
            params[2] = "微信零钱";
        }else if(apply.getExtractWay().intValue() == 3){
            String cardNum = apply.getBankCard();
            params[2] = apply.getBankName() + ":" + cardNum.substring(0, 3) + " **** **** " + cardNum.substring(cardNum.length() - 4);
        }

        WxSFNoticeUtils.getInstance().extractResultNotice(user, params);
    }
}
