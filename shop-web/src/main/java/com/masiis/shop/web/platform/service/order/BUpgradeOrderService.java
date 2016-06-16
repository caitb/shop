package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.OrderMakeUtils;
import com.masiis.shop.dao.beans.order.BOrderUpgradeDetail;
import com.masiis.shop.dao.po.*;
import com.masiis.shop.web.platform.service.product.SkuAgentService;
import com.masiis.shop.web.platform.service.product.SkuService;
import com.masiis.shop.web.platform.service.user.ComAgentLevelService;
import com.masiis.shop.web.platform.service.user.PfUserRecommendRelationService;
import com.masiis.shop.web.platform.service.user.UserService;
import com.masiis.shop.web.platform.service.user.UserSkuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 升级订单的确认和支付
 */
@Service
@Transactional
public class BUpgradeOrderService {

    private final static Logger log = Logger.getLogger(BUpgradeOrderService.class);

    @Resource
    private PfUserUpgradeNoticeService noticeService;
    @Resource
    private SkuService comSkuService;
    @Resource
    private UserSkuService userSkuService;
    @Resource
    private SkuAgentService skuAgentService;
    @Resource
    private ComAgentLevelService comAgentLevelService;
    @Resource
    private PfUserRecommendRelationService recommendRelationService;
    @Resource
    private PfBorderRecommenRewardService recommenRewardService;
    @Resource
    private BOrderService bOrderService;
    @Resource
    private BOrderOperationLogService orderOperationLogService;
    @Resource
    private UserService comUserService;



    /**
     * 订单界面获得升级通知信息
     * @param id   通知单id
     */
    public BOrderUpgradeDetail getUpgradeNoticeInfo(Long id){
        //升级订单通知信息
        if (id==null){
            throw new BusinessException("升级通知信息id为null");
        }
        log.info("获取升级通知信息------start");
        log.info("获取升级通知信息------id----"+id);
        PfUserUpgradeNotice upgradeNotice = noticeService.selectByPrimaryKey(id);
        BOrderUpgradeDetail upgradeDetail = null;
        if (upgradeNotice!=null){
            //验证条件是否可以进入
            if (true){
                upgradeDetail = new BOrderUpgradeDetail();
                ComUser oldComUser = comUserService.getUserById(upgradeNotice.getUserPid());
                if (oldComUser!=null){
                    upgradeDetail.setOldPUserId(upgradeNotice.getUserPid());
                    upgradeDetail.setOldPUserName(oldComUser.getRealName());
                }
                //商品信息
                ComSku comSku = getComSku(upgradeNotice.getSkuId());
                if (comSku!=null){
                    upgradeDetail.setSkuId(comSku.getId());
                    upgradeDetail.setSpuId(comSku.getSpuId());
                    upgradeDetail.setSkuName(comSku.getName());
                    upgradeDetail.setPriceRetail(comSku.getPriceRetail());
                }else{
                    log.info("商品信息为null-----id--"+upgradeNotice.getSkuId());
                    throw new BusinessException("商品信息为null-----id--"+upgradeNotice.getSkuId());
                }
                //级别
                PfSkuAgent oldSkuAgent = getPfSkuAgent(comSku.getId(),upgradeNotice.getOrgAgentLevelId());
                PfSkuAgent newSkuAgent = getPfSkuAgent(comSku.getId(),upgradeNotice.getWishAgentLevelId());
                if (oldSkuAgent!=null){
                    ComAgentLevel oldAgentLevel = getComAgentLeveal(oldSkuAgent.getAgentLevelId());
                    upgradeDetail.setCurrentAgentLevel(newSkuAgent.getAgentLevelId());
                    upgradeDetail.setCurrentAgentLevelName(oldAgentLevel.getName());
                }
                if (newSkuAgent!=null){
                    ComAgentLevel newAgentLevel = getComAgentLeveal(newSkuAgent.getAgentLevelId());
                    upgradeDetail.setApplyAgentLevel(newSkuAgent.getAgentLevelId());
                    upgradeDetail.setApplyAgentLevelName(newAgentLevel.getName());
                }
                //拿货数量
                upgradeDetail.setQuantity(newSkuAgent.getQuantity());
                //保证金差额
                upgradeDetail.setBailChange(getBailChange(oldSkuAgent,newSkuAgent));
                //总价
                BigDecimal totalPrice = getTotalPrice(oldSkuAgent,newSkuAgent);
                upgradeDetail.setTotalPrice(totalPrice);
            }else{
                log.info("升级通知信息状态不正确-----");
                throw new BusinessException("升级通知信息状态不正确-----");
            }
        }else{
            log.info("升级通知信息为null------id----"+id);
        }
        log.info("获取升级通知信息------end");
        return upgradeDetail;
    }
    /**
     * 支付成功后查询订单信息
     * @param orderId
     */
    public BOrderUpgradeDetail getUpgradeOrderInfo(Long orderId){
        log.info("支付成功后查询订单信息----start");
        //根据订单查询通知单号
        log.info("订单号-------"+orderId);
        PfUserUpgradeNotice upgradeNotice = noticeService.selectByPfBorderId(orderId);
        Long newPUserId = null;
        BOrderUpgradeDetail upgradeDetail = null;
        if (upgradeNotice!=null){
            log.info("通知单号-------"+upgradeNotice.getId());
            upgradeDetail = getUpgradeNoticeInfo(upgradeNotice.getId());
            if (upgradeDetail!=null){
                PfBorder pfBorder = bOrderService.getPfBorderById(orderId);
                if (pfBorder!=null){
                    log.info("新上级id--------"+pfBorder.getUserPid());
                    ComUser comUser = comUserService.getUserById(pfBorder.getUserPid());
                    if (comUser!=null){
                        upgradeDetail.setNewPUserId(comUser.getId());
                        upgradeDetail.setNewPUserName(comUser.getRealName());
                    }else{
                        log.info("新上级不存在-----");
                    }
                }
            }else {
                log.info("升级通知不存在------订单号----"+orderId);
                throw new BusinessException("升级通知不存在------订单号----"+orderId);
            }
        }
        log.info("支付成功后查询订单信息----end");
        return upgradeDetail;
    }

