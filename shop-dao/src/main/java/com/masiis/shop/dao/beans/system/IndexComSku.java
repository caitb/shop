package com.masiis.shop.dao.beans.system;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by muchaofeng on 2016/3/4.
 */
public class IndexComSku {
    private Integer id;//sku主键id
    private BigDecimal shipAmount;//试用费用
    private BigDecimal bail;//保证金
    private Date createTime;//创建日期
    private Long createMan;//创建人
    private Integer spuId;//spu主键id
    private Integer skuId;//sku主键id
    private Integer isTrial;//是否允许试用
    private Long uid;
    private Integer isPay;//是否付款
    private String imgName;//图片名称
    private String imgUrl;//图片地址
    private Date modifyTime;//修改日期
    private Long modifyMan;//修改人
    private Integer isDefault;//默认标志(0否1是)
    private Integer sort;//图片序号。SKU里的图片展示顺序，数据越小越靠前。要求是正整数。
    private String remark;//备注
    private String fullImgUrl;//完整的图片URL地址
    private  String discountLevel;//优惠区间
    private  String bailLevel;//优惠区间
    private Integer maxDiscount;//优惠%
    private ComSku comSku;//商品属性
    private Integer isSalt;//是否下架
    private Integer agentNum;//代理人数
    private Integer isPartner ;//是否为合伙人

    public void setBailLevel(String bailLevel) {
        this.bailLevel = bailLevel;
    }

    public String getBailLevel() {
        return bailLevel;
    }

    public void setIsTrial(Integer isTrial) {
        this.isTrial = isTrial;
    }

    public Integer getIsTrial() {
        return isTrial;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setBail(BigDecimal bail) {this.bail = bail;}
    public BigDecimal getBail() {return bail;}
    public void setMaxDiscount(Integer maxDiscount) {this.maxDiscount = maxDiscount;}
    public Integer getMaxDiscount() {return maxDiscount;}
    public void setShipAmount(BigDecimal shipAmount) {this.shipAmount = shipAmount;}
    public BigDecimal getShipAmount() {return shipAmount;}
    public void setIsPay(Integer isPay) {this.isPay = isPay;}
    public Integer getIsPay() {return isPay;}
    public void setIsSalt(Integer isSalt) {this.isSalt = isSalt;}
    public Integer getIsSalt() {return isSalt;}
    public Integer getIsPartner() {
        return isPartner;
    }
    public void setIsPartner(Integer isPartner) {this.isPartner = isPartner;}
    public void setAgentNum(Integer agentNum) {this.agentNum = agentNum;}
    public Integer getAgentNum() {return agentNum;}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public Integer getSpuId() {
        return spuId;
    }
    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }
    public String getImgName() {
        return imgName;
    }
    public void setImgName(String imgName) {
        this.imgName = imgName == null ? null : imgName.trim();
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
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
    public Integer getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFullImgUrl() {
        return fullImgUrl;
    }

    public void setFullImgUrl(String fullImgUrl) {
        this.fullImgUrl = fullImgUrl;
    }

    public void setDiscountLevel(String discountLevel) {
        this.discountLevel = discountLevel;
    }
    public String getDiscountLevel() {
        return discountLevel;
    }

    public void setComSku(ComSku comSku) {
        this.comSku = comSku;
    }

    public ComSku getComSku() {
        return comSku;
    }
}
