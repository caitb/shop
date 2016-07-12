package com.masiis.shop.web.platform.service.product;

import com.masiis.shop.dao.platform.user.PfUserAgentApplicationMapper;
import com.masiis.shop.dao.po.PfUserAgentApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by jiajinghao on 2016/7/4.
 * 用户意向申请service
 */
@Service
@Transactional
public class PfUserAgentApplicationService {


    @Resource
    private PfUserAgentApplicationMapper pfUserAgentApplicationMapper;
    /**
     * 添加用户意向申请
     */
    public int addApplicationUser(PfUserAgentApplication pfUserAgentApplication) throws Exception{
       return pfUserAgentApplicationMapper.insert(pfUserAgentApplication);
    }

    public PfUserAgentApplication getPfUserAgentApplicationByPhone(String phone){
        return pfUserAgentApplicationMapper.selectByPhone(phone);
    }
}
