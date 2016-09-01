package com.masiis.shop.api.bean.upload;

import com.masiis.shop.api.bean.base.BaseBusinessReq;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by cai_tb on 16/8/15.
 */
public class UploadReq extends BaseBusinessReq {

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
