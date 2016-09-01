package com.masiis.shop.dao.beans.statistic;

/**
 * Created by admin on 2016/8/29.
 */
public class StatisticUser {

    private String registerCountUser;
    //总量
    private Integer total;

    public String getRegisterCountUser() {
        return registerCountUser;
    }

    public void setRegisterCountUser(String registerCountUser) {
        this.registerCountUser = registerCountUser;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "StatisticUser{" +
                "registerCountUser='" + registerCountUser + '\'' +
                ", total=" + total +
                '}';
    }
}
