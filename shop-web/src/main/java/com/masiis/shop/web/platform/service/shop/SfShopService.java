package com.masiis.shop.web.platform.service.shop;

import com.masiis.shop.common.util.DateUtil;
import com.masiis.shop.dao.mall.shop.SfShopMapper;
import com.masiis.shop.dao.mall.shop.SfShopShoutLogMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.po.SfShop;
import com.masiis.shop.dao.po.SfShopShoutLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private SfShopMapper sfShopMapper;

    /**
     * 添加个人小铺
     * @author ZhaoLiang
     * @date 2016/4/11 11:25
     */
    public void AddSfShop(SfShop sfShop) {
        sfShopMapper.insert(sfShop);
    }

}
