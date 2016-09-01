package com.masiis.shop.dao.platform.product;

import com.masiis.shop.dao.po.PbBanner;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/13.
 */
@Repository
public interface PbBannerMapper {

    /**
     * 查询banner
     * @return
     */
    List<Map<String,Object>> bannerList();

    /**
     * 添加Banner
     */
    void addBanner(PbBanner pbBanner);

    /**
     * 根据id删除一条数据
     * @param id
     */
    void deleteBanner(Integer id);

    /**
     * 根据id查询banner
     * @param id
     */
    PbBanner selectById(Integer id);

    /**
     * 根据id编辑数据
     */
    void editBanner(PbBanner pbBanner);

    List<PbBanner> selectAll();

}
