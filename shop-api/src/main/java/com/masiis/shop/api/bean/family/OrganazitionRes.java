package com.masiis.shop.api.bean.family;

import com.masiis.shop.api.bean.base.BasePagingRes;
import com.masiis.shop.dao.po.PfUserOrganization;

import java.util.List;

/**
 * Created by jiajinghao on 2016/8/15.
 */
public class OrganazitionRes extends BasePagingRes {

    private List<PfUserOrganization> familyList;

    private List<PfUserOrganization> teamList;

    private Integer numberOfOrganization;

    public List<PfUserOrganization> getFamilyList() {
        return familyList;
    }

    public void setFamilyList(List<PfUserOrganization> familyList) {
        this.familyList = familyList;
    }

    public List<PfUserOrganization> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<PfUserOrganization> teamList) {
        this.teamList = teamList;
    }

    public Integer getNumberOfOrganization() {
        return numberOfOrganization;
    }

    public void setNumberOfOrganization(Integer numberOfOrganization) {
        this.numberOfOrganization = numberOfOrganization;
    }
}
