package com.masiis.shop.admin.service.promotion;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.admin.service.gift.ComGiftService;
import com.masiis.shop.admin.utils.WxSFNoticeUtils;
import com.masiis.shop.common.enums.platform.OperationType;
import com.masiis.shop.common.enums.promotion.SfGOrderPayStatusEnum;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.system.PbOperationLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;

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
    @Resource
    private SfUserPromotionGiftService userPromotionGiftService;
    @Resource
    private ComGiftService comGiftService;
    @Resource
    private ComUserMapper comUserMapper;




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
        //发送通知
        sendWxNotice(sfGorder,gorderFreight);
    }
    private void sendWxNotice(SfGorder gorder,SfGorderFreight gorderFreight){
        log.info("领取奖品发货发送微信通知-----start");
        List<SfUserPromotionGift> userPromotionGifts =  userPromotionGiftService.getPromoGiftByPromoIdAndRuleId(gorder.getPromoId(),gorder.getPromoRuleId());
        String promotionGiftName = "";
        Integer promotionGiftQuantity = 0;
        if (userPromotionGifts!=null&&userPromotionGifts.size()!=0){
            SfUserPromotionGift userPromotionGift =  userPromotionGifts.get(0);
            ComGift comGift = comGiftService.getComGiftById(userPromotionGift.getGiftValue());
            if (comGift!=null){
                promotionGiftName = comGift.getName();
            }
            promotionGiftQuantity = userPromotionGift.getQuantity();
        }
        ComUser comUser = comUserMapper.selectByPrimaryKey(gorder.getUserId());
        String[] param = new String[5];
        param[0] = gorder.getGorderCode();
        param[1] = gorderFreight.getShipManName();
        param[2] = gorderFreight.getFreight();
        param[3] = promotionGiftName;
        param[4] = promotionGiftQuantity+"";
        Boolean bl = WxSFNoticeUtils.getInstance().activityOrderShipNotice(comUser,param,null);
        if (bl){
            log.info("领取奖品发货发送微信通知成功");
        }
        log.info("领取奖品发货发送微信通知-----end");
    }
}
