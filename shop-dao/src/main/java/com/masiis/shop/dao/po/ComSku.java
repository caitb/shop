package com.masiis.shop.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class ComSku {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.id
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.name
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private String name;

    private String alias;

    private String eName;

    /**
     * 商品标志
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.spu_id
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private Integer spuId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.create_time
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.create_man
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private Long createMan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.modify_time
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private Date modifyTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.modify_man
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private Long modifyMan;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.bar_code
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private String barCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.price_cost
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private BigDecimal priceCost;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.price_market
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private BigDecimal priceMarket;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.price_retail
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private BigDecimal priceRetail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column com_sku.remark
     *
     * @mbggenerated Wed Mar 09 11:18:30 CST 2016
     */
    private String remark;

    private String cName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyMan() {
        return modifyMan;
    }

    public void setModifyMan(Long modifyMan) {
        this.modifyMan = modifyMan;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BigDecimal getPriceCost() {
        return priceCost;
    }

    public void setPriceCost(BigDecimal priceCost) {
        this.priceCost = priceCost;
    }

    public BigDecimal getPriceMarket() {
        return priceMarket;
    }

    public void setPriceMarket(BigDecimal priceMarket) {
        this.priceMarket = priceMarket;
    }

    public BigDecimal getPriceRetail() {
        return priceRetail;
    }

    public void setPriceRetail(BigDecimal priceRetail) {
        this.priceRetail = priceRetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @Override
    public String toString() {
        return "ComSku{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", eName='" + eName + '\'' +
                ", icon='" + icon + '\'' +
                ", spuId=" + spuId +
                ", createTime=" + createTime +
                ", createMan=" + createMan +
                ", modifyTime=" + modifyTime +
                ", modifyMan=" + modifyMan +
                ", barCode='" + barCode + '\'' +
                ", priceCost=" + priceCost +
                ", priceMarket=" + priceMarket +
                ", priceRetail=" + priceRetail +
                ", remark='" + remark + '\'' +
                '}';
    }
}