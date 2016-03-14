package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
public class BOrderService {
    @Resource
    private PfBorderMapper pfBorderMapper;
    @Resource
    private PfBorderItemMapper borderItemMapper;
    @Resource
    private PfBorderConsigneeMapper pfBorderConsigneeMapper;
    @Resource
    private PfBorderPaymentMapper pfBorderPaymentMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private PfBorderOperationLogMapper pfBorderOperationLogMapper;
    @Resource
    private PfUserSkussMapper pfUserSkussMapper;
    @Resource
    private ComAgentLevelsMapper comAgentLevelsMapper;

    /**
     * 添加订单
     *
     * @param pfBorder
     * @param pfBorderItems
     */
    @Transactional
    public Long AddBOrder(PfBorder pfBorder, List<PfBorderItem> pfBorderItems, PfUserSku pfUserSku, ComUser comUser) throws Exception {
        if (pfBorder == null) {
            throw new BusinessException("pfBorder为空");
        }
        if (pfUserSku == null) {
            throw new BusinessException("pfUserSku为空");
        }
        if (pfBorderItems == null || pfBorderItems.size() == 0) {
            throw new BusinessException("pfBorderItems为空");
        }
        if (comUser == null) {
            throw new BusinessException("comUser为空");
        }
        //添加订单
        pfBorderMapper.insert(pfBorder);
        //添加订单商品
        for (PfBorderItem pfBorderItem : pfBorderItems) {
            pfBorderItem.setPfBorderId(pfBorder.getId());
            borderItemMapper.insert(pfBorderItem);
        }
        pfUserSku.setPfBorderId(pfBorder.getId());
        //添加用户代理商品关系
        pfUserSkuMapper.insert(pfUserSku);
        //完善用户信息
        comUserMapper.updateByPrimaryKey(comUser);
        //添加订单日志
        PfBorderOperationLog pfBorderOperationLog = new PfBorderOperationLog();
        pfBorderOperationLog.setCreateTime(new Date());
        pfBorderOperationLog.setPfBorderId(pfBorder.getId());
        pfBorderOperationLog.setCreateMan(comUser.getId());
        pfBorderOperationLog.setPfBorderStatus(0);
        pfBorderOperationLog.setRemark("新增订单");
        pfBorderOperationLogMapper.insert(pfBorderOperationLog);
        return pfBorder.getId();
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

    /**
     * 根据订单号获取订单商品
     *
     * @author ZhaoLiang
     * @date 2016/3/9 11:45
     */
    public List<PfBorderItem> getPfBorderItemByOrderId(Long pfBorderId) {
        return borderItemMapper.selectAllByOrderId(pfBorderId);
    }

    /**
     * 合伙人订单支付
     *
     * @param pfBorderPayment
     */
    @Transactional
    public void payBOrder(PfBorderPayment pfBorderPayment) {
        try {
            PfBorder pfBorder = new PfBorder();
            if (pfBorderPayment.getAmount() != pfBorder.getReceivableAmount()) {
                throw new BusinessException("支付金额异常");
            }
            pfBorder.setPayStatus(1);
            pfBorder.setPayAmount(pfBorder.getReceivableAmount());
            pfBorder.setPayTime(new Date());
            //修改订单状态
            pfBorderMapper.updateByPrimaryKey(pfBorder);
            //添加订单支付信息
            pfBorderPaymentMapper.insert(pfBorderPayment);
            PfUserSku pfUserSku = new PfUserSku();
            pfUserSku.setIsPay(1);
            //修改合伙人商品关系状态
            pfUserSkuMapper.updateByPrimaryKey(pfUserSku);
            ComUser comUser = new ComUser();
            comUser.setIsAgent(1);
            //修改用户为合伙人
            comUserMapper.updateByPrimaryKey(comUser);
        } catch (Exception e) {
        }
    }

    /**
     * 查用户商品关系表
     *
     * @author muchaofeng
     * @date 2016/3/9 18:12
     */
    public PfUserSku findPfUserSkuById(Long id) {
        return pfUserSkussMapper.selectPfUserSkusById(id);
    }

    /**
     * 获取合伙人等级
     *
     * @author muchaofeng
     * @date 2016/3/9 18:52
     */
    public ComAgentLevel findComAgentLevel(Integer id) {
        return comAgentLevelsMapper.selectByPrimaryKey(id);
    }
    /**
     * 根据订单号获取订单
     * @author muchaofeng
     * @date 2016/3/14 13:23
     */

    public PfBorder findByOrderCode(String orderId) {
        return pfBorderMapper.selectByOrderCode(orderId);
    }

    /**
     * 根据用户id获取订单
     * @author muchaofeng
     * @date 2016/3/14 13:22
     */

    public List<PfBorder> findByUserId(Long UserId) {
        return pfBorderMapper.selectByUserId(UserId);
    }

}
