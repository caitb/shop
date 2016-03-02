/*
 * ComSpuExample.java
 * Copyright(C) 2014-2016 麦士集团
 * All rights reserved.
 * -----------------------------------------------
 * 2016-03-02 Created
 */
package com.masiis.shop.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComSpuExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public ComSpuExample() {
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
     * 商品spu表
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
        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }
        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }
        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }
        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }
        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }
        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }
        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }
        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
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
        public Criteria andCategoryIdIsNull() {
            addCriterion("category_id is null");
            return (Criteria) this;
        }
        public Criteria andCategoryIdIsNotNull() {
            addCriterion("category_id is not null");
            return (Criteria) this;
        }
        public Criteria andCategoryIdEqualTo(Integer value) {
            addCriterion("category_id =", value, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdNotEqualTo(Integer value) {
            addCriterion("category_id <>", value, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdGreaterThan(Integer value) {
            addCriterion("category_id >", value, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("category_id >=", value, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdLessThan(Integer value) {
            addCriterion("category_id <", value, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdLessThanOrEqualTo(Integer value) {
            addCriterion("category_id <=", value, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdIn(List<Integer> values) {
            addCriterion("category_id in", values, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdNotIn(List<Integer> values) {
            addCriterion("category_id not in", values, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdBetween(Integer value1, Integer value2) {
            addCriterion("category_id between", value1, value2, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryIdNotBetween(Integer value1, Integer value2) {
            addCriterion("category_id not between", value1, value2, "categoryId");
            return (Criteria) this;
        }
        public Criteria andCategoryNameIsNull() {
            addCriterion("category_name is null");
            return (Criteria) this;
        }
        public Criteria andCategoryNameIsNotNull() {
            addCriterion("category_name is not null");
            return (Criteria) this;
        }
        public Criteria andCategoryNameEqualTo(String value) {
            addCriterion("category_name =", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameNotEqualTo(String value) {
            addCriterion("category_name <>", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameGreaterThan(String value) {
            addCriterion("category_name >", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameGreaterThanOrEqualTo(String value) {
            addCriterion("category_name >=", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameLessThan(String value) {
            addCriterion("category_name <", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameLessThanOrEqualTo(String value) {
            addCriterion("category_name <=", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameLike(String value) {
            addCriterion("category_name like", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameNotLike(String value) {
            addCriterion("category_name not like", value, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameIn(List<String> values) {
            addCriterion("category_name in", values, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameNotIn(List<String> values) {
            addCriterion("category_name not in", values, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameBetween(String value1, String value2) {
            addCriterion("category_name between", value1, value2, "categoryName");
            return (Criteria) this;
        }
        public Criteria andCategoryNameNotBetween(String value1, String value2) {
            addCriterion("category_name not between", value1, value2, "categoryName");
            return (Criteria) this;
        }
        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }
        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }
        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
        public Criteria andIsSaleIsNull() {
            addCriterion("is_sale is null");
            return (Criteria) this;
        }
        public Criteria andIsSaleIsNotNull() {
            addCriterion("is_sale is not null");
            return (Criteria) this;
        }
        public Criteria andIsSaleEqualTo(Integer value) {
            addCriterion("is_sale =", value, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleNotEqualTo(Integer value) {
            addCriterion("is_sale <>", value, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleGreaterThan(Integer value) {
            addCriterion("is_sale >", value, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_sale >=", value, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleLessThan(Integer value) {
            addCriterion("is_sale <", value, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleLessThanOrEqualTo(Integer value) {
            addCriterion("is_sale <=", value, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleIn(List<Integer> values) {
            addCriterion("is_sale in", values, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleNotIn(List<Integer> values) {
            addCriterion("is_sale not in", values, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleBetween(Integer value1, Integer value2) {
            addCriterion("is_sale between", value1, value2, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsSaleNotBetween(Integer value1, Integer value2) {
            addCriterion("is_sale not between", value1, value2, "isSale");
            return (Criteria) this;
        }
        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }
        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }
        public Criteria andIsDeleteEqualTo(Integer value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteNotEqualTo(Integer value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteGreaterThan(Integer value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteLessThan(Integer value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteLessThanOrEqualTo(Integer value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteIn(List<Integer> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteNotIn(List<Integer> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteBetween(Integer value1, Integer value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }
        public Criteria andIsDeleteNotBetween(Integer value1, Integer value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }
        public Criteria andBrandIdIsNull() {
            addCriterion("brand_id is null");
            return (Criteria) this;
        }
        public Criteria andBrandIdIsNotNull() {
            addCriterion("brand_id is not null");
            return (Criteria) this;
        }
        public Criteria andBrandIdEqualTo(Integer value) {
            addCriterion("brand_id =", value, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdNotEqualTo(Integer value) {
            addCriterion("brand_id <>", value, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdGreaterThan(Integer value) {
            addCriterion("brand_id >", value, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("brand_id >=", value, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdLessThan(Integer value) {
            addCriterion("brand_id <", value, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdLessThanOrEqualTo(Integer value) {
            addCriterion("brand_id <=", value, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdIn(List<Integer> values) {
            addCriterion("brand_id in", values, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdNotIn(List<Integer> values) {
            addCriterion("brand_id not in", values, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdBetween(Integer value1, Integer value2) {
            addCriterion("brand_id between", value1, value2, "brandId");
            return (Criteria) this;
        }
        public Criteria andBrandIdNotBetween(Integer value1, Integer value2) {
            addCriterion("brand_id not between", value1, value2, "brandId");
            return (Criteria) this;
        }
        public Criteria andUpTimeIsNull() {
            addCriterion("up_time is null");
            return (Criteria) this;
        }
        public Criteria andUpTimeIsNotNull() {
            addCriterion("up_time is not null");
            return (Criteria) this;
        }
        public Criteria andUpTimeEqualTo(Date value) {
            addCriterion("up_time =", value, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeNotEqualTo(Date value) {
            addCriterion("up_time <>", value, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeGreaterThan(Date value) {
            addCriterion("up_time >", value, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("up_time >=", value, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeLessThan(Date value) {
            addCriterion("up_time <", value, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeLessThanOrEqualTo(Date value) {
            addCriterion("up_time <=", value, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeIn(List<Date> values) {
            addCriterion("up_time in", values, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeNotIn(List<Date> values) {
            addCriterion("up_time not in", values, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeBetween(Date value1, Date value2) {
            addCriterion("up_time between", value1, value2, "upTime");
            return (Criteria) this;
        }
        public Criteria andUpTimeNotBetween(Date value1, Date value2) {
            addCriterion("up_time not between", value1, value2, "upTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeIsNull() {
            addCriterion("down_time is null");
            return (Criteria) this;
        }
        public Criteria andDownTimeIsNotNull() {
            addCriterion("down_time is not null");
            return (Criteria) this;
        }
        public Criteria andDownTimeEqualTo(Date value) {
            addCriterion("down_time =", value, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeNotEqualTo(Date value) {
            addCriterion("down_time <>", value, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeGreaterThan(Date value) {
            addCriterion("down_time >", value, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("down_time >=", value, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeLessThan(Date value) {
            addCriterion("down_time <", value, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeLessThanOrEqualTo(Date value) {
            addCriterion("down_time <=", value, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeIn(List<Date> values) {
            addCriterion("down_time in", values, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeNotIn(List<Date> values) {
            addCriterion("down_time not in", values, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeBetween(Date value1, Date value2) {
            addCriterion("down_time between", value1, value2, "downTime");
            return (Criteria) this;
        }
        public Criteria andDownTimeNotBetween(Date value1, Date value2) {
            addCriterion("down_time not between", value1, value2, "downTime");
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
        public Criteria andSloganIsNull() {
            addCriterion("slogan is null");
            return (Criteria) this;
        }
        public Criteria andSloganIsNotNull() {
            addCriterion("slogan is not null");
            return (Criteria) this;
        }
        public Criteria andSloganEqualTo(String value) {
            addCriterion("slogan =", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganNotEqualTo(String value) {
            addCriterion("slogan <>", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganGreaterThan(String value) {
            addCriterion("slogan >", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganGreaterThanOrEqualTo(String value) {
            addCriterion("slogan >=", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganLessThan(String value) {
            addCriterion("slogan <", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganLessThanOrEqualTo(String value) {
            addCriterion("slogan <=", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganLike(String value) {
            addCriterion("slogan like", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganNotLike(String value) {
            addCriterion("slogan not like", value, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganIn(List<String> values) {
            addCriterion("slogan in", values, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganNotIn(List<String> values) {
            addCriterion("slogan not in", values, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganBetween(String value1, String value2) {
            addCriterion("slogan between", value1, value2, "slogan");
            return (Criteria) this;
        }
        public Criteria andSloganNotBetween(String value1, String value2) {
            addCriterion("slogan not between", value1, value2, "slogan");
            return (Criteria) this;
        }
        public Criteria andIsTrialIsNull() {
            addCriterion("is_trial is null");
            return (Criteria) this;
        }
        public Criteria andIsTrialIsNotNull() {
            addCriterion("is_trial is not null");
            return (Criteria) this;
        }
        public Criteria andIsTrialEqualTo(Integer value) {
            addCriterion("is_trial =", value, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialNotEqualTo(Integer value) {
            addCriterion("is_trial <>", value, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialGreaterThan(Integer value) {
            addCriterion("is_trial >", value, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_trial >=", value, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialLessThan(Integer value) {
            addCriterion("is_trial <", value, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialLessThanOrEqualTo(Integer value) {
            addCriterion("is_trial <=", value, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialIn(List<Integer> values) {
            addCriterion("is_trial in", values, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialNotIn(List<Integer> values) {
            addCriterion("is_trial not in", values, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialBetween(Integer value1, Integer value2) {
            addCriterion("is_trial between", value1, value2, "isTrial");
            return (Criteria) this;
        }
        public Criteria andIsTrialNotBetween(Integer value1, Integer value2) {
            addCriterion("is_trial not between", value1, value2, "isTrial");
            return (Criteria) this;
        }
        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }
        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }
        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }
        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }
        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }
        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }
        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }
        public Criteria andWeightIsNull() {
            addCriterion("weight is null");
            return (Criteria) this;
        }
        public Criteria andWeightIsNotNull() {
            addCriterion("weight is not null");
            return (Criteria) this;
        }
        public Criteria andWeightEqualTo(Long value) {
            addCriterion("weight =", value, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightNotEqualTo(Long value) {
            addCriterion("weight <>", value, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightGreaterThan(Long value) {
            addCriterion("weight >", value, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightGreaterThanOrEqualTo(Long value) {
            addCriterion("weight >=", value, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightLessThan(Long value) {
            addCriterion("weight <", value, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightLessThanOrEqualTo(Long value) {
            addCriterion("weight <=", value, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightIn(List<Long> values) {
            addCriterion("weight in", values, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightNotIn(List<Long> values) {
            addCriterion("weight not in", values, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightBetween(Long value1, Long value2) {
            addCriterion("weight between", value1, value2, "weight");
            return (Criteria) this;
        }
        public Criteria andWeightNotBetween(Long value1, Long value2) {
            addCriterion("weight not between", value1, value2, "weight");
            return (Criteria) this;
        }
        public Criteria andPackLengthIsNull() {
            addCriterion("pack_length is null");
            return (Criteria) this;
        }
        public Criteria andPackLengthIsNotNull() {
            addCriterion("pack_length is not null");
            return (Criteria) this;
        }
        public Criteria andPackLengthEqualTo(Long value) {
            addCriterion("pack_length =", value, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthNotEqualTo(Long value) {
            addCriterion("pack_length <>", value, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthGreaterThan(Long value) {
            addCriterion("pack_length >", value, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthGreaterThanOrEqualTo(Long value) {
            addCriterion("pack_length >=", value, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthLessThan(Long value) {
            addCriterion("pack_length <", value, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthLessThanOrEqualTo(Long value) {
            addCriterion("pack_length <=", value, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthIn(List<Long> values) {
            addCriterion("pack_length in", values, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthNotIn(List<Long> values) {
            addCriterion("pack_length not in", values, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthBetween(Long value1, Long value2) {
            addCriterion("pack_length between", value1, value2, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackLengthNotBetween(Long value1, Long value2) {
            addCriterion("pack_length not between", value1, value2, "packLength");
            return (Criteria) this;
        }
        public Criteria andPackWidthIsNull() {
            addCriterion("pack_width is null");
            return (Criteria) this;
        }
        public Criteria andPackWidthIsNotNull() {
            addCriterion("pack_width is not null");
            return (Criteria) this;
        }
        public Criteria andPackWidthEqualTo(Long value) {
            addCriterion("pack_width =", value, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthNotEqualTo(Long value) {
            addCriterion("pack_width <>", value, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthGreaterThan(Long value) {
            addCriterion("pack_width >", value, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthGreaterThanOrEqualTo(Long value) {
            addCriterion("pack_width >=", value, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthLessThan(Long value) {
            addCriterion("pack_width <", value, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthLessThanOrEqualTo(Long value) {
            addCriterion("pack_width <=", value, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthIn(List<Long> values) {
            addCriterion("pack_width in", values, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthNotIn(List<Long> values) {
            addCriterion("pack_width not in", values, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthBetween(Long value1, Long value2) {
            addCriterion("pack_width between", value1, value2, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackWidthNotBetween(Long value1, Long value2) {
            addCriterion("pack_width not between", value1, value2, "packWidth");
            return (Criteria) this;
        }
        public Criteria andPackHeightIsNull() {
            addCriterion("pack_height is null");
            return (Criteria) this;
        }
        public Criteria andPackHeightIsNotNull() {
            addCriterion("pack_height is not null");
            return (Criteria) this;
        }
        public Criteria andPackHeightEqualTo(Long value) {
            addCriterion("pack_height =", value, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightNotEqualTo(Long value) {
            addCriterion("pack_height <>", value, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightGreaterThan(Long value) {
            addCriterion("pack_height >", value, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightGreaterThanOrEqualTo(Long value) {
            addCriterion("pack_height >=", value, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightLessThan(Long value) {
            addCriterion("pack_height <", value, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightLessThanOrEqualTo(Long value) {
            addCriterion("pack_height <=", value, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightIn(List<Long> values) {
            addCriterion("pack_height in", values, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightNotIn(List<Long> values) {
            addCriterion("pack_height not in", values, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightBetween(Long value1, Long value2) {
            addCriterion("pack_height between", value1, value2, "packHeight");
            return (Criteria) this;
        }
        public Criteria andPackHeightNotBetween(Long value1, Long value2) {
            addCriterion("pack_height not between", value1, value2, "packHeight");
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
     * 商品spu表
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