package com.masiis.shop.web.common.service;

import com.alibaba.fastjson.JSONObject;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.beans.order.BorderAgentParamForAddress;
import com.masiis.shop.dao.beans.order.BorderSupplementParamForAddress;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.common.constant.platform.SysConstants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
 * 用户地址
 */
@Service
@Transactional
public class UserAddressService {

    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserAddressMapper comUserAddressMapper;

    private static final String indexPath = "/index";
    //新增地址增加完跳转到订单界面
    public static final int addAddressPageToOrderPage = 0;
    //新增地址增加完跳转到管理地址界面
    public static final int getAddAddressPageToPersonalInfoPage =1;

    /**
     * 根据条件查询用户地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 11:43
     */
    public List<ComUserAddress> queryComUserAddressesByParam(ComUserAddress comUserAddress) {
        return comUserAddressMapper.queryComUserAddressesByParam(comUserAddress);
    }

    /**
     * 获得订单的选择的地址
     *
     * @param selectedAddressId
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ComUserAddress getOrderAddress(Long selectedAddressId, Long userId) {
        try {
            //获得用户的默认地址
            ComUserAddress comUserAddress = new ComUserAddress();
            comUserAddress.setUserId(userId);
            if (StringUtils.isEmpty(selectedAddressId)) {
                //如果没有选中地址选择默认地址
                comUserAddress.setIsDefault(1);
            } else {
                //选中的地址
                comUserAddress.setId(selectedAddressId);
            }
            List<ComUserAddress> comuserAddressList = queryComUserAddressesByParam(comUserAddress);
            //地址
            if (comuserAddressList != null && comuserAddressList.size() > 0) {
                return comuserAddressList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new BusinessException("获得订单选择的地址失败----" + e);
        }
    }

    /**
     * 增加或更新地址
     *
     * @author hanzengzhi
     * @date 2016/3/22 20:30
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public String addOrUpdateAddress(HttpServletRequest request, Long id, Integer isDefault, ComUserAddress comUserAddress, String operateType, int addAddressJumpType) {
        try {
            if (operateType.equals("save")) {
                addComUserAddress(comUserAddress);
                String path = "";
                if (!StringUtils.isEmpty(comUserAddress.getId())) {
                    switch (addAddressJumpType) {
                        case addAddressPageToOrderPage:  //跳转到订单界面
                            path = getOrderPagePath(request, comUserAddress.getId());
                            break;
                        case getAddAddressPageToPersonalInfoPage://跳转到管理地址界面
                            path = "/userAddress/toManageAddressPage.html?manageAddressJumpType=1&addAddressJumpType=" + addAddressJumpType;
                            break;
                        default:
                            break;
                    }
                } else {
                    return "false";
                }
                return path;
            } else {
                int i = 0;
                comUserAddress.setIsDefault(isDefault);
                comUserAddress.setId(id);
                i = updateComUserAddress(comUserAddress);
                if (i == 1) {
                    return "success";
                } else {
                    return "false";
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
    /**
     * 获得订单地址路径参数
     * @author hanzengzhi
     * @date 2016/4/2 11:34
     */
    public String getOrderPagePath(HttpServletRequest request, Long selectedAddressId) {
        try{
            String orderType = (String) request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_ORDER_TYPE);
            Long orderId = (Long) request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_ORDER_Id);
            Integer skuId = (Integer) request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_ORDER_SKU_ID);
            Long  pfUserSkuStockId = (Long) request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_PF_USER_SKU_STOCK_ID);
            String agentOrderForAddressJson = (String) request.getSession().getAttribute(SysConstants.SESSION_ORDER_AGENT_PARAM);
            String supplementeOrderForAddress = (String) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SUPPLEMENT_PARAM);
            Long  shopId = (Long)request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_MALL_CONFIRM_ORDER_SHOP_ID);
            Integer  promoId = (Integer) request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_MALL_PROMOTION_RECEIVE_REWARD_PROMO_ID);
            Integer  promoRuleId = (Integer)request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_MALL_PROMOTION_RECEIVE_REWARD_PROMO_RULE_ID);
            Integer  turnTableId = (Integer)request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_TURN_TABLE_Id);
            Integer  giftId = (Integer)request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_TURN_TABLE_GIFT_ID);
            Long  userTurnTableRecordId = (Long)request.getSession().getAttribute(com.masiis.shop.common.constant.mall.SysConstants.SESSION_USER_TURN_TABLE_RECORD_ID);
            if (StringUtils.isEmpty(orderType)){
                return indexPath;
            }else{
                return getOrderAddress(orderType,
                        agentOrderForAddressJson,
                        supplementeOrderForAddress,
                        orderId,
                        skuId,
                        selectedAddressId,
                        pfUserSkuStockId,
                        shopId,
                        promoId,
                        promoRuleId,
                        turnTableId,
                        giftId,
                        userTurnTableRecordId);
            }
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
    }

	
	/**
     * 获得具体的订单地址
     * 1:支付试用订单地址
     * 2：合伙人支付订单地址
     *
     * @author hanzengzhi
     * @date 2016/3/22 12:13
     */
    private String getOrderAddress(String orderType,
                                   String agentOrderForAddressJson,
                                   String supplementeOrderForAddress,
                                   Long orderId,
                                   Integer skuId,
                                   Long selectedAddressId,
                                   Long pfUserSkuStockId,
                                   Long shopId,
                                   Integer promoId,
                                   Integer promoRuleId,
                                   Integer turnTableId,
                                   Integer giftId,
                                   Long userTurnTableRecordId) {
        StringBuffer sb = new StringBuffer();
        switch (orderType) {
            case SysConstants.SESSION_TRIAL_ORDER_TYPE_VALUE:
                getTrialOrderAddress(sb, skuId, selectedAddressId);
                break;
            case SysConstants.SESSION_PAY_ORDER_TYPE_VALUE:
                getPayBorderAddress(sb, orderId, selectedAddressId);
                break;
            case SysConstants.SESSION_ORDER_TAKE_GOODS_VALUE:
                getTakeGoodsPageAddress(sb, orderId, selectedAddressId);
                break;
            case SysConstants.SESSION_MANAGE_GOODS_TAKE_GOODS_VALUE://管理商品拿货
                getManageGoodsTakeGoodsPageAddress(sb, pfUserSkuStockId, selectedAddressId);
                break;
            case SysConstants.SESSION_ORDER_AGENT_TYPE_VALUE: //代理订单
                getAgentOrderPageAddress(sb, agentOrderForAddressJson, selectedAddressId);
                break;
            case SysConstants.SESSION_ORDER_SUPPLEMENT_TYPE_VALUE://补货订单
                getSupplementOrderPageAddress(sb, supplementeOrderForAddress, selectedAddressId);
                break;
            case com.masiis.shop.common.constant.mall.SysConstants.SESSION_MALL_CONFIRM_ORDER://小铺确认订单界面
                getMallConfrimOrderPageAddress(sb, shopId, selectedAddressId);
                break;
            case com.masiis.shop.common.constant.mall.SysConstants.SESSION_MALL_RECEIVE_REWARD://活动领取奖励界面
                getReceiveRewardPageAddress(sb,promoId,promoRuleId ,selectedAddressId);
                break;
            case com.masiis.shop.common.constant.mall.SysConstants.SESSION_MALL_TURN_TABLE_RECEIVE_GIFT://抽奖领取界面
                getTurnTableReceiveGiftPageAddress(sb,turnTableId,giftId,userTurnTableRecordId,selectedAddressId);
                break;
            default:
                break;
        }
        return sb.toString();
    }

    /**
     * 试用支付的地址
     *
     * @author hanzengzhi
     * @date 2016/3/22 13:41
     */
    private void getTrialOrderAddress(StringBuffer sb, Integer skuId, Long selectedAddressId) {
        //跳转到支付使用界面
        sb.append("/corder/confirmOrder.do?");
        if (!StringUtils.isEmpty(skuId)) {
            sb.append("skuId=").append(skuId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&selectedAddressId=").append(selectedAddressId);
        }
    }

    /**
     * border支付地址
     *
     * @author hanzengzhi
     * @date 2016/3/22 13:41
     */
    private void getPayBorderAddress(StringBuffer sb, Long orderId, Long selectedAddressId) {
        //跳转到支付界面
        sb.append("/border/payBOrder.shtml?");
        if (!StringUtils.isEmpty(orderId)) {
            sb.append("bOrderId=").append(orderId).append("&");
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("userAddressId=").append(selectedAddressId);
        }
    }

    /**
     * 拿货界面地址
     *
     * @author hanzengzhi
     * @date 2016/4/5 10:40
     */
    private void getTakeGoodsPageAddress(StringBuffer sb, Long orderId, Long selectedAddressId) {
        //跳转到支付界面
        sb.append("/border/setUserSendType.shtml?");
        if (!StringUtils.isEmpty(orderId)) {
            sb.append("bOrderId=").append(orderId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&selectedAddressId=").append(selectedAddressId);
        }
    }

    /**
     * 获得商品管理拿货的地址路径
     *
     * @author hanzengzhi
     * @date 2016/4/6 10:45
     */
    private void getManageGoodsTakeGoodsPageAddress(StringBuffer sb, Long pfUserSkuStockId, Long selectedAddressId) {
        sb.append("/product/user/applySkuInfo.list?");
        if (!StringUtils.isEmpty(pfUserSkuStockId)) {
            sb.append("id=").append(pfUserSkuStockId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&selectedAddressId=").append(selectedAddressId);
        }
    }

    /**
     * 代理订单的地址路径
     *
     * @author hanzengzhi
     * @date 2016/4/18 11:37
     */
    private void getAgentOrderPageAddress(StringBuffer sb, String jsonParam, Long selectedAddressId) {
        sb.append("/BOrderAdd/agentBOrder.shtml?");
        if (!StringUtils.isEmpty(jsonParam)) {
            BorderAgentParamForAddress paramObject = JSONObject.parseObject(jsonParam, BorderAgentParamForAddress.class);
            if (paramObject != null) {
                if (paramObject.getSkuId() != null) {
                    sb.append("skuId=").append(paramObject.getSkuId()).append("&");
                }
                if (paramObject.getAgentLevelId() != null) {
                    sb.append("agentLevelId=").append(paramObject.getAgentLevelId()).append("&");
                }
                if (paramObject.getWeiXinId() != null) {
                    sb.append("weiXinId=").append(paramObject.getWeiXinId()).append("&");
                }
                if (paramObject.getSendType() != null) {
                    sb.append("sendType=").append(paramObject.getSendType()).append("&");
                }
                if (paramObject.getPreviousPageType()!=null){
                    sb.append("previousPageType=").append(paramObject.getPreviousPageType()).append("&");
                }
            }
        }
        if (selectedAddressId != null) {
            sb.append("userAddressId=").append(selectedAddressId);
        }
    }

    /**
     * 获得补货订单地址参数
     *
     * @author hanzengzhi
     * @date 2016/4/18 16:00
     */
    private void getSupplementOrderPageAddress(StringBuffer sb, String jsonParam, Long selectedAddressId) {
        sb.append("/BOrderAdd/supplementBOrder.shtml?");
        if (!StringUtils.isEmpty(jsonParam)) {
            BorderSupplementParamForAddress paramForAddress = JSONObject.parseObject(jsonParam, BorderSupplementParamForAddress.class);
            if (paramForAddress != null) {
                if (paramForAddress.getSkuId() != null) {
                    sb.append("skuId=").append(paramForAddress.getSkuId()).append("&");
                }
                if (paramForAddress.getQuantity() != null) {
                    sb.append("quantity=").append(paramForAddress.getQuantity()).append("&");
                }
            }
            if (selectedAddressId != null) {
                sb.append("userAddressId=").append(selectedAddressId);
            }
        }
    }
	
	/**
     * 小铺确定订单界面地址
     * @author hanzengzhi
     * @date 2016/4/10 16:36
     */
    private void getMallConfrimOrderPageAddress(StringBuffer sb, Long shopId, Long selectedAddressId) {
        sb.append("/orderPurchase/getShopCartInfo.html?");
        if (!StringUtils.isEmpty(shopId)) {
            sb.append("shopId=").append(shopId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&selectedAddressId=").append(selectedAddressId);
        }
    }

    /**
     *  获取小铺端领取活动界面地址
     * @param sb        地址字符串
     * @param promoId   活动id
     * @param promoRuleId       活动规则id
     * @param selectedAddressId  当前选择的地址id
     */
    private void getReceiveRewardPageAddress(StringBuffer sb,Integer promoId,Integer promoRuleId, Long selectedAddressId) {
        sb.append("/promotionGorder/getPromotionGorderPageInfo.html?");
        if (!StringUtils.isEmpty(promoId)) {
            sb.append("promoId=").append(promoId);
        }
        if (!StringUtils.isEmpty(promoRuleId)) {
            sb.append("&promoRuleId=").append(promoRuleId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&selectedAddressId=").append(selectedAddressId);
        }
    }

    /**
     * 获取小铺端转盘领取界面地址
     * @param sb
     * @param turnTableId
     * @param giftId
     * @param userTurnTableRecordId
     * @param selectedAddressId
     */
    private void getTurnTableReceiveGiftPageAddress(StringBuffer sb,Integer turnTableId,Integer giftId, Long userTurnTableRecordId,Long selectedAddressId) {
        sb.append("/turnTableGorder/getTurnTableGiftInfo.html?");
        if (!StringUtils.isEmpty(turnTableId)) {
            sb.append("turnTableId=").append(turnTableId);
        }
        if (!StringUtils.isEmpty(giftId)) {
            sb.append("&giftId=").append(giftId);
        }
        if (!StringUtils.isEmpty(userTurnTableRecordId)) {
            sb.append("&userTurnTableRecordId=").append(userTurnTableRecordId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&selectedAddressId=").append(selectedAddressId);
        }
    }

    /**
     * 增加收货地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 15:29
     */
    public int addComUserAddress(ComUserAddress comUserAddress) {
        //是否有地址
        ComUserAddress _comUserAddress = new ComUserAddress();
        _comUserAddress.setUserId(comUserAddress.getUserId());
        List<ComUserAddress> comUserAddressList = comUserAddressMapper.queryComUserAddressesByParam(_comUserAddress);
        if (comUserAddressList != null && comUserAddressList.size() > 0) {
            comUserAddress.setIsDefault(0);
            return comUserAddressMapper.insert(comUserAddress);
        } else {
            //没有地址，将新地址设置为默认地址
            comUserAddress.setIsDefault(1);
            return comUserAddressMapper.insert(comUserAddress);
        }
    }

    /**
     * 更新收货地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 20:18
     */
    public int updateComUserAddress(ComUserAddress comUserAddress) {
        return comUserAddressMapper.updateByPrimaryKey(comUserAddress);
    }

    /**
     * 根据地址ID获取用户地址
     *
     * @author ZhaoLiang
     * @date 2016/3/9 10:57
     */
    public ComUserAddress getUserAddressById(Long userAddressId) {
        return comUserAddressMapper.selectByPrimaryKey(userAddressId);
    }

    /**
     * 删除用户地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 15:23
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Long deleteUserAddressById(Long id, Long userId, Long defaultAddressId) {
        try {
            int i = comUserAddressMapper.deleteByPrimaryKey(id);
            int ii = 0;
            ComUserAddress comUserAddress = new ComUserAddress();
            if (id.equals(defaultAddressId)) {
                //如果删除的是默认地址，把最新的地址设置为默认地址
                comUserAddress.setUserId(userId);
                List<ComUserAddress> comUserAddressList = comUserAddressMapper.queryComUserAddressesByParam(comUserAddress);
                if (comUserAddressList != null && comUserAddressList.size() > 0) {
                    comUserAddress = comUserAddressList.get(0);
                    comUserAddress.setIsDefault(1);
                    ii = comUserAddressMapper.updateByPrimaryKey(comUserAddress);
                }
            }
            if (i == 1 && ii == 1) {
                //删除成功，设置默认地址的id值
                return comUserAddress.getId();
            } else if (i == 1) {
                //删除成功
                return -1L;
            } else {
                //删除失败
                return 0L;
            }
        } catch (Exception e) {
            throw new BusinessException("删除地址失败----" + e);
        }
    }

    /**
     * 地址设置为默认地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 16:24
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Boolean settingDefaultAddress(Long id, Long userId) {
        try {
            //取消之前的默认地址
            int ii = comUserAddressMapper.cancelDefaultAddress(userId);
            if (ii==1){
                //设置新的默认地址
                int i = comUserAddressMapper.settingDefaultAddress(id);
                if (i == 1) {
                    return true;
                } else {
                    return false;
                }
            }else{
                throw new BusinessException("设置默认地址失败-----默认地址有多个");

            }

        } catch (Exception e) {
            throw new BusinessException("设置默认地址失败-----" + e);
        }
    }


}
