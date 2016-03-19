package com.masiis.shop.admin.service.extract;

import com.masiis.shop.dao.beans.extract.ExtractApply;
import com.masiis.shop.dao.platform.user.ComUserAccountMapper;
import com.masiis.shop.dao.platform.user.ComUserExtractApplyMapper;
import com.masiis.shop.dao.po.ComUserAccount;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuechao
 * @Date 2016/3/18 12:13.
 */
@Service
public class ComUserExtractApplyService {

    @Resource
    private ComUserExtractApplyMapper comUserExtractApplyMapper;
    @Resource
    private ComUserAccountMapper comUserAccountMapper;


    public List<ExtractApply> getExtractApplyList() {
        return comUserExtractApplyMapper.getExtractApplyList();
    }

    public ComUserAccount findByUserId(Long comUserId) {
        return comUserAccountMapper.findByUserId(comUserId);
    }

    public ExtractApply findById(Long id) {
        return comUserExtractApplyMapper.findById(id);
    }

    public void pass(Long id) {
        comUserExtractApplyMapper.pass(id);
    }

    public void refuse(Long id) {
        comUserExtractApplyMapper.refuse(id);
    }

    public void pay(Long id) {
        comUserExtractApplyMapper.pay(id);
    }
}
