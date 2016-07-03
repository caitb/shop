package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfBorderItemMapper;
import com.masiis.shop.dao.platform.order.PfBorderMapper;
import com.masiis.shop.dao.po.ComUserAccount;
import com.masiis.shop.dao.po.PfBorder;
import com.masiis.shop.dao.po.PfBorderItem;
import com.masiis.shop.dao.po.PfBorderRecommenReward;
import com.masiis.shop.web.platform.service.user.ComUserAccountService;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
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
    @Resource
    private PfBorderRecommenRewardService pfBorderRecommenRewardService;
    @Resource
    private PfBorderItemMapper pfBorderItemMapper;

    /**
     * 修改结算中数据
     *
     * @param orderId
     */
    public void orderBillAmount(Long orderId) {
        PfBorder pfBorder = getPfBorderById(orderId);
        logger.info("---合伙金额结算------------------------------------start");
        updateDisBillAmount(pfBorder);
        logger.info("---合伙金额结算-----------------------------------end");
        logger.info("---推荐奖励结算------------------------------------start");
        updateRecommenBillAmount(pfBorder);
        logger.info("---推荐奖励结算-----------------------------------end");
    }

    /**
     * 更新用户账户合伙结算中
     *
     * @author hanzengzhi
     * @date 2016/6/7 9:59
     */
    private void updateDisBillAmount(PfBorder order) {
        logger.info("更新用户账户合伙结算中------start");
        ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(order.getUserPid());
        BigDecimal ordAmount = order.getOrderAmount();
        BigDecimal bailAmount = order.getBailAmount();
        BigDecimal recommenAmount = order.getRecommenAmount();
        if (comUserAccount != null) {
            logger.info("结算中-----之前----" + comUserAccount.getAgentBillAmount());
            if (comUserAccount.getAgentBillAmount() != null) {
                comUserAccount.setAgentBillAmount(comUserAccount.getAgentBillAmount().add(ordAmount.subtract(bailAmount).subtract(recommenAmount)));
            } else {
                comUserAccount.setAgentBillAmount(ordAmount.subtract(bailAmount).subtract(recommenAmount));
            }
            logger.info("结算中-----之后----" + comUserAccount.getAgentBillAmount());
            logger.info("结算中-----增加----" + ordAmount.subtract(bailAmount));
            int i = comUserAccountService.updateByIdWithVersion(comUserAccount);
            if (i != 1) {
                throw new BusinessException("更新结算中数据失败-----用户id-----" + order.getUserPid());
            }
        }
        logger.info("更新用户账户合伙结算中------end");
    }

    /**
     * 更新用户账户推荐奖励结算中
     *
     * @param pfBorder
     */
    private void updateRecommenBillAmount(PfBorder pfBorder) {
        //奖励为0不需要处理
        logger.info("更新用户账户推荐奖励结算中------start");
        for (PfBorderItem pfBorderItem : pfBorderItemMapper.selectAllByOrderId(pfBorder.getId())) {
            //获取推荐奖励明细
            PfBorderRecommenReward pfBorderRecommenReward = pfBorderRecommenRewardService.getByPfBorderItemId(pfBorderItem.getId());
            //获取推荐人账户信息
            if (pfBorderRecommenReward != null && pfBorderRecommenReward.getRecommenUserId() != 0) {
                ComUserAccount comUserAccount = comUserAccountService.findAccountByUserid(pfBorderRecommenReward.getRecommenUserId());
                if (comUserAccount != null) {
                    logger.info("账户：" + pfBorderRecommenReward.getRecommenUserId() + "结算中-----之前----" + comUserAccount.getRecommenBillAmount());
                    comUserAccount.setRecommenBillAmount(comUserAccount.getRecommenBillAmount().add(pfBorderRecommenReward.getRewardTotalPrice()));
                    logger.info("账户：" + pfBorderRecommenReward.getRecommenUserId() + "结算中-----之后----" + comUserAccount.getRecommenBillAmount());
                    int i = comUserAccountService.updateByIdWithVersion(comUserAccount);
                    if (i != 1) {
                        throw new BusinessException("更新结算中数据失败-----用户id-----" + pfBorder.getUserPid());
                    }
                } else {
                    logger.info("推荐人账户为空");
                }
            }
        }
        logger.info("更新用户账户推荐奖励结算中------end");
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
