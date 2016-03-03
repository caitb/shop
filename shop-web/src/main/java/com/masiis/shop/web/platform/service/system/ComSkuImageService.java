package com.masiis.shop.web.platform.service.system;


import com.masiis.shop.dao.platform.system.ComSkuImageMap;
import com.masiis.shop.dao.po.ComSkuImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/2.
 */

@Service
@Transactional
public class ComSkuImageService {
    @Resource
    private ComSkuImageMap comSkuImageMap;

    /**
     * 获取所有图片
     *
     * @return
     */
    public List<ComSkuImage> findImage(){
        System.out.println(comSkuImageMap.selectAllComSkuImage());
        return comSkuImageMap.selectAllComSkuImage();
    }
}
