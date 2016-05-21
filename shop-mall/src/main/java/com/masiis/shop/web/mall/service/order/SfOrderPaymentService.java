package com.masiis.shop.web.mall.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.order.SfOrderPaymentMapper;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.common.beans.wxpay.WxPaySysParamReq;
import com.masiis.shop.web.mall.utils.WXBeanUtils;
import com.masiis.shop.dao.po.SfOrder;
import com.masiis.shop.dao.po.SfOrderConsignee;
import com.masiis.shop.dao.po.SfOrderItem;
import com.masiis.shop.dao.po.SfOrderOperationLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/4/10.
 */
@Service
public class SfOrderPaymentService {

    @Resource
    private SfOrderPaymentMapper sfOrderPaymentMapper;

    public int updateOrderPayment(SfOrderPayment orderPayment){
       return sfOrderPaymentMapper.updateByPrimaryKey(orderPayment);
    }

}
