package com.masiis.shop.dao.beans.system;

import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComSkuImage;

import java.util.Date;


/**
 * Created by muchaofeng on 2016/3/4.
 */
public class IndexComSku {
    private Integer id;
    private Date createTime;//创建日期
    private Long createMan;//创建人
    private Integer spuId;//spu主键id
    private Integer skuId;//sku主键id
    private String imgName;//图片名称
    private String imgUrl;//图片地址
    private Date modifyTime;//修改日期
    private Long modifyMan;//修改人
    private Integer isDefault;//默认标志(0否1是)
    private Integer sort;//图片序号。SKU里的图片展示顺序，数据越小越靠前。要求是正整数。
    private String remark;//备注
    private String fullImgUrl;//完整的图片URL地址
    private  String discountLevel;//优惠区间
    private ComSku comSku;//商品属性
    private Integer agentNum;//代理人数
    private Boolean isPartner = false;//是否为合伙人

    public Boolean getIsPartner() {
        return isPartner;
    }
    public void setIsPartner(Boolean isPartner) {this.isPartner = isPartner;}
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
    public Integer getSkuId() {
        return skuId;
    }
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
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
