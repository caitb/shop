package com.masiis.shop.web.mall.service.user;

import com.github.pagehelper.PageHelper;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.user.SfUserShopViewExtendMapper;
import com.masiis.shop.dao.mall.user.SfUserShopViewMapper;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfUserShopView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wangbingjian on 2016/4/10.
 */
@Service
public class SfUserShopViewService {

    private final Logger logger = Logger.getLogger(SfUserShopViewService.class);
    @Autowired
    private SfUserShopViewExtendMapper sfUserShopViewExtendMapper;

    @Autowired
    protected SfUserShopViewMapper sfUserShopViewMapper;
    @Autowired
    protected SfShopMapper sfshopMapper;


    /**
     * 根据用户id查询浏览过的店铺
     * @param userId         用户id
     * @param currentPage   当前页
     * @param pageSize      每页数量
     * @return
     * @author:wbj
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

    /**
     * 查询总数
     * @param userId
     * @return
     */
    public Integer findCountByUserId(Long userId){
        return sfUserShopViewExtendMapper.selectCountByUserId(userId);
    }


    /**
      * @Author jjh
      * @Date 2016/4/19 0019 下午 3:14
      * 增加店铺浏览量
      */

    public void addShopView(Long userId,Long shopId){
        try {
            SfShop sfShop = sfshopMapper.selectByPrimaryKey(shopId);
            if(sfShop==null){
                throw new BusinessException("店铺不存在");
            }
            SfUserShopView sfUserShopView =sfUserShopViewMapper.selectByShopIdAndUserId(userId,shopId);
            if(sfUserShopView==null){
                SfUserShopView sf = new SfUserShopView();
                sf.setCreateTime(new Date());
                sf.setShopId(shopId);
                sf.setUserId(userId);
                sf.setShopUserId(sfShop.getUserId());
                sf.setRemark("");
                sfUserShopViewMapper.insert(sf);
                logger.info(""+userId+"浏览了店铺"+shopId+"");
            }else{
                sfUserShopView.setCreateTime(new Date());
                int updateByPrimaryKey = sfUserShopViewMapper.updateByPrimaryKey(sfUserShopView);
                if(updateByPrimaryKey==0){
                    throw new BusinessException("更新浏览店铺时间失败");
                }
                logger.info(""+userId+"更新浏览了店铺"+shopId+"时间");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
