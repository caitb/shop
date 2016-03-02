/*
 * ComUserExample.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComUserExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public ComUserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
    public String getOrderByClause() {
        return orderByClause;
    }
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }
    public boolean isDistinct() {
        return distinct;
    }
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * 用户表（包括代理和分销用户）
     * 
     * @author masiis
     * @version 1.0 2016-03-02
     */
    protected abstract static class GeneratedCriteria {

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }
        public boolean isValid() {
            return criteria.size() > 0;
        }
        public List<Criterion> getAllCriteria() {
            return criteria;
        }
        public List<Criterion> getCriteria() {
            return criteria;
        }
        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }
        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }
        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }
        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }
        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }
        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }
        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }
        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }
        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }
        public Criteria andOpenidIsNull() {
            addCriterion("openid is null");
            return (Criteria) this;
        }
        public Criteria andOpenidIsNotNull() {
            addCriterion("openid is not null");
            return (Criteria) this;
        }
        public Criteria andOpenidEqualTo(String value) {
            addCriterion("openid =", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidNotEqualTo(String value) {
            addCriterion("openid <>", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidGreaterThan(String value) {
            addCriterion("openid >", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidGreaterThanOrEqualTo(String value) {
            addCriterion("openid >=", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidLessThan(String value) {
            addCriterion("openid <", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidLessThanOrEqualTo(String value) {
            addCriterion("openid <=", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidLike(String value) {
            addCriterion("openid like", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidNotLike(String value) {
            addCriterion("openid not like", value, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidIn(List<String> values) {
            addCriterion("openid in", values, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidNotIn(List<String> values) {
            addCriterion("openid not in", values, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidBetween(String value1, String value2) {
            addCriterion("openid between", value1, value2, "openid");
            return (Criteria) this;
        }
        public Criteria andOpenidNotBetween(String value1, String value2) {
            addCriterion("openid not between", value1, value2, "openid");
            return (Criteria) this;
        }
        public Criteria andWxNkNameIsNull() {
            addCriterion("wx_nk_name is null");
            return (Criteria) this;
        }
        public Criteria andWxNkNameIsNotNull() {
            addCriterion("wx_nk_name is not null");
            return (Criteria) this;
        }
        public Criteria andWxNkNameEqualTo(String value) {
            addCriterion("wx_nk_name =", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameNotEqualTo(String value) {
            addCriterion("wx_nk_name <>", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameGreaterThan(String value) {
            addCriterion("wx_nk_name >", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameGreaterThanOrEqualTo(String value) {
            addCriterion("wx_nk_name >=", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameLessThan(String value) {
            addCriterion("wx_nk_name <", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameLessThanOrEqualTo(String value) {
            addCriterion("wx_nk_name <=", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameLike(String value) {
            addCriterion("wx_nk_name like", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameNotLike(String value) {
            addCriterion("wx_nk_name not like", value, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameIn(List<String> values) {
            addCriterion("wx_nk_name in", values, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameNotIn(List<String> values) {
            addCriterion("wx_nk_name not in", values, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameBetween(String value1, String value2) {
            addCriterion("wx_nk_name between", value1, value2, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxNkNameNotBetween(String value1, String value2) {
            addCriterion("wx_nk_name not between", value1, value2, "wxNkName");
            return (Criteria) this;
        }
        public Criteria andWxIdIsNull() {
            addCriterion("wx_id is null");
            return (Criteria) this;
        }
        public Criteria andWxIdIsNotNull() {
            addCriterion("wx_id is not null");
            return (Criteria) this;
        }
        public Criteria andWxIdEqualTo(String value) {
            addCriterion("wx_id =", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdNotEqualTo(String value) {
            addCriterion("wx_id <>", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdGreaterThan(String value) {
            addCriterion("wx_id >", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdGreaterThanOrEqualTo(String value) {
            addCriterion("wx_id >=", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdLessThan(String value) {
            addCriterion("wx_id <", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdLessThanOrEqualTo(String value) {
            addCriterion("wx_id <=", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdLike(String value) {
            addCriterion("wx_id like", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdNotLike(String value) {
            addCriterion("wx_id not like", value, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdIn(List<String> values) {
            addCriterion("wx_id in", values, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdNotIn(List<String> values) {
            addCriterion("wx_id not in", values, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdBetween(String value1, String value2) {
            addCriterion("wx_id between", value1, value2, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxIdNotBetween(String value1, String value2) {
            addCriterion("wx_id not between", value1, value2, "wxId");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgIsNull() {
            addCriterion("wx_head_img is null");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgIsNotNull() {
            addCriterion("wx_head_img is not null");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgEqualTo(String value) {
            addCriterion("wx_head_img =", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgNotEqualTo(String value) {
            addCriterion("wx_head_img <>", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgGreaterThan(String value) {
            addCriterion("wx_head_img >", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgGreaterThanOrEqualTo(String value) {
            addCriterion("wx_head_img >=", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgLessThan(String value) {
            addCriterion("wx_head_img <", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgLessThanOrEqualTo(String value) {
            addCriterion("wx_head_img <=", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgLike(String value) {
            addCriterion("wx_head_img like", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgNotLike(String value) {
            addCriterion("wx_head_img not like", value, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgIn(List<String> values) {
            addCriterion("wx_head_img in", values, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgNotIn(List<String> values) {
            addCriterion("wx_head_img not in", values, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgBetween(String value1, String value2) {
            addCriterion("wx_head_img between", value1, value2, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andWxHeadImgNotBetween(String value1, String value2) {
            addCriterion("wx_head_img not between", value1, value2, "wxHeadImg");
            return (Criteria) this;
        }
        public Criteria andAccessTokenIsNull() {
            addCriterion("access_token is null");
            return (Criteria) this;
        }
        public Criteria andAccessTokenIsNotNull() {
            addCriterion("access_token is not null");
            return (Criteria) this;
        }
        public Criteria andAccessTokenEqualTo(String value) {
            addCriterion("access_token =", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenNotEqualTo(String value) {
            addCriterion("access_token <>", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenGreaterThan(String value) {
            addCriterion("access_token >", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenGreaterThanOrEqualTo(String value) {
            addCriterion("access_token >=", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenLessThan(String value) {
            addCriterion("access_token <", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenLessThanOrEqualTo(String value) {
            addCriterion("access_token <=", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenLike(String value) {
            addCriterion("access_token like", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenNotLike(String value) {
            addCriterion("access_token not like", value, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenIn(List<String> values) {
            addCriterion("access_token in", values, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenNotIn(List<String> values) {
            addCriterion("access_token not in", values, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenBetween(String value1, String value2) {
            addCriterion("access_token between", value1, value2, "accessToken");
            return (Criteria) this;
        }
        public Criteria andAccessTokenNotBetween(String value1, String value2) {
            addCriterion("access_token not between", value1, value2, "accessToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenIsNull() {
            addCriterion("refresh_token is null");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenIsNotNull() {
            addCriterion("refresh_token is not null");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenEqualTo(String value) {
            addCriterion("refresh_token =", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenNotEqualTo(String value) {
            addCriterion("refresh_token <>", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenGreaterThan(String value) {
            addCriterion("refresh_token >", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenGreaterThanOrEqualTo(String value) {
            addCriterion("refresh_token >=", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenLessThan(String value) {
            addCriterion("refresh_token <", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenLessThanOrEqualTo(String value) {
            addCriterion("refresh_token <=", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenLike(String value) {
            addCriterion("refresh_token like", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenNotLike(String value) {
            addCriterion("refresh_token not like", value, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenIn(List<String> values) {
            addCriterion("refresh_token in", values, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenNotIn(List<String> values) {
            addCriterion("refresh_token not in", values, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenBetween(String value1, String value2) {
            addCriterion("refresh_token between", value1, value2, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andRefreshTokenNotBetween(String value1, String value2) {
            addCriterion("refresh_token not between", value1, value2, "refreshToken");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireIsNull() {
            addCriterion("atoken_expire is null");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireIsNotNull() {
            addCriterion("atoken_expire is not null");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireEqualTo(Date value) {
            addCriterion("atoken_expire =", value, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireNotEqualTo(Date value) {
            addCriterion("atoken_expire <>", value, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireGreaterThan(Date value) {
            addCriterion("atoken_expire >", value, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireGreaterThanOrEqualTo(Date value) {
            addCriterion("atoken_expire >=", value, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireLessThan(Date value) {
            addCriterion("atoken_expire <", value, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireLessThanOrEqualTo(Date value) {
            addCriterion("atoken_expire <=", value, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireIn(List<Date> values) {
            addCriterion("atoken_expire in", values, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireNotIn(List<Date> values) {
            addCriterion("atoken_expire not in", values, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireBetween(Date value1, Date value2) {
            addCriterion("atoken_expire between", value1, value2, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andAtokenExpireNotBetween(Date value1, Date value2) {
            addCriterion("atoken_expire not between", value1, value2, "atokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireIsNull() {
            addCriterion("rtoken_expire is null");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireIsNotNull() {
            addCriterion("rtoken_expire is not null");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireEqualTo(Date value) {
            addCriterion("rtoken_expire =", value, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireNotEqualTo(Date value) {
            addCriterion("rtoken_expire <>", value, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireGreaterThan(Date value) {
            addCriterion("rtoken_expire >", value, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireGreaterThanOrEqualTo(Date value) {
            addCriterion("rtoken_expire >=", value, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireLessThan(Date value) {
            addCriterion("rtoken_expire <", value, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireLessThanOrEqualTo(Date value) {
            addCriterion("rtoken_expire <=", value, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireIn(List<Date> values) {
            addCriterion("rtoken_expire in", values, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireNotIn(List<Date> values) {
            addCriterion("rtoken_expire not in", values, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireBetween(Date value1, Date value2) {
            addCriterion("rtoken_expire between", value1, value2, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRtokenExpireNotBetween(Date value1, Date value2) {
            addCriterion("rtoken_expire not between", value1, value2, "rtokenExpire");
            return (Criteria) this;
        }
        public Criteria andRealNameIsNull() {
            addCriterion("real_name is null");
            return (Criteria) this;
        }
        public Criteria andRealNameIsNotNull() {
            addCriterion("real_name is not null");
            return (Criteria) this;
        }
        public Criteria andRealNameEqualTo(String value) {
            addCriterion("real_name =", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameNotEqualTo(String value) {
            addCriterion("real_name <>", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameGreaterThan(String value) {
            addCriterion("real_name >", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameGreaterThanOrEqualTo(String value) {
            addCriterion("real_name >=", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameLessThan(String value) {
            addCriterion("real_name <", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameLessThanOrEqualTo(String value) {
            addCriterion("real_name <=", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameLike(String value) {
            addCriterion("real_name like", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameNotLike(String value) {
            addCriterion("real_name not like", value, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameIn(List<String> values) {
            addCriterion("real_name in", values, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameNotIn(List<String> values) {
            addCriterion("real_name not in", values, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameBetween(String value1, String value2) {
            addCriterion("real_name between", value1, value2, "realName");
            return (Criteria) this;
        }
        public Criteria andRealNameNotBetween(String value1, String value2) {
            addCriterion("real_name not between", value1, value2, "realName");
            return (Criteria) this;
        }
        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }
        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }
        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }
        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }
        public Criteria andIdCardIsNull() {
            addCriterion("id_card is null");
            return (Criteria) this;
        }
        public Criteria andIdCardIsNotNull() {
            addCriterion("id_card is not null");
            return (Criteria) this;
        }
        public Criteria andIdCardEqualTo(String value) {
            addCriterion("id_card =", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("id_card <>", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("id_card >", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("id_card >=", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardLessThan(String value) {
            addCriterion("id_card <", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("id_card <=", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardLike(String value) {
            addCriterion("id_card like", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardNotLike(String value) {
            addCriterion("id_card not like", value, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardIn(List<String> values) {
            addCriterion("id_card in", values, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("id_card not in", values, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("id_card between", value1, value2, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("id_card not between", value1, value2, "idCard");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlIsNull() {
            addCriterion("id_card_front_url is null");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlIsNotNull() {
            addCriterion("id_card_front_url is not null");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlEqualTo(String value) {
            addCriterion("id_card_front_url =", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlNotEqualTo(String value) {
            addCriterion("id_card_front_url <>", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlGreaterThan(String value) {
            addCriterion("id_card_front_url >", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_front_url >=", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlLessThan(String value) {
            addCriterion("id_card_front_url <", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_front_url <=", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlLike(String value) {
            addCriterion("id_card_front_url like", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlNotLike(String value) {
            addCriterion("id_card_front_url not like", value, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlIn(List<String> values) {
            addCriterion("id_card_front_url in", values, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlNotIn(List<String> values) {
            addCriterion("id_card_front_url not in", values, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlBetween(String value1, String value2) {
            addCriterion("id_card_front_url between", value1, value2, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardFrontUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_front_url not between", value1, value2, "idCardFrontUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlIsNull() {
            addCriterion("id_card_back_url is null");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlIsNotNull() {
            addCriterion("id_card_back_url is not null");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlEqualTo(String value) {
            addCriterion("id_card_back_url =", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlNotEqualTo(String value) {
            addCriterion("id_card_back_url <>", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlGreaterThan(String value) {
            addCriterion("id_card_back_url >", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlGreaterThanOrEqualTo(String value) {
            addCriterion("id_card_back_url >=", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlLessThan(String value) {
            addCriterion("id_card_back_url <", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlLessThanOrEqualTo(String value) {
            addCriterion("id_card_back_url <=", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlLike(String value) {
            addCriterion("id_card_back_url like", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlNotLike(String value) {
            addCriterion("id_card_back_url not like", value, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlIn(List<String> values) {
            addCriterion("id_card_back_url in", values, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlNotIn(List<String> values) {
            addCriterion("id_card_back_url not in", values, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlBetween(String value1, String value2) {
            addCriterion("id_card_back_url between", value1, value2, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andIdCardBackUrlNotBetween(String value1, String value2) {
            addCriterion("id_card_back_url not between", value1, value2, "idCardBackUrl");
            return (Criteria) this;
        }
        public Criteria andProvinceIdIsNull() {
            addCriterion("province_id is null");
            return (Criteria) this;
        }
        public Criteria andProvinceIdIsNotNull() {
            addCriterion("province_id is not null");
            return (Criteria) this;
        }
        public Criteria andProvinceIdEqualTo(Integer value) {
            addCriterion("province_id =", value, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdNotEqualTo(Integer value) {
            addCriterion("province_id <>", value, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdGreaterThan(Integer value) {
            addCriterion("province_id >", value, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("province_id >=", value, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdLessThan(Integer value) {
            addCriterion("province_id <", value, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdLessThanOrEqualTo(Integer value) {
            addCriterion("province_id <=", value, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdIn(List<Integer> values) {
            addCriterion("province_id in", values, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdNotIn(List<Integer> values) {
            addCriterion("province_id not in", values, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdBetween(Integer value1, Integer value2) {
            addCriterion("province_id between", value1, value2, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("province_id not between", value1, value2, "provinceId");
            return (Criteria) this;
        }
        public Criteria andProvinceNameIsNull() {
            addCriterion("province_name is null");
            return (Criteria) this;
        }
        public Criteria andProvinceNameIsNotNull() {
            addCriterion("province_name is not null");
            return (Criteria) this;
        }
        public Criteria andProvinceNameEqualTo(String value) {
            addCriterion("province_name =", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameNotEqualTo(String value) {
            addCriterion("province_name <>", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameGreaterThan(String value) {
            addCriterion("province_name >", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameGreaterThanOrEqualTo(String value) {
            addCriterion("province_name >=", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameLessThan(String value) {
            addCriterion("province_name <", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameLessThanOrEqualTo(String value) {
            addCriterion("province_name <=", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameLike(String value) {
            addCriterion("province_name like", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameNotLike(String value) {
            addCriterion("province_name not like", value, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameIn(List<String> values) {
            addCriterion("province_name in", values, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameNotIn(List<String> values) {
            addCriterion("province_name not in", values, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameBetween(String value1, String value2) {
            addCriterion("province_name between", value1, value2, "provinceName");
            return (Criteria) this;
        }
        public Criteria andProvinceNameNotBetween(String value1, String value2) {
            addCriterion("province_name not between", value1, value2, "provinceName");
            return (Criteria) this;
        }
        public Criteria andCityIdIsNull() {
            addCriterion("city_id is null");
            return (Criteria) this;
        }
        public Criteria andCityIdIsNotNull() {
            addCriterion("city_id is not null");
            return (Criteria) this;
        }
        public Criteria andCityIdEqualTo(Integer value) {
            addCriterion("city_id =", value, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdNotEqualTo(Integer value) {
            addCriterion("city_id <>", value, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdGreaterThan(Integer value) {
            addCriterion("city_id >", value, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("city_id >=", value, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdLessThan(Integer value) {
            addCriterion("city_id <", value, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdLessThanOrEqualTo(Integer value) {
            addCriterion("city_id <=", value, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdIn(List<Integer> values) {
            addCriterion("city_id in", values, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdNotIn(List<Integer> values) {
            addCriterion("city_id not in", values, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdBetween(Integer value1, Integer value2) {
            addCriterion("city_id between", value1, value2, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityIdNotBetween(Integer value1, Integer value2) {
            addCriterion("city_id not between", value1, value2, "cityId");
            return (Criteria) this;
        }
        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }
        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }
        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }
        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }
        public Criteria andRegionIdIsNull() {
            addCriterion("region_id is null");
            return (Criteria) this;
        }
        public Criteria andRegionIdIsNotNull() {
            addCriterion("region_id is not null");
            return (Criteria) this;
        }
        public Criteria andRegionIdEqualTo(Integer value) {
            addCriterion("region_id =", value, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdNotEqualTo(Integer value) {
            addCriterion("region_id <>", value, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdGreaterThan(Integer value) {
            addCriterion("region_id >", value, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("region_id >=", value, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdLessThan(Integer value) {
            addCriterion("region_id <", value, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdLessThanOrEqualTo(Integer value) {
            addCriterion("region_id <=", value, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdIn(List<Integer> values) {
            addCriterion("region_id in", values, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdNotIn(List<Integer> values) {
            addCriterion("region_id not in", values, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdBetween(Integer value1, Integer value2) {
            addCriterion("region_id between", value1, value2, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("region_id not between", value1, value2, "regionId");
            return (Criteria) this;
        }
        public Criteria andRegionNameIsNull() {
            addCriterion("region_name is null");
            return (Criteria) this;
        }
        public Criteria andRegionNameIsNotNull() {
            addCriterion("region_name is not null");
            return (Criteria) this;
        }
        public Criteria andRegionNameEqualTo(String value) {
            addCriterion("region_name =", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameNotEqualTo(String value) {
            addCriterion("region_name <>", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameGreaterThan(String value) {
            addCriterion("region_name >", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameGreaterThanOrEqualTo(String value) {
            addCriterion("region_name >=", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameLessThan(String value) {
            addCriterion("region_name <", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameLessThanOrEqualTo(String value) {
            addCriterion("region_name <=", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameLike(String value) {
            addCriterion("region_name like", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameNotLike(String value) {
            addCriterion("region_name not like", value, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameIn(List<String> values) {
            addCriterion("region_name in", values, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameNotIn(List<String> values) {
            addCriterion("region_name not in", values, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameBetween(String value1, String value2) {
            addCriterion("region_name between", value1, value2, "regionName");
            return (Criteria) this;
        }
        public Criteria andRegionNameNotBetween(String value1, String value2) {
            addCriterion("region_name not between", value1, value2, "regionName");
            return (Criteria) this;
        }
        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }
        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }
        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }
        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }
        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }
        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }
        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }
        public Criteria andIsAgentIsNull() {
            addCriterion("is_agent is null");
            return (Criteria) this;
        }
        public Criteria andIsAgentIsNotNull() {
            addCriterion("is_agent is not null");
            return (Criteria) this;
        }
        public Criteria andIsAgentEqualTo(Integer value) {
            addCriterion("is_agent =", value, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentNotEqualTo(Integer value) {
            addCriterion("is_agent <>", value, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentGreaterThan(Integer value) {
            addCriterion("is_agent >", value, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_agent >=", value, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentLessThan(Integer value) {
            addCriterion("is_agent <", value, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentLessThanOrEqualTo(Integer value) {
            addCriterion("is_agent <=", value, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentIn(List<Integer> values) {
            addCriterion("is_agent in", values, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentNotIn(List<Integer> values) {
            addCriterion("is_agent not in", values, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentBetween(Integer value1, Integer value2) {
            addCriterion("is_agent between", value1, value2, "isAgent");
            return (Criteria) this;
        }
        public Criteria andIsAgentNotBetween(Integer value1, Integer value2) {
            addCriterion("is_agent not between", value1, value2, "isAgent");
            return (Criteria) this;
        }
        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }
        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }
        public Criteria andSexEqualTo(Integer value) {
            addCriterion("sex =", value, "sex");
            return (Criteria) this;
        }
        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }
        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("sex >", value, "sex");
            return (Criteria) this;
        }
        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }
        public Criteria andSexLessThan(Integer value) {
            addCriterion("sex <", value, "sex");
            return (Criteria) this;
        }
        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }
        public Criteria andSexIn(List<Integer> values) {
            addCriterion("sex in", values, "sex");
            return (Criteria) this;
        }
        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }
        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }
        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    /**
     * 用户表（包括代理和分销用户）
     * 
     * @author masiis
     * @version 1.0 2016-03-02
     */
    public static class Criterion {

        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;

        public String getCondition() {
            return condition;
        }
        public Object getValue() {
            return value;
        }
        public Object getSecondValue() {
            return secondValue;
        }
        public boolean isNoValue() {
            return noValue;
        }
        public boolean isSingleValue() {
            return singleValue;
        }
        public boolean isBetweenValue() {
            return betweenValue;
        }
        public boolean isListValue() {
            return listValue;
        }
        public String getTypeHandler() {
            return typeHandler;
        }
        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }
        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }
        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }
        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}