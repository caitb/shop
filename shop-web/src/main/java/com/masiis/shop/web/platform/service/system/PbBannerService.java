package com.masiis.shop.web.platform.service.system;



import com.masiis.shop.dao.platform.system.PbBannerMapper;
import com.masiis.shop.dao.po.PbBanner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by muchaofeng on 2016/3/2.
 */

@Service
@Transactional
public class PbBannerService {
    @Resource
    private PbBannerMapper pbBannerMapper;

    /**
     * 获取所有图片
     *
     * @return
     */
    public List<PbBanner> findPbBanner(){
        return pbBannerMapper.selectAllPbBanner();
    }

}
