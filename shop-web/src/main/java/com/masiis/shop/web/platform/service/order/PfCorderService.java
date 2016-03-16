package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.po.PfCorder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by hzz on 2016/3/16.
 */
@Service
@Transactional
public class PfCorderService {

    @Resource
    private PfCorderMapper pfCorderMapper;

    /**
     * 根据用户的id和skuId查询用户试用过的产品
     * @param pfCorder
     * @return
     */
    public PfCorder trialCorder(PfCorder pfCorder)throws Exception{
        try{
            pfCorder = pfCorderMapper.trialCorder(pfCorder);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return  pfCorder;
    }
}
