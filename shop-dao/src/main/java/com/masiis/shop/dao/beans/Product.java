package com.masiis.shop.dao.beans;

import com.masiis.shop.dao.po.ComSku;

/**
 * Created by ZhaoLiang on 2016/3/2.
 */
public class Product extends ComSku{

    private String spuName;
    private String content;
    private String slogan;
    private String imgUrl;//图片地址

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