    /**
     * 生成订单数据
     * @param id
     */
    public void insertOrderInfo(Long id){
        log.info("生成订单数据----start");
        BOrderUpgradeDetail upgradeDetail = getUpgradeNoticeInfo(id);
        if (upgradeDetail!=null){
            ComUser comUser = null;
            //插入订单表
            PfSkuAgent newSkuAgent = getPfSkuAgent(upgradeDetail.getSkuId(),upgradeDetail.getApplyAgentLevel());
            PfBorder border = insertPfBorder(upgradeDetail,comUser,newSkuAgent);
            //插入订单子表
            PfBorderItem orderItem = insertBOrderItem(border,upgradeDetail,comUser,newSkuAgent);
            //插入订单操作日志
            insertBorderOperationLog(border);
            //插入合伙人订单推荐奖励表
            insertBorderRecommenReward(border,orderItem);
        }

        log.info("生成订单数据----end");
    }
    /**
     * 插入订单表
     * @param upgradeDetail
     * @param comUser
     * @param newSkuAgent
     * @return
     */
    private PfBorder  insertPfBorder(BOrderUpgradeDetail upgradeDetail,ComUser comUser,PfSkuAgent newSkuAgent){
        PfBorder pfBorder = new PfBorder();
        pfBorder.setCreateTime(new Date());
        pfBorder.setOrderCode(OrderMakeUtils.makeOrder("B"));
        pfBorder.setUserId(getNewPUserId(upgradeDetail,comUser));//上级id
        pfBorder.setUserMessage("");
        pfBorder.setSupplierId(0);//平台给供应商id
        pfBorder.setModifyTime(new Date());
        //商品折扣后价格
        BigDecimal unitPrice = newSkuAgent.getUnitPrice();
        //商品总金额=商品微信销售价*折扣*数量
        BigDecimal productAmount = unitPrice.multiply(BigDecimal.valueOf(newSkuAgent.getQuantity()));
        //订单总金额=商品总金额+保证金差额
        BigDecimal orderAmount = productAmount.add(upgradeDetail.getBailChange());
        pfBorder.setReceivableAmount(orderAmount);
        pfBorder.setOrderAmount(orderAmount);//订单费用
        pfBorder.setBailAmount(upgradeDetail.getBailChange()); //保证金
        pfBorder.setProductAmount(productAmount);//商品总费用
        pfBorder.setRecommenAmount(getRewardPrice(newSkuAgent.getQuantity(),pfBorder.getUserPid(),upgradeDetail.getSkuId()));//推荐奖励
        pfBorder.setShipAmount(new BigDecimal(0));
        pfBorder.setPayAmount(new BigDecimal(0));
        pfBorder.setShipType(0);//配送类型
        pfBorder.setSendType(1);//拿货方式
        pfBorder.setOrderType(3);//订单类型
        pfBorder.setOrderStatus(0);//订单状态
        pfBorder.setShipStatus(0);//物流状态
        pfBorder.setPayStatus(0);//支付状态
        pfBorder.setIsCounting(0);//是否结算
        pfBorder.setIsShip(0);//是否发货
        pfBorder.setIsReceipt(0);//收货标识位
        pfBorder.setRemark("升级插入");
        int i = bOrderService.insertPfBorder(pfBorder);
        if (i!=1){
            throw new BusinessException("升级插入订单失败");
        }
        return pfBorder;
    }

