package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.ComUserAddressMapper;
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
     * @author  hanzengzhi
     * @date  2016/3/7 11:43
     */
    public List<ComUserAddress> queryComUserAddressesByParam(ComUserAddress comUserAddress){
        return comUserAddressMapper.queryComUserAddressesByParam(comUserAddress);
    }

    /**
     * 增加收货地址
     * @author  hanzengzhi
     * @date  2016/3/7 15:29
     */
    public int addComUserAddress(ComUserAddress comUserAddress){
        return  comUserAddressMapper.insert(comUserAddress);
    }

}
