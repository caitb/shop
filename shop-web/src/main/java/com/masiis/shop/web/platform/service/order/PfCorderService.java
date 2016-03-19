package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.order.PfCorderMapper;
import com.masiis.shop.dao.po.PfCorder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    public List<PfCorder> trialCorder(PfCorder pfCorder)throws Exception{
        List<PfCorder> pfCorders = new ArrayList<PfCorder>();
        try{
            pfCorders = pfCorderMapper.trialCorder(pfCorder);
        }catch (Exception e){
            throw new BusinessException(e.getMessage());
        }
        return  pfCorders;
    }
}
