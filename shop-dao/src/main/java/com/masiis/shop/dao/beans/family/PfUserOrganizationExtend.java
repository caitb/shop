package com.masiis.shop.dao.beans.family;

import com.masiis.shop.dao.beans.user.CountGroup;
import com.masiis.shop.dao.po.PfUserOrganization;

/**
 * Created by wangbingjian on 2016/8/12.
 */
public class PfUserOrganizationExtend {

    private PfUserOrganization pfUserOrganization;

    private CountGroup countGroup;

    public PfUserOrganization getPfUserOrganization() {
        return pfUserOrganization;
    }

    public void setPfUserOrganization(PfUserOrganization pfUserOrganization) {
        this.pfUserOrganization = pfUserOrganization;
    }

    public CountGroup getCountGroup() {
        return countGroup;
    }

    public void setCountGroup(CountGroup countGroup) {
        this.countGroup = countGroup;
    }
}
