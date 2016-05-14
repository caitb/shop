package com.masiis.shop.web.mall.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.user.SfUserAccountMapper;
import com.masiis.shop.dao.mall.user.SfUserExtractApplyMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfUserAccount;
import com.masiis.shop.dao.po.SfUserExtractApply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Service
public class SfUserExtractApplyService {

    private final Logger logger = Logger.getLogger(SfUserExtractApplyService.class);
    @Autowired
    private SfUserExtractApplyMapper sfUserExtractApplyMapper;
    @Autowired
    private SfUserAccountMapper sfUserAccountMapper;
    /**
     * 用户提现申请处理
     * @param exMoney
     * @param user
     * @throws Exception
     * @author:wbj
     */
    @Transactional
    public void applyExtract(BigDecimal exMoney, ComUser user, SfUserAccount userAccount) throws Exception{
        logger.info("用户提现申请处理");
        SfUserExtractApply apply = new SfUserExtractApply();
        apply.setComUserId(user.getId());
        apply.setExtractFee(exMoney);
        apply.setExtractwayInfoId(Long.valueOf(0));//分销用户暂时没有提现方式设置，填写固定值0
        apply.setApplyTime(new Date());
        apply.setExtractWay(1);   //默认设置为微信提现
        apply.setAuditType(0);      //设置为待审核状态
        apply.setAuditCause("分销用户提现申请");
        apply.setRemark("分销用户提现申请");
        userAccount.setAppliedFee(userAccount.getAppliedFee() == null?new BigDecimal(0):userAccount.getAppliedFee().add(exMoney));
        if (sfUserExtractApplyMapper.insert(apply) == 0){
            throw new BusinessException("插入用户提现记录表失败");
        }
        if (sfUserAccountMapper.updateByPrimaryKey(userAccount) == 0){
            throw new BusinessException("更新分销用户账户表失败");
        }
    }

    /**
     * 根据用户和日期查询提现记录数量
     * @param comUser
     * @param beginDate
     * @param endDate
     * @return
     * @author:wbj
     */
    public Integer findCountByUserAndDate(ComUser comUser,Date beginDate,Date endDate){
        return sfUserExtractApplyMapper.selectCountByUserAndDate(comUser.getId(),beginDate,endDate);
    }

    /**
     * 根据用户和日期查询提现记录
     * @param comUser
     * @param beginDate
     * @param endDate
     * @param currentPage   当前页
     * @param pageSize      页面数量
     * @return
     * 用户提现申请处理
     */
    public List<SfUserExtractApply> findListByUserAndDate(ComUser comUser,Date beginDate,Date endDate,Integer currentPage,Integer pageSize){
        if (currentPage == 0||currentPage == 0){
            return sfUserExtractApplyMapper.selectListByUserAndDate(comUser.getId(),beginDate,endDate);
        }
        PageHelper.startPage(currentPage,pageSize);
        return sfUserExtractApplyMapper.selectListByUserAndDate(comUser.getId(),beginDate,endDate);
    }

    /**
     * 通过userId查询提现成功的金额
     * @param userId
     * @return
     */
    public Map<String,BigDecimal> selectextractFeeByUserId(Long userId){
        return sfUserExtractApplyMapper.selectextractFeeByUserId(userId);
    }
}
