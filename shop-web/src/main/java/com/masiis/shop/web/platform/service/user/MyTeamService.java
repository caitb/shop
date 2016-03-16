package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai_tb on 16/3/16.
 */
@Service
public class MyTeamService {

    @Resource
    private PfUserSkuMapper pfUserSkuMapper;
    @Resource
    private ComSkuMapper comSkuMapper;


    /**
     * 获取用户代理的所有产品
     * @param userId
     * @return
     */
    public List<ComSku> listAgentSku(Long userId){
        PfUserSku pfUserSku = new PfUserSku();
        pfUserSku.setUserId(userId);

        List<PfUserSku> pfUserSkus = pfUserSkuMapper.selectByCondition(pfUserSku);

        List<ComSku> comSkus = new ArrayList<>();
        for(PfUserSku pus : pfUserSkus){
            ComSku comSku = comSkuMapper.selectById(pus.getSkuId());
            comSkus.add(comSku);
        }

        return comSkus;
    }
}
