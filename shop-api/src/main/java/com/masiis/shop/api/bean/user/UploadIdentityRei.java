package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessReq;

import java.io.InputStream;

/**
 * Created by hzz on 2016/5/24.
 */
public class UploadIdentityRei extends BaseBusinessReq {
    private InputStream inputStream;
    private byte[] bytes;
    private String imageType;
    private Long size;


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }



}
