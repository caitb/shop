package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.*;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.*;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.swing.plaf.nimbus.NimbusStyle;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
@Service
@Transactional
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

    /**
     * 添加订单
     *
     * @param pfBorder
     * @param pfBorderItems
     */
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

}
