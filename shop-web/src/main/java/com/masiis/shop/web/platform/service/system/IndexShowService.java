package com.masiis.shop.web.platform.service.system;

import com.masiis.shop.dao.beans.system.IndexComSku;
import com.masiis.shop.dao.platform.system.IndexShowMapper;
import com.masiis.shop.dao.po.PbBanner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by muchaofeng on 2016/3/4.
 */
@Service
@Transactional
public class IndexShowService {
    @Resource
    private IndexShowMapper indexShowMapper;

    /**
     * 获取isDefault=1所有图片
     *
     * @return
     */
    public List<IndexComSku> findIndexComSku(Long userId) throws Exception{
        return indexShowMapper.selectAllIndexComSku(userId);
    }
    /**
     * 获取所有展示商品信息
     *
     * @return
     */
    public List<PbBanner> findPbBanner()throws Exception{
        return indexShowMapper.selectAllPbBanner();
    }
}
