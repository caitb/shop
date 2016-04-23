package com.masiis.shop.admin.service.fundmanage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.fundmanage.ExtractApply;
import com.masiis.shop.admin.service.wx.WxPayUserService;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractApplyMapper;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.SfUserAccount;
import com.masiis.shop.dao.po.SfUserExtractApply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/18.
 */
@Service
public class SfUserExtractApplyService {

    @Resource
    private SfUserExtractApplyMapper sfUserExtractApplyMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfUserAccountMapper sfUserAccountMapper;

    @Resource
    private WxPayUserService wxPayUserService;

    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> conditionMap){
        PageHelper.startPage(pageNumber, pageSize);
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

    public void audit(Long id, Integer auditType, String auditCause, String rootPath){
        SfUserExtractApply sfUserExtractApply = sfUserExtractApplyMapper.selectByPrimaryKey(id);
        if(auditType.intValue() == 3){
            wxPayUserService.payUserByExtractApply(sfUserExtractApply, null, rootPath);
            return;
        }

        sfUserExtractApply.setAuditType(auditType);
        sfUserExtractApply.setAuditCause(auditCause);
        sfUserExtractApplyMapper.updateByPrimaryKey(sfUserExtractApply);
    }
}
