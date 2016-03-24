package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.user.ComUserExtractApplyMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.ComUserExtractApply;
import com.masiis.shop.dao.po.ComUserExtractwayInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lzh on 2016/3/20.
 */
@Service
public class UserExtractApplyService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserExtractApplyMapper applyMapper;

    public void applyExtract(ComUserAccount account, BigDecimal exMoney, ComUser user, ComUserExtractwayInfo info){
        try{
            if(exMoney.compareTo(account.getExtractableFee()) > 0
                    || exMoney.compareTo(new BigDecimal(0)) <= 0){
                log.error("提现金额错误,超过可提现金额或者小于0,用户id:" + user.getId());
                throw new BusinessException("提现金额错误,超过可提现金额或者小于0");
            }
            ComUserExtractApply apply = new ComUserExtractApply();
            apply.setApplyTime(new Date());
            apply.setAuditType(0);
            apply.setBankCard(info.getBankCard());
            apply.setBankName(info.getBankName());
            apply.setCardOwnerName(info.getCardOwnerName());
            apply.setComUserId(info.getComUserId());
            apply.setDepositBankName(info.getDepositBankName());
            apply.setExtractFee(exMoney);
            apply.setExtractWay(info.getExtractWay());
            apply.setExtractwayInfoId(info.getId());
            applyMapper.insert(apply);
        } catch (Exception e) {
            log.error("提现申请业务操作失败," + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public List<ComUserExtractApply> findListByUserAndDate(ComUser user, Date start, Date end) {
        return applyMapper.selectByUserAndDate(user.getId(), start, end);
    }

    public Integer findNumsByUserAndDate(ComUser user, Date start, Date end) {
        return applyMapper.selectNumsByUserAndDate(user.getId(), start, end);
    }

    public List<ComUserExtractApply> findListByUserAndDateAndPageNum(ComUser user, Date start, Date end, Integer startNum, Integer qSize) {
        return applyMapper.selectByUserAndDateAndPageNum(user.getId(), start, end, startNum, qSize);
    }
}
