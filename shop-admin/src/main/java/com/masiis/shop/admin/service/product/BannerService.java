package com.masiis.shop.admin.service.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.product.PbBannerMapper;
import com.masiis.shop.dao.po.PbBanner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/13.
 */
@Service
public class BannerService {

    @Resource
    private PbBannerMapper bannerMapper;

    public Map<String,Object> bannerList(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> map) {
        String sort = "banner.id desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> BannerList = bannerMapper.bannerList();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(BannerList);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows",  BannerList);
        return pageMap;
    }

    public void addBanner(PbBanner pbBanner) {
        pbBanner.setCreateTime(new Date());
        bannerMapper.addBanner(pbBanner);
    }

    public void deleteBanner(Integer id) {
        bannerMapper.deleteBanner(id);
    }

    public void editBanner(PbBanner pbBanner ) {
        pbBanner.setCreateTime(new Date());
        bannerMapper.editBanner(pbBanner);
    }

    public PbBanner selectById(Integer id) {
       return bannerMapper.selectById(id);
    }

}
