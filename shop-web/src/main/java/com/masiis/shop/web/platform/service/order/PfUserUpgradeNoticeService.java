package com.masiis.shop.web.platform.service.order;

import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 升级通知
 */
@Service
@Transactional
public class PfUserUpgradeNoticeService {
    @Resource
    private PfUserUpgradeNoticeMapper  noticeMapper;


    public PfUserUpgradeNotice selectByPrimaryKey(Long id){
        return noticeMapper.selectByPrimaryKey(id);
    }
}