    /**
     * 插入订单商品子表
     * @param order
     * @param upgradeDetail
     * @param comUser
     * @param newSkuAgent
     * @return
     */
    private PfBorderItem insertBOrderItem(PfBorder order,BOrderUpgradeDetail upgradeDetail,ComUser comUser,PfSkuAgent newSkuAgent){
        PfBorderItem orderItem = new PfBorderItem();
        orderItem.setCreateTime(new Date());
        orderItem.setPfBorderId(order.getId());
        orderItem.setSpuId(upgradeDetail.getSpuId());
        orderItem.setSkuId(upgradeDetail.getSkuId());
        orderItem.setSkuName(upgradeDetail.getSkuName());
        orderItem.setAgentLevelId(upgradeDetail.getApplyAgentLevel());
        orderItem.setWxId(comUser.getWxId());
        orderItem.setQuantity(order.getTotalQuantity());
        orderItem.setOriginalPrice(upgradeDetail.getPriceRetail());//销售价格
        orderItem.setUnitPrice(newSkuAgent.getUnitPrice());//购买价格
        orderItem.setTotalPrice(order.getProductAmount());//总价
        orderItem.setBailAmount(order.getBailAmount());//保证金
        orderItem.setIsComment(0);
        orderItem.setIsReturn(0);
        orderItem.setRemark("升级插入");
        int i = bOrderService.insertPfBorderItem(orderItem);
        if (i!=1){
            throw new BusinessException("升级插入orderItem失败");
        }
        return orderItem;
    }

    /**
     * 插入订单操作日志表
     * @param order
     */
    private void insertBorderOperationLog(PfBorder order){
        orderOperationLogService.insertBOrderOperationLog(order,"升级插入");
    }

    /**
     * 插入合伙人订单推荐奖励表
     * @param order
     * @param orderItem
     */
    private void insertBorderRecommenReward(PfBorder order,PfBorderItem orderItem){
        PfBorderRecommenReward recommenReward = new PfBorderRecommenReward();
        recommenReward.setCreateTime(new Date());
        recommenReward.setPfBorderId(order.getId());
        recommenReward.setPfBorderItemId(orderItem.getId());
        recommenReward.setSkuId(orderItem.getSkuId());
        //推荐人
        PfUserRecommenRelation recommenRelation = getRecommenUserInfo(order.getUserId(),orderItem.getSkuId());
        if (recommenRelation!=null){
            recommenReward.setRecommenUserId(recommenRelation.getUserPid());
        }
        recommenReward.setQuantity(orderItem.getQuantity());
        //奖励单价
        //新上级的 pfUserSku表中的推荐单价
        recommenReward.setRewardUnitPrice(getRewardUnitPrice(order.getUserPid(),orderItem.getSkuId()));
        recommenReward.setRewardTotalPrice(order.getRecommenAmount());//奖励总金额
        recommenReward.setRemark("升级插入");
        int i = recommenRewardService.insert(recommenReward);
        if (i!=1){
            throw new BusinessException("升级插入合伙人订单推荐奖励表失败");
        }
    }


    private PfUserRecommenRelation getRecommenUserInfo(Long userId,Integer skuId){
       return recommendRelationService.selectRecommenRelationByUserIdAndSkuId(userId,skuId);
    }

    /**
     * 获得新上级
     * @param upgradeDetail
     * @param comUser
     * @return
     */
    private Long getNewPUserId(BOrderUpgradeDetail upgradeDetail,ComUser comUser){
        Integer applyAgentLevel = upgradeDetail.getApplyAgentLevel();
        Long newPUserId = null;
        //查询上级的等级
        PfUserSku userSku = null;
        PfUserSku pUserSku = null;
        Integer pUserAgentLevel = null;
        try {
            userSku = userSkuService.getUserSkuByUserIdAndSkuId(comUser.getId(),upgradeDetail.getSkuId());
            if (userSku!=null){
                pUserSku = userSkuService.getUserSkuByUserIdAndSkuId(Long.parseLong(userSku.getPid()+""),upgradeDetail.getSkuId());
                if (pUserSku!=null){
                    pUserAgentLevel = pUserSku.getAgentLevelId();
                }else{
                    log.info("查询父级userSku为null----父级id---"+userSku.getUserPid());
                    throw new BusinessException("查询父级userSku为null----父级id---"+userSku.getUserPid());
                }
            }else {
                log.info("查询usersku为null-----userId-----"+userSku.getUserId());
                throw new BusinessException("查询usersku为null-----userId-----"+userSku.getUserId());
            }
        }catch (Exception e){
            log.info("获得userSku出错-----"+e.getMessage());
            throw new BusinessException("获得userSku出错-----"+e.getMessage());
        }
        if (pUserAgentLevel-applyAgentLevel<0){
            //直接升级  上级的等级大于用户要升级的等级
            newPUserId = pUserSku.getUserId();
        }else if (pUserAgentLevel-applyAgentLevel==0){
            //平级升级   上级的等级=用户要升级的等级
            newPUserId = pUserSku.getUserPid();
        }else {
            log.info("申请登记超过了上级的等级-----skuId---"+upgradeDetail.getSkuId()+"----当前等级----"+upgradeDetail.getCurrentAgentLevel());
            log.info("----升级等级----"+upgradeDetail.getApplyAgentLevel());
            throw new BusinessException("申请登记超过了上级的等级");
        }
        return newPUserId;
    }

