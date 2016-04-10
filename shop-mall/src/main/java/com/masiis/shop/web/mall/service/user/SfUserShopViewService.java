package com.masiis.shop.web.mall.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.dao.mall.user.SfUserShopViewExtendMapper;
import com.masiis.shop.dao.po.SfUserShopView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Service
public class SfUserShopViewService {

    private final Logger logger = Logger.getLogger(SfUserShopViewService.class);
    @Autowired
    private SfUserShopViewExtendMapper sfUserShopViewExtendMapper;

    /**
     * 根据用户id查询浏览过的店铺
     * @param userId         用户id
     * @param currentPage   当前页
     * @param pageSize      每页数量
     * @return
     */
    public List<SfUserShopView> findShopViewByUserIdByLimit(Long userId,int currentPage,int pageSize){
        logger.info("根据用户id查询浏览过的店铺");
        //当当前页或者每页数量为0时 不进行分页查询
        if (currentPage == 0 || pageSize == 0){
            return sfUserShopViewExtendMapper.selectViewedShopByUserId(userId);
        }
        PageHelper.startPage(currentPage,pageSize);
        return sfUserShopViewExtendMapper.selectViewedShopByUserId(userId);
    }

}
