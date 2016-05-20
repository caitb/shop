package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import com.masiis.shop.api.bean.base.BaseRes;
import com.masiis.shop.dao.po.ComUserAddress;

import java.util.List;

/**
 * Created by hzz on 2016/5/19.
 */
public class ComUserAddressRes extends BaseRes {
    private List<ComUserAddress> addresses;

    public List<ComUserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ComUserAddress> addresses) {
        this.addresses = addresses;
    }
}
