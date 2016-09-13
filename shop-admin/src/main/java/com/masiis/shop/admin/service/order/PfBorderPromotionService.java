package com.masiis.shop.admin.service.order;

import com.masiis.shop.admin.service.product.PfSkuStockService;
import com.masiis.shop.admin.service.product.PfUserSkuStockService;
import com.masiis.shop.admin.service.product.SpuService;
import com.masiis.shop.common.enums.platform.*;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.platform.order.PfBorderPromotionMapper;
import com.masiis.shop.dao.po.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hzz on 2016/9/6.
 */
@Service
public class PfBorderPromotionService {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfBorderPromotionMapper pfBorderPromotionMapper;
    @Resource
    private PfUserSkuStockService userSkuStockService;
    @Resource
    private PfSkuStockService pfSkuStockService;
    @Resource
    private PfUserUpgradeNoticeService userUpgradeNoticeService;
    @Resource
    private SpuService spuService;

    public PfBorderPromotion getBorderPromotionsByBorderIdAndIsTake(Long pfBorderId,Integer isTake){
        return pfBorderPromotionMapper.getBorderPromotionsByBorderIdAndIsTake(pfBorderId,isTake);
    }

    public PfBorderPromotion getBorderPromotionsByBorderIdAndIsSend(Long pfBorderId,Integer isSend){
        return pfBorderPromotionMapper.getBorderPromotionsByBorderIdAndIsSend(pfBorderId,isSend);
    }
    public int update(PfBorderPromotion pfBorderPromotion){
        return pfBorderPromotionMapper.updateByPrimaryKey(pfBorderPromotion);
    }

    private String promotionStartDateString = "2000-10-10";
    private String promotionStartEndString  = "2010-10-10";
    private Integer giveSkuAgentLevel = 4;

