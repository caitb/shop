package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.po.ComArea;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.web.platform.constants.SysConstants;
import com.masiis.shop.web.platform.controller.user.UserAddressController;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
 * 用户地址
 */
@Service
@Transactional
public class UserAddressService {

    @Resource
    private ComUserAddressMapper comUserAddressMapper;

    private static final String indexPath="/index";
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
    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    public ComUserAddress getOrderAddress( Long selectedAddressId, Long userId) {
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
        }catch (Exception e){
            throw new BusinessException("获得订单选择的地址失败----"+e);
        }
    }
    /**
     * 增加或更新地址
     * @author hanzengzhi
     * @date 2016/3/22 20:30
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public String addOrUpdateAddress(HttpServletRequest request, Long id, Integer isDefault, ComUserAddress comUserAddress, String operateType, int addAddressJumpType) {
        try {
            if (operateType.equals("save")) {
                addComUserAddress(comUserAddress);
                String path = "";
                if (!StringUtils.isEmpty(comUserAddress.getId())) {
                    switch (addAddressJumpType) {
                        case UserAddressController.addAddressPageToOrderPage:  //跳转到订单界面
                            path = getOrderPagePath(request, comUserAddress.getId());
                            break;
                        case UserAddressController.getAddAddressPageToPersonalInfoPage://跳转到管理地址界面
                            path = "/userAddress/toManageAddressPage.html?manageAddressJumpType=1&addAddressJumpType="+addAddressJumpType;
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
            String orderType = (String) request.getSession().getAttribute(SysConstants.SESSION_ORDER_TYPE);
            Long orderId = (Long) request.getSession().getAttribute(SysConstants.SESSION_ORDER_Id);
            Integer skuId = (Integer) request.getSession().getAttribute(SysConstants.SESSION_ORDER_SKU_ID);
            Long  pfUserSkuStockId = (Long) request.getSession().getAttribute(SysConstants.SESSION_PF_USER_SKU_STOCK_ID);
            if (StringUtils.isEmpty(orderType)){
                return indexPath;
            }else{
                return getOrderAddress(orderType, orderId, skuId, selectedAddressId,pfUserSkuStockId);
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
    private String getOrderAddress(String orderType, Long orderId, Integer skuId, Long selectedAddressId,Long pfUserSkuStockId) {
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
            sb.append("bOrderId=").append(orderId);
        }
        if (!StringUtils.isEmpty(selectedAddressId)) {
            sb.append("&userAddressId=").append(selectedAddressId);
        }
    }
    /**
     *  拿货界面地址
     * @author hanzengzhi
     * @date 2016/4/5 10:40
     */
    private void getTakeGoodsPageAddress(StringBuffer sb, Long orderId, Long selectedAddressId){
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
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Long deleteUserAddressById(Long id, Long userId, Long defaultAddressId) {
        try{
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
        }catch (Exception e){
            throw new BusinessException("删除地址失败----"+e);
        }
    }

    /**
     * 地址设置为默认地址
     *
     * @author hanzengzhi
     * @date 2016/3/9 16:24
     */
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public Boolean settingDefaultAddress(Long id, Long userId) {
        try{
            //取消之前的默认地址
            int ii = comUserAddressMapper.cancelDefaultAddress(userId);
            //设置新的默认地址
            int i = comUserAddressMapper.settingDefaultAddress(id);
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            throw new BusinessException("设置默认地址失败-----"+e);
        }
    }


}
