package com.masiis.shop.web.mall.service.shop;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.common.util.PropertiesUtils;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopShoutLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfShopShoutLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/8.
 */
@Service
@Transactional
public class SfShopService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private SfShopMapper sfShopMapper;
    @Resource
    private SfShopShoutLogMapper sfShopShoutLogMapper;

    /**
     * 呐喊
     * @param shopId 小铺id
     * @param userId 呐喊人id
     */
    public boolean shout(Long shopId, Long userId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", userId);
        condition.put("shopId", shopId);
        condition.put("createTime", "%"+sdf.format(new Date()).toString()+"%");

        SfShopShoutLog sfShopShoutLog = sfShopShoutLogMapper.selectByCondition(condition);
        if(sfShopShoutLog == null){
            SfShop sfShop = sfShopMapper.selectByPrimaryKey(shopId);
            sfShop.setShoutNum(sfShop.getShoutNum()+1);

            sfShopShoutLog = new SfShopShoutLog();
            sfShopShoutLog.setCreateTime(new Date());
            sfShopShoutLog.setNum(1);
            sfShopShoutLog.setShopId(shopId);
            sfShopShoutLog.setShopUserId(sfShop.getUserId());
            sfShopShoutLog.setUserId(userId);

            sfShopMapper.updateByPrimaryKey(sfShop);
            sfShopShoutLogMapper.insert(sfShopShoutLog);

            return true;
        }

        return false;
    }

    /**
     * 根据id获得小铺信息
     * @author hanzengzhi
     * @date 2016/4/9 12:04
     */
    public SfShop getSfShopById(Long id){
        return sfShopMapper.selectByPrimaryKey(id);
    }

    /**
     * 判断呐喊
     * @author muchaofeng
     * @date 2016/4/10 10:56
     */
    public boolean mallShout(Long userId, Long shopId){
        try{
            Map<String, Object> map = new HashMap<>();

            map.put("userId", userId);
            map.put("shopId", shopId);
            map.put("createTime", new Date());
            Date minDay = DateUtil.getMinTimeofDay(new Date());
            Date maxDay = DateUtil.getMaxTimeofDay(new Date());
            map.put("minDay", minDay);
            map.put("maxDay", maxDay);

            log.info("mallShout进入了。。。");
            SfShopShoutLog sfShopShoutLog = sfShopShoutLogMapper.selectByMap(map);
            if(sfShopShoutLog != null){
                log.info("用户已经呐喊过。。。");
                return false;
            }else{
                log.info("用户未呐喊。。。");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        return true;
    }

    /**
     * 根据小铺归属人查询小铺信息
     * @param userId
     * @return
     */
    public SfShop getSfShopByUserId(Long userId){
        return sfShopMapper.selectByUserId(userId);
    }

    /**
     * 添加个人小铺
     * @author ZhaoLiang
     * @date 2016/4/11 11:25
     */
    public void AddSfShop(SfShop sfShop) {
        sfShopMapper.insert(sfShop);
    }

    /**
     * 更新店铺信息
     * @param sfShop
     */
    public int updateById(SfShop sfShop){
        return sfShopMapper.updateByPrimaryKey(sfShop);
    }

    /**
     * jjh
     * @param code 微信二维码文件名
     * @return
     */
    public String getWXQRImgByCode(String code) {
        String WXQRImg = null;
        String baseValue = PropertiesUtils.getStringValue("oss.BASE_URL");
        String shopValue = PropertiesUtils.getStringValue("oss.OSS_SHOPMAN_WX_QRCODE");
        WXQRImg = baseValue +"/"+shopValue + code;
        return WXQRImg;
    }

    /**
     * 查询分享过的小铺列表
     * @param userId    用户id
     * @return  list
     */
    public List<SfShop> getSharedShops(Long userId){
        return sfShopMapper.selectSharedShops(userId);
    }
}