    /**
     * 获得推荐总价
     * @param quantity  商品数量
     * @param pUserId   border中的新上级id
     * @param skuId    商品id
     * @return
     */
    private BigDecimal getRewardPrice(Integer quantity,Long pUserId,Integer skuId){
        log.info("获得商品总价----quantity----"+quantity+"----pUserId----"+pUserId+"-----skuId---"+skuId);
        BigDecimal rewardUnitPrice = getRewardUnitPrice(pUserId,skuId);
        log.info("推荐单价-----"+rewardUnitPrice);
        BigDecimal rewardPrice = null;
        if (rewardUnitPrice!=null){
            rewardPrice =  rewardUnitPrice.multiply(BigDecimal.valueOf(quantity));
        }else{
            rewardPrice = BigDecimal.ZERO;
        }
        log.info("推荐总价------"+rewardPrice.intValue());
        return  rewardPrice;
    }

    /**
     *  获得推荐单价
     * @param pUserId   border里新上级puserId
     * @param skuId     商品的skuId
     * @return
     */
    private BigDecimal getRewardUnitPrice(Long pUserId,Integer skuId){
        BigDecimal rewardUnitPrice = null;
        try {
            PfUserSku userSku = userSkuService.getUserSkuByUserIdAndSkuId(pUserId,skuId);
            if (userSku!=null){
                rewardUnitPrice = userSku.getRewardUnitPrice();
            }else {
                log.info("获得奖励单价出错----pfuserSku为null---userId--"+pUserId+"----skuId--"+skuId);
                throw new BusinessException("获得奖励单价出错----pfuserSku为null---userId--"+pUserId+"----skuId--"+skuId);
            }
        }catch (Exception e){
            log.info("获得奖励单价出错----"+e.getMessage());
            throw new BusinessException("获得奖励单价出错----"+e.getMessage());
        }
        return rewardUnitPrice;
    }



    /**
     * 商品信息
     * @param skuId
     * @return
     */
    private ComSku getComSku (Integer skuId){
        return comSkuService.getSkuById(skuId);
    }

    /**
     * 获得商品代理等级信息
     * @param skuId
     * @param levelId
     * @return
     */
    private PfSkuAgent getPfSkuAgent(Integer skuId,Integer levelId){
        log.info("获得商品代理等级信息----商品skuId----"+skuId+"-----等级id----"+levelId);
        return skuAgentService.getBySkuIdAndLevelId(skuId,levelId);
    }
    /**
     * 获得等级信息
     * @param levelId
     * @return
     */
    private ComAgentLevel getComAgentLeveal(Integer levelId){
        log.info("获得等级信息----等级id-----"+levelId);
        return comAgentLevelService.selectByPrimaryKey(levelId);
    }


    /**
     * 获取订单的总价  = 新代理产品总价（数量*单价） + 保证金差额
     * @param oldSkuAgent
     * @param newSkuAgent
     * @return
     */
    private BigDecimal getTotalPrice(PfSkuAgent oldSkuAgent,PfSkuAgent newSkuAgent){
        BigDecimal totalPice = null;
        if (oldSkuAgent!=null&&newSkuAgent!=null){
            BigDecimal bailChange = getBailChange(oldSkuAgent,newSkuAgent);
            totalPice = newSkuAgent.getTotalPrice().add(bailChange);
        }else{
            totalPice = BigDecimal.ZERO ;
        }
        log.info("总价------"+totalPice.intValue());
        return totalPice;
    }

    /**
     * 获取保证金的差额
     * @param oldSkuAgent  旧代理商品
     * @param newSkuAgent  新代理商品
     * @return
     */
    private BigDecimal getBailChange(PfSkuAgent oldSkuAgent,PfSkuAgent newSkuAgent){
        if (oldSkuAgent!=null&&newSkuAgent!=null){
            log.info("保证金差额-------"+newSkuAgent.getBail().subtract(oldSkuAgent.getBail()).intValue());
            return newSkuAgent.getBail().subtract(oldSkuAgent.getBail());
        }else{
            log.info("获取保证金差额失败");
            throw new BusinessException("获取保证金差额失败");
        }
    }

}
