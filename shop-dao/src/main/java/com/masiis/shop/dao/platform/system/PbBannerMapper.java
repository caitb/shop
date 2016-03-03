package com.masiis.shop.dao.platform.system;

import com.masiis.shop.dao.po.PbBanner;

import java.util.List;

/**
 * Created by muchaofeng on 2016/3/3.
 */
public interface PbBannerMapper {
    /**
     * 查首页所有的图片
     * @return
     */
    public List<PbBanner> selectAllPbBanner();


}
