package com.masiis.shop.admin.service.promotion;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.common.enums.platform.OperationType;
import com.masiis.shop.common.enums.promotion.SfGOrderPayStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.po.PbOperationLog;
import com.masiis.shop.dao.po.PbUser;
import com.masiis.shop.dao.po.SfGorder;
import com.masiis.shop.dao.po.SfGorderFreight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Date;

/**
 * Created by hzz on 2016/7/12.
 */
@Service
@Transactional
public class PromotionGorderService {

    private final static Log log = LogFactory.getLog(PromotionGorderService.class);

    @Resource
    private SfGorderService gorderService;
    @Resource
    private SfGorderOperationLogService gorderOperationLogService;
    @Resource
    private SfGorderFreightService gorderFreightService;
    @Resource
    private PbOperationLogMapper pbOperationLogMapper;

    public void deliveryGift(SfGorderFreight gorderFreight,PbUser pbUser)throws Exception{
        //修改订单的状态
        SfGorder sfGorder = gorderService.deliveryGiftAndUpdate(gorderFreight);
        //插入订单日志
        gorderOperationLogService.addGorderOperationLog(sfGorder.getUserId(),sfGorder.getId(),"update",SfGOrderPayStatusEnum.ORDER_PAID.getCode(),sfGorder.getGorderStatus(),"奖品发货修改订单状态，订单完成");
        //插入运单表
        gorderFreight.setCreateTime(new Date());
        gorderFreightService.insert(gorderFreight);
        //插入平台后台操作日志表
        PbOperationLog pbOperationLog = new PbOperationLog();
        pbOperationLog.setOperateIp(InetAddress.getLocalHost().getHostAddress());
        pbOperationLog.setCreateTime(new Date());
        pbOperationLog.setPbUserId(pbUser.getId());
        pbOperationLog.setPbUserName(pbUser.getUserName());
        pbOperationLog.setOperateType(OperationType.Update.getCode());
        pbOperationLog.setRemark("发货");
        pbOperationLog.setOperateContent(gorderFreight.toString());
        int updateByPrimaryKey = pbOperationLogMapper.insert(pbOperationLog);
        if(updateByPrimaryKey==0){
            throw new BusinessException("日志新建领取奖品发货失败!");
        }
    }
}
