/*
 * PfCorderFreightExample.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PfCorderFreightExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public PfCorderFreightExample() {
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
     * 平台分销订单运单表
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
        public Criteria andPfCorderIdIsNull() {
            addCriterion("pf_corder_id is null");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdIsNotNull() {
            addCriterion("pf_corder_id is not null");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdEqualTo(Long value) {
            addCriterion("pf_corder_id =", value, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdNotEqualTo(Long value) {
            addCriterion("pf_corder_id <>", value, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdGreaterThan(Long value) {
            addCriterion("pf_corder_id >", value, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("pf_corder_id >=", value, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdLessThan(Long value) {
            addCriterion("pf_corder_id <", value, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdLessThanOrEqualTo(Long value) {
            addCriterion("pf_corder_id <=", value, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdIn(List<Long> values) {
            addCriterion("pf_corder_id in", values, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdNotIn(List<Long> values) {
            addCriterion("pf_corder_id not in", values, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdBetween(Long value1, Long value2) {
            addCriterion("pf_corder_id between", value1, value2, "pfCorderId");
            return (Criteria) this;
        }
        public Criteria andPfCorderIdNotBetween(Long value1, Long value2) {
            addCriterion("pf_corder_id not between", value1, value2, "pfCorderId");
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
        public Criteria andFreightIsNull() {
            addCriterion("freight is null");
            return (Criteria) this;
        }
        public Criteria andFreightIsNotNull() {
            addCriterion("freight is not null");
            return (Criteria) this;
        }
        public Criteria andFreightEqualTo(String value) {
            addCriterion("freight =", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightNotEqualTo(String value) {
            addCriterion("freight <>", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightGreaterThan(String value) {
            addCriterion("freight >", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightGreaterThanOrEqualTo(String value) {
            addCriterion("freight >=", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightLessThan(String value) {
            addCriterion("freight <", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightLessThanOrEqualTo(String value) {
            addCriterion("freight <=", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightLike(String value) {
            addCriterion("freight like", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightNotLike(String value) {
            addCriterion("freight not like", value, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightIn(List<String> values) {
            addCriterion("freight in", values, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightNotIn(List<String> values) {
            addCriterion("freight not in", values, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightBetween(String value1, String value2) {
            addCriterion("freight between", value1, value2, "freight");
            return (Criteria) this;
        }
        public Criteria andFreightNotBetween(String value1, String value2) {
            addCriterion("freight not between", value1, value2, "freight");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    /**
     * 平台分销订单运单表
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