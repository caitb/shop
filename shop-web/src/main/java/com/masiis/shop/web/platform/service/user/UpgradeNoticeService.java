package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by JingHao on 2016/6/15
 * 升级管理service
 */
@Service
@Transactional
public class UpgradeNoticeService {

    @Resource
    private PfUserUpgradeNoticeMapper pfUserUpgradeNoticeMapper;


    /**
     * jjh
     * 我的升级申请记录
     * @param userId
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByUserId(Long userId) throws Exception{
        return pfUserUpgradeNoticeMapper.selectByUserId(userId);
    }

    /**
     * jjh
     * 我的下级申请记录
     * @param userId
     * @return
     */
    public List<PfUserUpgradeNotice> getPfUserUpGradeInfoByUserPId(Long userId) throws Exception {
        return pfUserUpgradeNoticeMapper.selectByUserPId(userId);
    }

}
