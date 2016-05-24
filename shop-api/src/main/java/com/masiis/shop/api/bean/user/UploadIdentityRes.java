package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseRes;

/**
 * Created by hzz on 2016/5/24.
 */
public class UploadIdentityRes extends BaseRes {
    private String imageName;
    private String imagePath;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
