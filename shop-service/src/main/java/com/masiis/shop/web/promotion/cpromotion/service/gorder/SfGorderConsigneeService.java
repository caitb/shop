package com.masiis.shop.web.promotion.cpromotion.service.gorder;

import com.masiis.shop.dao.mall.promotion.SfGorderConsigneeMapper;
import com.masiis.shop.dao.po.ComUserAddress;
import com.masiis.shop.dao.po.SfGorderConsignee;
import com.masiis.shop.web.common.service.UserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * C端用户奖励订单收货人表service
 */
@Service
public class SfGorderConsigneeService {

    @Resource
    private SfGorderConsigneeMapper sfGorderConsigneeMapper;

    @Resource
    private UserAddressService userAddressService;

    public int addGorderConsignee(Long gorderId,Long addressId){
        ComUserAddress comUserAddress = userAddressService.getUserAddressById(addressId);
        if (comUserAddress!=null){
            SfGorderConsignee gorderConsignee = new SfGorderConsignee();
            gorderConsignee.setSfGorderId(gorderId);
            gorderConsignee.setUserId(comUserAddress.getUserId());
            gorderConsignee.setConsignee(comUserAddress.getName());
            gorderConsignee.setMobile(comUserAddress.getMobile());
            gorderConsignee.setProvinceId(comUserAddress.getProvinceId());
            gorderConsignee.setProvinceName(comUserAddress.getProvinceName());
            gorderConsignee.setCityId(comUserAddress.getCityId());
            gorderConsignee.setCityName(comUserAddress.getCityName());
            gorderConsignee.setRegionId(comUserAddress.getRegionId());
            gorderConsignee.setRegionName(comUserAddress.getRegionName());
            gorderConsignee.setAddress(comUserAddress.getAddress());
            gorderConsignee.setZip(comUserAddress.getZip());
            gorderConsignee.setRemark("领取奖励插入收货地址");
            return sfGorderConsigneeMapper.insert(gorderConsignee);
        }
        return 0;
    }
}
