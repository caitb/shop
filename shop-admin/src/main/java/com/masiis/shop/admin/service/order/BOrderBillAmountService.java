package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.user.ComUserAccountService;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfBorder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by hzz on 2016/6/9.
 */
@Service
@Transactional
public class BOrderBillAmountService {

    private final static Logger logger = Logger.getLogger(BOrderBillAmountService.class);

    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private ComUserAccountService comUserAccountService;

    public void orderBillAmount(Long orderId){
        PfBorder pfBorder = getPfBorderById(orderId);
        logger.info("---结算------------------------------------start");
        updateDisBillAmount(pfBorder);
        logger.info("---结算-----------------------------------end");
    }
    /**
     * 更新用户账户结算中
     * @author hanzengzhi
     * @date 2016/6/7 9:59
     */
    private void updateDisBillAmount(PfBorder order){
        logger.info("更新用户账户结算中------start");
        ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(order.getUserPid());
        BigDecimal ordAmount = order.getOrderAmount();
        BigDecimal bailAmount = order.getBailAmount();
        if (comUserAccount != null){
            logger.info("结算中-----之前----"+comUserAccount.getAgentBillAmount());
            if (comUserAccount.getAgentBillAmount()!=null){
                comUserAccount.setAgentBillAmount(comUserAccount.getAgentBillAmount().add(ordAmount.subtract(bailAmount)));
            }else{
                comUserAccount.setAgentBillAmount(ordAmount.subtract(bailAmount));
            }
            logger.info("结算中-----之后----"+comUserAccount.getAgentBillAmount());
            logger.info("结算中-----增加----"+ordAmount.subtract(bailAmount));
            int i = comUserAccountService.updateByIdWithVersion(comUserAccount);
            if(i!=1){
                throw new BusinessException("更新结算中数据失败-----用户id-----"+order.getUserPid());
            }
        }
        logger.info("更新用户账户结算中------end");
    }
    /**
     * 获取订单
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:07
     */
    public PfBorder getPfBorderById(Long id) {
        return pfBorderMapper.selectByPrimaryKey(id);
    }
}
