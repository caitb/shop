package com.masiis.shop.admin.beans.recommend;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserRecommenRelation;

/**
 * 推荐表数据载体
 * Created by cai_tb on 16/6/15.
 */
public class Recommend {

    /* 推荐关系表实体 */
    private PfUserRecommenRelation pfUserRecommenRelation;
    /* 推荐人 */
    private ComUser recommendUser;
    /* 被推荐人 */
    private ComUser presenteeUser;
    /* 被推荐人上级 */
    private ComUser presenteePUser;
    /* 代理产品 */
    private ComSku comSku;

    public PfUserRecommenRelation getPfUserRecommenRelation() {
        return pfUserRecommenRelation;
    }

    public void setPfUserRecommenRelation(PfUserRecommenRelation pfUserRecommenRelation) {
        this.pfUserRecommenRelation = pfUserRecommenRelation;
    }

    public ComUser getRecommendUser() {
        return recommendUser;
    }

    public void setRecommendUser(ComUser recommendUser) {
        this.recommendUser = recommendUser;
    }

    public ComUser getPresenteeUser() {
        return presenteeUser;
    }

    public void setPresenteeUser(ComUser presenteeUser) {
        this.presenteeUser = presenteeUser;
    }

    public ComUser getPresenteePUser() {
        return presenteePUser;
    }

    public void setPresenteePUser(ComUser presenteePUser) {
        this.presenteePUser = presenteePUser;
    }

    public ComSku getComSku() {
        return comSku;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    @Override
    public String toString() {
        return "Recommend{" +
                "pfUserRecommenRelation=" + pfUserRecommenRelation +
                ", recommendUser=" + recommendUser +
                ", presenteeUser=" + presenteeUser +
                ", presenteePUser=" + presenteePUser +
                ", comSku=" + comSku +
                '}';
    }
}
