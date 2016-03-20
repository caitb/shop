package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.po.ComUserAddress;
import org.springframework.stereotype.Service;
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

    @Resource
    private ComUserAddressMapper comUserAddressMapper;

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
     * @param selectedAddressId
     * @param userId
     * @param request
     * @return
     */
    public ComUserAddress getOrderAddress(HttpServletRequest request,Long selectedAddressId,Long userId){
        //获得用户的默认地址
        ComUserAddress comUserAddress = new ComUserAddress();
        comUserAddress.setUserId(userId);
        if (StringUtils.isEmpty(selectedAddressId)){
            //如果没有选中地址选择默认地址
            comUserAddress.setIsDefault(1);
        }else{
            //选中的地址
            comUserAddress.setId(selectedAddressId);
        }
        List<ComUserAddress> comuserAddressList = queryComUserAddressesByParam(comUserAddress);
        //地址
        if (comuserAddressList!=null&&comuserAddressList.size()>0){
            return comuserAddressList.get(0);
        }else{
            return null;
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
        if (comUserAddressList!=null&&comUserAddressList.size()>0){
            comUserAddress.setIsDefault(0);
            return comUserAddressMapper.insert(comUserAddress);
        }else{
            //没有地址，将新地址设置为默认地址
            comUserAddress.setIsDefault(1);
            return comUserAddressMapper.insert(comUserAddress);
        }
    }
    /**
     * 更新收货地址
     * @author  hanzengzhi
     * @date  2016/3/9 20:18
     */
    public int updateComUserAddress(ComUserAddress comUserAddress){
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
     * @author  hanzengzhi
     * @date  2016/3/9 15:23
     */
    public Long deleteUserAddressById(Long id,Long userId,Long defaultAddressId){
       int i = comUserAddressMapper.deleteByPrimaryKey(id);
        int ii = 0;
        ComUserAddress comUserAddress = new ComUserAddress();
        if (id.equals(defaultAddressId)){
            //如果删除的是默认地址，把最新的地址设置为默认地址
            comUserAddress.setUserId(userId);
            List<ComUserAddress> comUserAddressList = comUserAddressMapper.queryComUserAddressesByParam(comUserAddress);
            if (comUserAddressList!=null&&comUserAddressList.size()>0){
                comUserAddress = comUserAddressList.get(0);
                comUserAddress.setIsDefault(1);
                ii = comUserAddressMapper.updateByPrimaryKey(comUserAddress);
            }
        }
        if (i==1&&ii==1){
            //删除成功，设置默认地址的id值
            return comUserAddress.getId();
        }else if (i==1){
            //删除成功
            return -1L;
        }else{
             //删除失败
            return 0L;
        }
    }
    /**
     * 地址设置为默认地址
     * @author  hanzengzhi
     * @date  2016/3/9 16:24
     */
    public Boolean settingDefaultAddress(Long id,Long userId){
        //取消之前的默认地址
        int ii = comUserAddressMapper.cancelDefaultAddress(userId);
        //设置新的默认地址
        int i =comUserAddressMapper.settingDefaultAddress(id);
        if (i==1){
            return true;
        }else{
            return false;
        }
    }
}
