package com.masiis.shop.web.mall.service.shop;

import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopShoutLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfShopShoutLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cai_tb on 16/4/8.
 */
@Service
public class SfShopService {

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
        condition.put("createTime", sdf.format(new Date()).toString());

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

}
