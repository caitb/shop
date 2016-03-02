/*
 * PfCorderExample.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao.generate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PfCorderExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public PfCorderExample() {
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
     * 平台分销订单表
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
        public Criteria andCreateManIsNull() {
            addCriterion("create_man is null");
            return (Criteria) this;
        }
        public Criteria andCreateManIsNotNull() {
            addCriterion("create_man is not null");
            return (Criteria) this;
        }
        public Criteria andCreateManEqualTo(Long value) {
            addCriterion("create_man =", value, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManNotEqualTo(Long value) {
            addCriterion("create_man <>", value, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManGreaterThan(Long value) {
            addCriterion("create_man >", value, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManGreaterThanOrEqualTo(Long value) {
            addCriterion("create_man >=", value, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManLessThan(Long value) {
            addCriterion("create_man <", value, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManLessThanOrEqualTo(Long value) {
            addCriterion("create_man <=", value, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManIn(List<Long> values) {
            addCriterion("create_man in", values, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManNotIn(List<Long> values) {
            addCriterion("create_man not in", values, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManBetween(Long value1, Long value2) {
            addCriterion("create_man between", value1, value2, "createMan");
            return (Criteria) this;
        }
        public Criteria andCreateManNotBetween(Long value1, Long value2) {
            addCriterion("create_man not between", value1, value2, "createMan");
            return (Criteria) this;
        }
        public Criteria andOrderCodeIsNull() {
            addCriterion("order_code is null");
            return (Criteria) this;
        }
        public Criteria andOrderCodeIsNotNull() {
            addCriterion("order_code is not null");
            return (Criteria) this;
        }
        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("order_code =", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("order_code <>", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("order_code >", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("order_code >=", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("order_code <", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("order_code <=", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeLike(String value) {
            addCriterion("order_code like", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("order_code not like", value, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("order_code in", values, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("order_code not in", values, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("order_code between", value1, value2, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("order_code not between", value1, value2, "orderCode");
            return (Criteria) this;
        }
        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }
        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }
        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }
        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }
        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }
        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }
        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }
        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }
        public Criteria andUserPidIsNull() {
            addCriterion("user_pid is null");
            return (Criteria) this;
        }
        public Criteria andUserPidIsNotNull() {
            addCriterion("user_pid is not null");
            return (Criteria) this;
        }
        public Criteria andUserPidEqualTo(Long value) {
            addCriterion("user_pid =", value, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidNotEqualTo(Long value) {
            addCriterion("user_pid <>", value, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidGreaterThan(Long value) {
            addCriterion("user_pid >", value, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidGreaterThanOrEqualTo(Long value) {
            addCriterion("user_pid >=", value, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidLessThan(Long value) {
            addCriterion("user_pid <", value, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidLessThanOrEqualTo(Long value) {
            addCriterion("user_pid <=", value, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidIn(List<Long> values) {
            addCriterion("user_pid in", values, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidNotIn(List<Long> values) {
            addCriterion("user_pid not in", values, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidBetween(Long value1, Long value2) {
            addCriterion("user_pid between", value1, value2, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserPidNotBetween(Long value1, Long value2) {
            addCriterion("user_pid not between", value1, value2, "userPid");
            return (Criteria) this;
        }
        public Criteria andUserMassageIsNull() {
            addCriterion("user_massage is null");
            return (Criteria) this;
        }
        public Criteria andUserMassageIsNotNull() {
            addCriterion("user_massage is not null");
            return (Criteria) this;
        }
        public Criteria andUserMassageEqualTo(String value) {
            addCriterion("user_massage =", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageNotEqualTo(String value) {
            addCriterion("user_massage <>", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageGreaterThan(String value) {
            addCriterion("user_massage >", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageGreaterThanOrEqualTo(String value) {
            addCriterion("user_massage >=", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageLessThan(String value) {
            addCriterion("user_massage <", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageLessThanOrEqualTo(String value) {
            addCriterion("user_massage <=", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageLike(String value) {
            addCriterion("user_massage like", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageNotLike(String value) {
            addCriterion("user_massage not like", value, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageIn(List<String> values) {
            addCriterion("user_massage in", values, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageNotIn(List<String> values) {
            addCriterion("user_massage not in", values, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageBetween(String value1, String value2) {
            addCriterion("user_massage between", value1, value2, "userMassage");
            return (Criteria) this;
        }
        public Criteria andUserMassageNotBetween(String value1, String value2) {
            addCriterion("user_massage not between", value1, value2, "userMassage");
            return (Criteria) this;
        }
        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }
        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }
        public Criteria andSupplierIdEqualTo(Integer value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdNotEqualTo(Integer value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdGreaterThan(Integer value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdLessThan(Integer value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdLessThanOrEqualTo(Integer value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdIn(List<Integer> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdNotIn(List<Integer> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdBetween(Integer value1, Integer value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }
        public Criteria andSupplierIdNotBetween(Integer value1, Integer value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }
        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }
        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }
        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
        public Criteria andModifyManIsNull() {
            addCriterion("modify_man is null");
            return (Criteria) this;
        }
        public Criteria andModifyManIsNotNull() {
            addCriterion("modify_man is not null");
            return (Criteria) this;
        }
        public Criteria andModifyManEqualTo(Long value) {
            addCriterion("modify_man =", value, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManNotEqualTo(Long value) {
            addCriterion("modify_man <>", value, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManGreaterThan(Long value) {
            addCriterion("modify_man >", value, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManGreaterThanOrEqualTo(Long value) {
            addCriterion("modify_man >=", value, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManLessThan(Long value) {
            addCriterion("modify_man <", value, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManLessThanOrEqualTo(Long value) {
            addCriterion("modify_man <=", value, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManIn(List<Long> values) {
            addCriterion("modify_man in", values, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManNotIn(List<Long> values) {
            addCriterion("modify_man not in", values, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManBetween(Long value1, Long value2) {
            addCriterion("modify_man between", value1, value2, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andModifyManNotBetween(Long value1, Long value2) {
            addCriterion("modify_man not between", value1, value2, "modifyMan");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountIsNull() {
            addCriterion("receivable_amount is null");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountIsNotNull() {
            addCriterion("receivable_amount is not null");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountEqualTo(BigDecimal value) {
            addCriterion("receivable_amount =", value, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountNotEqualTo(BigDecimal value) {
            addCriterion("receivable_amount <>", value, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountGreaterThan(BigDecimal value) {
            addCriterion("receivable_amount >", value, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_amount >=", value, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountLessThan(BigDecimal value) {
            addCriterion("receivable_amount <", value, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_amount <=", value, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountIn(List<BigDecimal> values) {
            addCriterion("receivable_amount in", values, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountNotIn(List<BigDecimal> values) {
            addCriterion("receivable_amount not in", values, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_amount between", value1, value2, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andReceivableAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_amount not between", value1, value2, "receivableAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountIsNull() {
            addCriterion("order_amount is null");
            return (Criteria) this;
        }
        public Criteria andOrderAmountIsNotNull() {
            addCriterion("order_amount is not null");
            return (Criteria) this;
        }
        public Criteria andOrderAmountEqualTo(BigDecimal value) {
            addCriterion("order_amount =", value, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountNotEqualTo(BigDecimal value) {
            addCriterion("order_amount <>", value, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountGreaterThan(BigDecimal value) {
            addCriterion("order_amount >", value, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_amount >=", value, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountLessThan(BigDecimal value) {
            addCriterion("order_amount <", value, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_amount <=", value, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountIn(List<BigDecimal> values) {
            addCriterion("order_amount in", values, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountNotIn(List<BigDecimal> values) {
            addCriterion("order_amount not in", values, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_amount between", value1, value2, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andOrderAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_amount not between", value1, value2, "orderAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountIsNull() {
            addCriterion("product_amount is null");
            return (Criteria) this;
        }
        public Criteria andProductAmountIsNotNull() {
            addCriterion("product_amount is not null");
            return (Criteria) this;
        }
        public Criteria andProductAmountEqualTo(BigDecimal value) {
            addCriterion("product_amount =", value, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountNotEqualTo(BigDecimal value) {
            addCriterion("product_amount <>", value, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountGreaterThan(BigDecimal value) {
            addCriterion("product_amount >", value, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("product_amount >=", value, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountLessThan(BigDecimal value) {
            addCriterion("product_amount <", value, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("product_amount <=", value, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountIn(List<BigDecimal> values) {
            addCriterion("product_amount in", values, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountNotIn(List<BigDecimal> values) {
            addCriterion("product_amount not in", values, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("product_amount between", value1, value2, "productAmount");
            return (Criteria) this;
        }
        public Criteria andProductAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("product_amount not between", value1, value2, "productAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountIsNull() {
            addCriterion("ship_amount is null");
            return (Criteria) this;
        }
        public Criteria andShipAmountIsNotNull() {
            addCriterion("ship_amount is not null");
            return (Criteria) this;
        }
        public Criteria andShipAmountEqualTo(BigDecimal value) {
            addCriterion("ship_amount =", value, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountNotEqualTo(BigDecimal value) {
            addCriterion("ship_amount <>", value, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountGreaterThan(BigDecimal value) {
            addCriterion("ship_amount >", value, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ship_amount >=", value, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountLessThan(BigDecimal value) {
            addCriterion("ship_amount <", value, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ship_amount <=", value, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountIn(List<BigDecimal> values) {
            addCriterion("ship_amount in", values, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountNotIn(List<BigDecimal> values) {
            addCriterion("ship_amount not in", values, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ship_amount between", value1, value2, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andShipAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ship_amount not between", value1, value2, "shipAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountIsNull() {
            addCriterion("pay_amount is null");
            return (Criteria) this;
        }
        public Criteria andPayAmountIsNotNull() {
            addCriterion("pay_amount is not null");
            return (Criteria) this;
        }
        public Criteria andPayAmountEqualTo(BigDecimal value) {
            addCriterion("pay_amount =", value, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountNotEqualTo(BigDecimal value) {
            addCriterion("pay_amount <>", value, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountGreaterThan(BigDecimal value) {
            addCriterion("pay_amount >", value, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pay_amount >=", value, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountLessThan(BigDecimal value) {
            addCriterion("pay_amount <", value, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pay_amount <=", value, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountIn(List<BigDecimal> values) {
            addCriterion("pay_amount in", values, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountNotIn(List<BigDecimal> values) {
            addCriterion("pay_amount not in", values, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pay_amount between", value1, value2, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pay_amount not between", value1, value2, "payAmount");
            return (Criteria) this;
        }
        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }
        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }
        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }
        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
            return (Criteria) this;
        }
        public Criteria andShipManIdIsNull() {
            addCriterion("ship_man_id is null");
            return (Criteria) this;
        }
        public Criteria andShipManIdIsNotNull() {
            addCriterion("ship_man_id is not null");
            return (Criteria) this;
        }
        public Criteria andShipManIdEqualTo(Integer value) {
            addCriterion("ship_man_id =", value, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdNotEqualTo(Integer value) {
            addCriterion("ship_man_id <>", value, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdGreaterThan(Integer value) {
            addCriterion("ship_man_id >", value, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ship_man_id >=", value, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdLessThan(Integer value) {
            addCriterion("ship_man_id <", value, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdLessThanOrEqualTo(Integer value) {
            addCriterion("ship_man_id <=", value, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdIn(List<Integer> values) {
            addCriterion("ship_man_id in", values, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdNotIn(List<Integer> values) {
            addCriterion("ship_man_id not in", values, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdBetween(Integer value1, Integer value2) {
            addCriterion("ship_man_id between", value1, value2, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ship_man_id not between", value1, value2, "shipManId");
            return (Criteria) this;
        }
        public Criteria andShipManNameIsNull() {
            addCriterion("ship_man_name is null");
            return (Criteria) this;
        }
        public Criteria andShipManNameIsNotNull() {
            addCriterion("ship_man_name is not null");
            return (Criteria) this;
        }
        public Criteria andShipManNameEqualTo(String value) {
            addCriterion("ship_man_name =", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameNotEqualTo(String value) {
            addCriterion("ship_man_name <>", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameGreaterThan(String value) {
            addCriterion("ship_man_name >", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameGreaterThanOrEqualTo(String value) {
            addCriterion("ship_man_name >=", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameLessThan(String value) {
            addCriterion("ship_man_name <", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameLessThanOrEqualTo(String value) {
            addCriterion("ship_man_name <=", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameLike(String value) {
            addCriterion("ship_man_name like", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameNotLike(String value) {
            addCriterion("ship_man_name not like", value, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameIn(List<String> values) {
            addCriterion("ship_man_name in", values, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameNotIn(List<String> values) {
            addCriterion("ship_man_name not in", values, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameBetween(String value1, String value2) {
            addCriterion("ship_man_name between", value1, value2, "shipManName");
            return (Criteria) this;
        }
        public Criteria andShipManNameNotBetween(String value1, String value2) {
            addCriterion("ship_man_name not between", value1, value2, "shipManName");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeIsNull() {
            addCriterion("delivery_time is null");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeIsNotNull() {
            addCriterion("delivery_time is not null");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeEqualTo(Date value) {
            addCriterion("delivery_time =", value, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeNotEqualTo(Date value) {
            addCriterion("delivery_time <>", value, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeGreaterThan(Date value) {
            addCriterion("delivery_time >", value, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delivery_time >=", value, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeLessThan(Date value) {
            addCriterion("delivery_time <", value, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeLessThanOrEqualTo(Date value) {
            addCriterion("delivery_time <=", value, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeIn(List<Date> values) {
            addCriterion("delivery_time in", values, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeNotIn(List<Date> values) {
            addCriterion("delivery_time not in", values, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeBetween(Date value1, Date value2) {
            addCriterion("delivery_time between", value1, value2, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andDeliveryTimeNotBetween(Date value1, Date value2) {
            addCriterion("delivery_time not between", value1, value2, "deliveryTime");
            return (Criteria) this;
        }
        public Criteria andShipRemarkIsNull() {
            addCriterion("ship_remark is null");
            return (Criteria) this;
        }
        public Criteria andShipRemarkIsNotNull() {
            addCriterion("ship_remark is not null");
            return (Criteria) this;
        }
        public Criteria andShipRemarkEqualTo(String value) {
            addCriterion("ship_remark =", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkNotEqualTo(String value) {
            addCriterion("ship_remark <>", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkGreaterThan(String value) {
            addCriterion("ship_remark >", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("ship_remark >=", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkLessThan(String value) {
            addCriterion("ship_remark <", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkLessThanOrEqualTo(String value) {
            addCriterion("ship_remark <=", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkLike(String value) {
            addCriterion("ship_remark like", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkNotLike(String value) {
            addCriterion("ship_remark not like", value, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkIn(List<String> values) {
            addCriterion("ship_remark in", values, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkNotIn(List<String> values) {
            addCriterion("ship_remark not in", values, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkBetween(String value1, String value2) {
            addCriterion("ship_remark between", value1, value2, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andShipRemarkNotBetween(String value1, String value2) {
            addCriterion("ship_remark not between", value1, value2, "shipRemark");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdIsNull() {
            addCriterion("replace_order_id is null");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdIsNotNull() {
            addCriterion("replace_order_id is not null");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdEqualTo(Long value) {
            addCriterion("replace_order_id =", value, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdNotEqualTo(Long value) {
            addCriterion("replace_order_id <>", value, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdGreaterThan(Long value) {
            addCriterion("replace_order_id >", value, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("replace_order_id >=", value, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdLessThan(Long value) {
            addCriterion("replace_order_id <", value, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("replace_order_id <=", value, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdIn(List<Long> values) {
            addCriterion("replace_order_id in", values, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdNotIn(List<Long> values) {
            addCriterion("replace_order_id not in", values, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdBetween(Long value1, Long value2) {
            addCriterion("replace_order_id between", value1, value2, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andReplaceOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("replace_order_id not between", value1, value2, "replaceOrderId");
            return (Criteria) this;
        }
        public Criteria andOrderStatusIsNull() {
            addCriterion("order_status is null");
            return (Criteria) this;
        }
        public Criteria andOrderStatusIsNotNull() {
            addCriterion("order_status is not null");
            return (Criteria) this;
        }
        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("order_status =", value, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("order_status <>", value, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("order_status >", value, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_status >=", value, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("order_status <", value, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("order_status <=", value, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusIn(List<Integer> values) {
            addCriterion("order_status in", values, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusNotIn(List<Integer> values) {
            addCriterion("order_status not in", values, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("order_status between", value1, value2, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("order_status not between", value1, value2, "orderStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusIsNull() {
            addCriterion("ship_status is null");
            return (Criteria) this;
        }
        public Criteria andShipStatusIsNotNull() {
            addCriterion("ship_status is not null");
            return (Criteria) this;
        }
        public Criteria andShipStatusEqualTo(Integer value) {
            addCriterion("ship_status =", value, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusNotEqualTo(Integer value) {
            addCriterion("ship_status <>", value, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusGreaterThan(Integer value) {
            addCriterion("ship_status >", value, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("ship_status >=", value, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusLessThan(Integer value) {
            addCriterion("ship_status <", value, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusLessThanOrEqualTo(Integer value) {
            addCriterion("ship_status <=", value, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusIn(List<Integer> values) {
            addCriterion("ship_status in", values, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusNotIn(List<Integer> values) {
            addCriterion("ship_status not in", values, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusBetween(Integer value1, Integer value2) {
            addCriterion("ship_status between", value1, value2, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andShipStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("ship_status not between", value1, value2, "shipStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }
        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }
        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusIn(List<Integer> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusNotIn(List<Integer> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }
        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
            return (Criteria) this;
        }
        public Criteria andIsShipIsNull() {
            addCriterion("is_ship is null");
            return (Criteria) this;
        }
        public Criteria andIsShipIsNotNull() {
            addCriterion("is_ship is not null");
            return (Criteria) this;
        }
        public Criteria andIsShipEqualTo(Integer value) {
            addCriterion("is_ship =", value, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipNotEqualTo(Integer value) {
            addCriterion("is_ship <>", value, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipGreaterThan(Integer value) {
            addCriterion("is_ship >", value, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_ship >=", value, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipLessThan(Integer value) {
            addCriterion("is_ship <", value, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipLessThanOrEqualTo(Integer value) {
            addCriterion("is_ship <=", value, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipIn(List<Integer> values) {
            addCriterion("is_ship in", values, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipNotIn(List<Integer> values) {
            addCriterion("is_ship not in", values, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipBetween(Integer value1, Integer value2) {
            addCriterion("is_ship between", value1, value2, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsShipNotBetween(Integer value1, Integer value2) {
            addCriterion("is_ship not between", value1, value2, "isShip");
            return (Criteria) this;
        }
        public Criteria andIsReplaceIsNull() {
            addCriterion("is_replace is null");
            return (Criteria) this;
        }
        public Criteria andIsReplaceIsNotNull() {
            addCriterion("is_replace is not null");
            return (Criteria) this;
        }
        public Criteria andIsReplaceEqualTo(Integer value) {
            addCriterion("is_replace =", value, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceNotEqualTo(Integer value) {
            addCriterion("is_replace <>", value, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceGreaterThan(Integer value) {
            addCriterion("is_replace >", value, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_replace >=", value, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceLessThan(Integer value) {
            addCriterion("is_replace <", value, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceLessThanOrEqualTo(Integer value) {
            addCriterion("is_replace <=", value, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceIn(List<Integer> values) {
            addCriterion("is_replace in", values, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceNotIn(List<Integer> values) {
            addCriterion("is_replace not in", values, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceBetween(Integer value1, Integer value2) {
            addCriterion("is_replace between", value1, value2, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReplaceNotBetween(Integer value1, Integer value2) {
            addCriterion("is_replace not between", value1, value2, "isReplace");
            return (Criteria) this;
        }
        public Criteria andIsReceiptIsNull() {
            addCriterion("is_receipt is null");
            return (Criteria) this;
        }
        public Criteria andIsReceiptIsNotNull() {
            addCriterion("is_receipt is not null");
            return (Criteria) this;
        }
        public Criteria andIsReceiptEqualTo(Integer value) {
            addCriterion("is_receipt =", value, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptNotEqualTo(Integer value) {
            addCriterion("is_receipt <>", value, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptGreaterThan(Integer value) {
            addCriterion("is_receipt >", value, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_receipt >=", value, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptLessThan(Integer value) {
            addCriterion("is_receipt <", value, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptLessThanOrEqualTo(Integer value) {
            addCriterion("is_receipt <=", value, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptIn(List<Integer> values) {
            addCriterion("is_receipt in", values, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptNotIn(List<Integer> values) {
            addCriterion("is_receipt not in", values, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptBetween(Integer value1, Integer value2) {
            addCriterion("is_receipt between", value1, value2, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andIsReceiptNotBetween(Integer value1, Integer value2) {
            addCriterion("is_receipt not between", value1, value2, "isReceipt");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeIsNull() {
            addCriterion("receipt_time is null");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeIsNotNull() {
            addCriterion("receipt_time is not null");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeEqualTo(Date value) {
            addCriterion("receipt_time =", value, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeNotEqualTo(Date value) {
            addCriterion("receipt_time <>", value, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeGreaterThan(Date value) {
            addCriterion("receipt_time >", value, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("receipt_time >=", value, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeLessThan(Date value) {
            addCriterion("receipt_time <", value, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeLessThanOrEqualTo(Date value) {
            addCriterion("receipt_time <=", value, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeIn(List<Date> values) {
            addCriterion("receipt_time in", values, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeNotIn(List<Date> values) {
            addCriterion("receipt_time not in", values, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeBetween(Date value1, Date value2) {
            addCriterion("receipt_time between", value1, value2, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andReceiptTimeNotBetween(Date value1, Date value2) {
            addCriterion("receipt_time not between", value1, value2, "receiptTime");
            return (Criteria) this;
        }
        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }
        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }
        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }
        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    /**
     * 平台分销订单表
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