    /**
     * 判断活动是否开启
     * @return
     */
    private Boolean isOpenPromotion(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYYMMDDFMT);
            Date startDate = sdf.parse(promotionStartDateString);
            Date endDate = sdf.parse(promotionStartEndString);
            Date currentDate = new Date();
            //如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
            if (DateUtil.compare(currentDate,startDate)>0){
                if (DateUtil.compare(currentDate,endDate)<0){
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 代理，补货，升级，购买，回收 更新平台赠送商品的库存的--入口
     * @param pfBorderId            B端订单(C端为null)
     * @param userId
     * @param skuId                 C端购买使用(B端可以为null)
     * @param spuId                 C端购买使用(B端可以为null)
     * @param mallSellQuantity      C端购买的量
     * @param changeGiveStockType   类型(B端传null)
     * @param orderType             B端订单类型(C端传null)
     */
    public void updateGivePlatformStock(Long pfBorderId,
                                        Long userId,
                                        Integer skuId,
                                        Integer spuId,
                                        Integer mallSellQuantity,
                                        PfBorderPromotionGiveStockChangeEnum changeGiveStockType,
                                        Integer orderType){
        if (isOpenPromotion()){
            log.info("代理，补货，升级，购买，回收 更新平台赠送商品的库存的--入口---start");
            log.info("入口参数------pfBorderId---"+pfBorderId+"---userId---"+userId+"---skuId----"+skuId+"----spuId---"+spuId);
            log.info("mallSellQuantity-----"+mallSellQuantity+"------orderType----"+orderType+"-----changeGiveStockType----"+changeGiveStockType);
            PfBorderPromotion pfBorderPromotion = null;
            PfUserSkuStock userSkuStock =  null;
            if (orderType!=null){
                switch (orderType){
                    case 0:
                        changeGiveStockType = PfBorderPromotionGiveStockChangeEnum.agent;
                        break;
                    case 1:
                        changeGiveStockType = PfBorderPromotionGiveStockChangeEnum.Supplement;
                        break;
                    case 2:
                        changeGiveStockType = PfBorderPromotionGiveStockChangeEnum.Take;
                        break;
                    case 3:
                        changeGiveStockType = PfBorderPromotionGiveStockChangeEnum.UPGRADE;
                        break;
                    default:
                        break;
                }
            }
            log.info("changeGiveStockType----"+changeGiveStockType.getCode());
            switch (changeGiveStockType){
                case agent:
                    if (skuId==null&&spuId==null){
                        pfBorderPromotion = getBorderPromotionsByBorderIdAndIsSend(pfBorderId, PfBorderPromotionIsSendEnum.NO_GiVE.getCode());
                    }
                    if (pfBorderPromotion!=null){
                        registAgentSuccessUpdateStockAndIsSend(pfBorderId,pfBorderPromotion,pfBorderPromotion.getSkuId(),pfBorderPromotion.getSpuId(),userId);
                    }
                    break;
                case Supplement:
                    break;
                case Take:
                    break;
                case UPGRADE:
                    PfUserUpgradeNotice pfUserUpgradeNotice = userUpgradeNoticeService.selectByPfBorderId(pfBorderId);
                    if (pfUserUpgradeNotice!=null){
                        if (pfUserUpgradeNotice.getOrgAgentLevelId().equals(giveSkuAgentLevel)){
                            log.info("原始等级是之前平台赠送商品的等级，需要修改赠送的商品库存");
                            ComSpu comSpu =  spuService.selectBrandBySkuId(pfUserUpgradeNotice.getSkuId());
                            if(comSpu!=null){
                                updateOwnStock(null,
                                        pfBorderId,
                                        null,
                                        userId,
                                        pfUserUpgradeNotice.getSkuId(),
                                        comSpu.getId(),
                                        PfBorderPromotionGiveStockChangeEnum.UPGRADE);
                            }
                        }
                    }
                    break;
                case sell:
                    userSkuStock =  userSkuStockService.selectByUserIdAndSkuIdAndSpuId(userId,skuId,spuId);
                    if (userSkuStock!=null&&userSkuStock.getRegisterGiveSkuStock()>0){
                        updateOwnStock(mallSellQuantity,
                                null,
                                userSkuStock,
                                userId,
                                skuId,
                                spuId,
                                PfBorderPromotionGiveStockChangeEnum.sell);
                    }
                    break;
                case recovery:
                    recoverySkuStock();
                    break;
                default:
                    break;
            }
            log.info("代理，补货，升级，购买，回收 更新平台赠送商品的库存的--入口---end");
        }

    }

    /**
     *  支付成功或发货后更新用户和平台库存以及订单是否发货的状态
     * @param pfBorderId
     * @param pfBorderPromotion
     * @param skuId
     * @param spuId
     * @param userId
     */
    private void registAgentSuccessUpdateStockAndIsSend(Long pfBorderId,PfBorderPromotion pfBorderPromotion,Integer skuId,Integer spuId,Long userId){
        log.info("支付成功或发货后更新用户和平台库存以及订单是否发货的状态----入口参数----pfBorderId---"+pfBorderId+"----skuId---"+skuId+"----spuId----"+spuId+"----userId----"+userId);
        log.info("-----pfBorderItem中quantity数量为0------");
        log.info("-----pfbodrerId-------"+pfBorderId);
        if (pfBorderPromotion==null){
            pfBorderPromotion = getBorderPromotionsByBorderIdAndIsTake(pfBorderId, PfBorderPromotionIsSendEnum.NO_GiVE.getCode());
        }
        log.info("更新订单促销活动赠品表中是否已发放商品----start");
        updateIsSend(pfBorderId,pfBorderPromotion);
        log.info("更新订单促销活动赠品表中是否已发放商品----end");
        log.info("支付成功发货后更新小白库存和平台库存----start");
        registAgentUpdateStock(pfBorderId,skuId,spuId,userId,pfBorderPromotion.getUserPid(),pfBorderPromotion.getQuantity());
        log.info("支付成功发货后更新小白库存和平台库存----end");
    }


    /**
     *  更新订单促销活动赠品表中是否已发放商品
     * @param pfBorderId
     */
    private void updateIsSend(Long pfBorderId,PfBorderPromotion pfBorderPromotion){
        if (pfBorderPromotion==null){
            pfBorderPromotion = getBorderPromotionsByBorderIdAndIsSend(pfBorderId, PfBorderPromotionIsSendEnum.NO_GiVE.getCode());
        }
        pfBorderPromotion.setIsSend(PfBorderPromotionIsSendEnum.GiVED.getCode());
        int i = update(pfBorderPromotion);
        if (i!=1){
            throw new BusinessException("------更新订单促销活动赠品表中是否已发放商品失败------");
        }
    }
    /**
     *  注册代理----支付成功发货后更新小白库存和平台库存
     * @param pfBorderId
     * @param skuId
     * @param spuId
     * @param userId
     */
    private void registAgentUpdateStock(Long pfBorderId,Integer skuId,Integer spuId,Long userId,Long userPid,Integer changeQuantity){
        log.info("更新小白库存和平台库存----入口参数----pfBorderId---"+pfBorderId+"----skuId---"+skuId+"----spuId----"+spuId+"----userId----"+userId);
        try{
            PfUserSkuStock userSkuStock =  userSkuStockService.selectByUserIdAndSkuIdAndSpuId(userId,skuId,spuId);
            if (userSkuStock!=null){
                //更新自己的库存
                updateOwnStock(changeQuantity,
                        pfBorderId,
                        userSkuStock,
                        userId,
                        skuId,
                        spuId,
                        PfBorderPromotionGiveStockChangeEnum.agent);
                //更新上级的库存
                if (userPid!=null&&userPid!=0){
                    updateUserPidStock(userPid,
                            skuId,
                            spuId,
                            changeQuantity,
                            PfBorderPromotionGiveStockChangeEnum.agent);
                }
                //更新平台库存
                updatePlatformStock(changeQuantity,spuId,skuId,pfBorderId,SkuStockLogType.registerGiveSku);
            }else{
                throw new BusinessException("----------用户库存商品不存在----------");
            }
        }catch (Exception e){
            throw new BusinessException("----小白注册更新用户库存失败----------"+e.getMessage());
        }
    }


    /**
     * 更新自己的库存
     * @param changeQuantity
     * @param pfBorderId
     * @param userSkuStock
     */
    private void updateOwnStock(Integer changeQuantity,
                                Long pfBorderId,
                                PfUserSkuStock userSkuStock,
                                Long userId,
                                Integer skuId,
                                Integer spuId,
                                PfBorderPromotionGiveStockChangeEnum changeGiveStockType){
        log.info("修改小白用户库存-----更新用户库存----start----");
        log.info("入口参数-----changeQuantity---"+changeQuantity+"---pfBorderId----"+pfBorderId+"----userSkuStock---"+userSkuStock);
        log.info("-----userId----"+userId+"----skuId---"+skuId+"---spuId----"+spuId+"----changeGiveStockType---"+changeGiveStockType.getCode());
        if (userSkuStock==null){
            userSkuStock =  userSkuStockService.selectByUserIdAndSkuIdAndSpuId(userId,skuId,spuId);
        }
        Integer registerGiveSkuStock = userSkuStock.getRegisterGiveSkuStock();
        if (registerGiveSkuStock==null){
            registerGiveSkuStock = 0;
        }
        Integer stock = userSkuStock.getStock();
        log.info("registerGiveSkuStock------"+registerGiveSkuStock+"-------changeQuantity----"+changeQuantity);
        switch (changeGiveStockType){
            case agent:
                registerGiveSkuStock += changeQuantity;
                userSkuStock.setRemark("小白用户注册代理平台送货");
                break;
            case Supplement:
                break;
            case UPGRADE:
                registerGiveSkuStock = 0;
                userSkuStock.setRemark("小白用户升级,平台赠送的货还有，清空平台赠送的货");
                break;
            case sell:
                if (registerGiveSkuStock>changeQuantity){
                    registerGiveSkuStock -= changeQuantity;
                }else {
                    registerGiveSkuStock = 0;
                    log.info("购买小白的商品，先购买平台赠送的商品");
                }
                userSkuStock.setRemark("小白用户售卖平台赠送的货");
                break;
            case recovery:
                if (registerGiveSkuStock>0){
                    stock -= registerGiveSkuStock;
                    registerGiveSkuStock = 0;
                    userSkuStock.setRemark("时间到期回收小白没有卖出去的库存");
                }
                break;
            default:
                break;
        }
        log.info("registerGiveSkuStock---变动后的值-----"+registerGiveSkuStock);
        if (registerGiveSkuStock<0){
            throw new BusinessException("平台赠送的商品为负数,逻辑错误---userId---"+userId+"----skuId---"+skuId);
        }
        userSkuStock.setStock(stock);
        userSkuStock.setRegisterGiveSkuStock(registerGiveSkuStock);
        if (userSkuStockService.updateByIdAndVersions(userSkuStock)!=1){
            throw new BusinessException("-----更新小白--平台赠送货物时失败-----");
        };
        log.info("修改小白用户库存---end");
    }

    /**
     * 更新上级的库存
     * @param userPid
     * @param skuId
     * @param spuId
     * @param changeQuantity
     * @param changeGiveStockType
     */
    private void updateUserPidStock(Long userPid,
                                    Integer skuId,
                                    Integer spuId,
                                    Integer changeQuantity,
                                    PfBorderPromotionGiveStockChangeEnum changeGiveStockType){
        log.info("更新小白上级---平台赠送的库存----start");
        log.info("入口参数-----userPid---"+userPid+"----skuId----"+skuId+"---spuId---"+spuId+"----changeQuantity---"+changeQuantity+"-----changeGiveStockType---"+changeGiveStockType.getCode());
        PfUserSkuStock parentUserSkuStock = userSkuStockService.selectByUserIdAndSkuIdAndSpuId(userPid,skuId,spuId);
        if (userPid!=null&&userPid!=0&&parentUserSkuStock!=null){
            Integer registerGiveSkuStock = parentUserSkuStock.getRegisterGiveSkuStock();
            if (registerGiveSkuStock!=null&&registerGiveSkuStock>0){
                log.info("-----上级平台赠送的剩余库存------"+registerGiveSkuStock);
                switch (changeGiveStockType){
                    case agent:
                        if (registerGiveSkuStock>changeQuantity){
                            registerGiveSkuStock -= changeQuantity;
                        }else{
                            registerGiveSkuStock = 0;
                        }
                        parentUserSkuStock.setRemark("小白用户下级补货或代理,平台赠送的货还有,减少平台赠送的货");
                        break;
                    default:
                        break;
                }
            }
            if (registerGiveSkuStock==null){
                registerGiveSkuStock=0;
            }
            parentUserSkuStock.setRegisterGiveSkuStock(registerGiveSkuStock);
            if (userSkuStockService.updateByIdAndVersions(parentUserSkuStock)!=1){
                throw new BusinessException("-----更新小白上级--平台赠送货物时失败-----");
            };
        }
        log.info("更新小白上级---平台赠送的库存----end");
    }


    /**
     * 更新平台库存
     * @param changeQuantity
     * @param spuId
     * @param skuId
     * @param pfBorderId
     * @param handleType
     */
    private void updatePlatformStock(Integer changeQuantity,Integer spuId,Integer skuId,Long pfBorderId,SkuStockLogType handleType){
        log.info("用户注册赠送商品-----更新平台库存----start");
        Map<String,Object>  map =  pfSkuStockService.isEnoughPlatformSku(changeQuantity,spuId,skuId);
        PfSkuStock pfSkuStock = (PfSkuStock)map.get("pfSkuStock");
        if (pfSkuStock==null){
            throw new BusinessException("用户注册赠送商品-----平台库存不足----逻辑错误--");
        }
        pfSkuStockService.updateSkuStockWithLog(changeQuantity,pfSkuStock,pfBorderId, handleType);
        log.info("用户注册赠送商品-----更新平台库存----end");
    }

    /**
     * 回收到期的平台送的商品
     */
    private void recoverySkuStock(){
        try{
            log.info("回收平台赠送的到期的商品-----start");
            Date _beforTime = DateUtil.getLastDays(0);
            Date startTime = DateUtil.getMinTimeofDay(_beforTime) ;
            Date endTime  = DateUtil.getMaxTimeofDay(_beforTime) ;
            log.info("startTime------"+startTime+"-----endTime----"+endTime);
            List<PfBorderPromotion> borderPromotions = pfBorderPromotionMapper.getExpirePromotionsByIsSendAndQuantityAndTime(
                    PfBorderPromotionIsSendEnum.GiVED.getCode(),
                    0,
                    startTime,
                    endTime);
            for (PfBorderPromotion pfBorderPromotion: borderPromotions){
                PfUserSkuStock  userSkuStock =  userSkuStockService.selectByUserIdAndSkuIdAndSpuId(pfBorderPromotion.getUserId(),pfBorderPromotion.getSkuId(),pfBorderPromotion.getSpuId());
                if (userSkuStock!=null&&userSkuStock.getRegisterGiveSkuStock()>0){
                    //更新自己的库存
                    log.info("-----更新自己的库存----start");
                    updateOwnStock(null,
                            pfBorderPromotion.getPfBorderId(),
                            userSkuStock,
                            pfBorderPromotion.getUserId(),
                            pfBorderPromotion.getSkuId(),
                            pfBorderPromotion.getSpuId(),
                            PfBorderPromotionGiveStockChangeEnum.recovery);
                    log.info("-----更新自己的库存----end");
                    //更新平台库存
                    log.info("-----更新平台库存----start");
                    updatePlatformStock(pfBorderPromotion.getQuantity(),
                            pfBorderPromotion.getSpuId(),
                            pfBorderPromotion.getSkuId(),
                            pfBorderPromotion.getPfBorderId(),
                            SkuStockLogType.recoveryGiveSku);
                    log.info("-----更新平台库存----end");
                }
            }

            log.info("回收平台赠送的到期的商品-----end");
        }catch (Exception e){
            throw new BusinessException("回收平台赠送的到期的商品失败----"+e.getMessage());
        }

    }
}
