package com.masiis.shop.dao.po;

/**
 * Created by cai_tb on 16/3/23.
 */
public class ComSkuExtension {

    private Integer id;
    private Integer skuId;
    private String poster;
    private String skuBackgroundImg;
    private String illustratingPicture;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSkuBackgroundImg() {
        return skuBackgroundImg;
    }

    public void setSkuBackgroundImg(String skuBackgroundImg) {
        this.skuBackgroundImg = skuBackgroundImg;
    }

    public String getIllustratingPicture() {
        return illustratingPicture;
    }

    public void setIllustratingPicture(String illustratingPicture) {
        this.illustratingPicture = illustratingPicture;
    }

    @Override
    public String toString() {
        return "ComSkuExtension{" +
                "id=" + id +
                ", skuId=" + skuId +
                ", poster='" + poster + '\'' +
                ", skuBackgroundImg='" + skuBackgroundImg + '\'' +
                ", illustratingPicture='" + illustratingPicture + '\'' +
                '}';
    }
}
