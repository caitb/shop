/*
 * PfBorderOperationLogExample.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PfBorderOperationLogExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public PfBorderOperationLogExample() {
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
     * 平台代理订单操作日志表
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
        public Criteria andPfBorderIdIsNull() {
            addCriterion("pf_border_id is null");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdIsNotNull() {
            addCriterion("pf_border_id is not null");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdEqualTo(Long value) {
            addCriterion("pf_border_id =", value, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdNotEqualTo(Long value) {
            addCriterion("pf_border_id <>", value, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdGreaterThan(Long value) {
            addCriterion("pf_border_id >", value, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("pf_border_id >=", value, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdLessThan(Long value) {
            addCriterion("pf_border_id <", value, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdLessThanOrEqualTo(Long value) {
            addCriterion("pf_border_id <=", value, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdIn(List<Long> values) {
            addCriterion("pf_border_id in", values, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdNotIn(List<Long> values) {
            addCriterion("pf_border_id not in", values, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdBetween(Long value1, Long value2) {
            addCriterion("pf_border_id between", value1, value2, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderIdNotBetween(Long value1, Long value2) {
            addCriterion("pf_border_id not between", value1, value2, "pfBorderId");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusIsNull() {
            addCriterion("pf_border_status is null");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusIsNotNull() {
            addCriterion("pf_border_status is not null");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusEqualTo(Integer value) {
            addCriterion("pf_border_status =", value, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusNotEqualTo(Integer value) {
            addCriterion("pf_border_status <>", value, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusGreaterThan(Integer value) {
            addCriterion("pf_border_status >", value, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pf_border_status >=", value, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusLessThan(Integer value) {
            addCriterion("pf_border_status <", value, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pf_border_status <=", value, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusIn(List<Integer> values) {
            addCriterion("pf_border_status in", values, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusNotIn(List<Integer> values) {
            addCriterion("pf_border_status not in", values, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusBetween(Integer value1, Integer value2) {
            addCriterion("pf_border_status between", value1, value2, "pfBorderStatus");
            return (Criteria) this;
        }
        public Criteria andPfBorderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pf_border_status not between", value1, value2, "pfBorderStatus");
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
     * 平台代理订单操作日志表
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