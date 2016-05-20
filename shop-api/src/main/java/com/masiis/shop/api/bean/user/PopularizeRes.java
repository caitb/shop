package com.masiis.shop.api.bean.user;

import com.masiis.shop.api.bean.base.BaseBusinessRes;

/**
 * Created by cai_tb on 16/5/20.
 */
public class PopularizeRes extends BaseBusinessRes {

    private String posterUrl;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "PosterRes{" +
                "posterUrl='" + posterUrl + '\'' +
                '}';
    }

}
