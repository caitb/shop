package com.masiis.shop.web.material.service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.dao.platform.material.ComUserMaterielApplicationMapper;
import com.masiis.shop.dao.po.ComUserMaterielApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by jiajinghao on 2016/7/6.
 * 用户物料申请service
 */
@Service
@Transactional
public class ComUserMaterielApplicationService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ComUserMaterielApplicationMapper comUserMaterielApplicationMapper;


    /**
     * jjh
     * 申请线下物料到邮箱
     * @param comUserMaterielApplication
     * @return
     */
    public int addMaterielApplication(ComUserMaterielApplication comUserMaterielApplication) throws Exception{
        return comUserMaterielApplicationMapper.insert(comUserMaterielApplication);
    }
}
