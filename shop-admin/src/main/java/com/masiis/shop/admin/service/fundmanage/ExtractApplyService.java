package com.masiis.shop.admin.service.fundmanage;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.fundmanage.ExtractApply;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserExtractApplyMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractApply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现申请
 * Created by cai_tb on 16/3/31.
 */
@Service
public class ExtractApplyService {

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;
    @Resource
    private ComUserExtractApplyMapper comUserExtractApplyMapper;


    /**
     * 提现申请记录列表
     */
    public Map<String, Object> listByCondition(Integer pageNumber, Integer pageSize, Map<String, Object> con){
        PageHelper.startPage(pageNumber, pageSize);
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

    public void audit(ComUserExtractApply comUserExtractApply){
        comUserExtractApplyMapper.updateByPrimaryKey(comUserExtractApply);
    }
}
