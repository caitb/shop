package com.masiis.shop.scheduler.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 升级通知单service
 *
 * @Date 2016/6/16
 * @author lzh
 */
@Service
public class PfUserUpgradeNoticeService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private PfUserUpgradeNoticeMapper noticeMapper;

    public PfUserUpgradeNotice findById(Long id){
        return noticeMapper.selectByPrimaryKey(id);
    }

    public List<PfUserUpgradeNotice> findAllUnsolvedNoticesByDate() {
        return null;
    }

    public List<PfUserUpgradeNotice> findNoticesByStatusAndDate() {
        return null;
    }
}
