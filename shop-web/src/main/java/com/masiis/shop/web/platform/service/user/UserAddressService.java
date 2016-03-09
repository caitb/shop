package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.ComUserAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
     * 增加收货地址
     *
     * @author hanzengzhi
     * @date 2016/3/7 15:29
     */
    public int addComUserAddress(ComUserAddress comUserAddress) {
        return comUserAddressMapper.insert(comUserAddress);
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
     * 根据ID获取用户地址
     *
     * @author ZhaoLiang
     * @date 2016/3/9 10:57
     */
    public ComUserAddress getUserAddressById(Integer userAddressId) {
        return comUserAddressMapper.selectByPrimaryKey(userAddressId);
    }
    /**
     * 删除用户地址
     * @author  hanzengzhi
     * @date  2016/3/9 15:23
     */
    public int deleteUserAddressById(Integer id){
        return comUserAddressMapper.deleteByPrimaryKey(id);
    }
    /**
     * 地址设置为默认地址
     * @author  hanzengzhi
     * @date  2016/3/9 16:24
     */
    public Boolean settingDefaultAddress(Integer id,Long userId){
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
