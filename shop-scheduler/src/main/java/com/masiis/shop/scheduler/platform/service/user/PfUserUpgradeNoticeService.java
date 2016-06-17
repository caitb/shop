package com.masiis.shop.scheduler.platform.service.user;

import com.masiis.shop.common.enums.upgrade.UpGradeStatus;
import com.masiis.shop.common.enums.upgrade.UpGradeUpStatus;
import com.masiis.shop.common.exceptions.BusinessException;
import com.masiis.shop.dao.platform.user.PfUserUpgradeNoticeMapper;
import com.masiis.shop.dao.po.PfUserUpgradeNotice;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    /**
     * 查询上级未处理状态和创建时间小于指定时间的未取消通知单
     *
     * @param time  指定时间
     * @return  通知单集合
     */
    public List<PfUserUpgradeNotice> findAllUnsolvedNoticesByDate(Date time) {
        return findUncancelNoticesByStatusAndDate(UpGradeUpStatus.UP_STATUS_Untreated, time);
    }

    /**
     * 查询指定上级处理状态和创建时间小于指定时间的未取消通知单
     *
     * @param status    上级处理状态枚举
     * @param time  指定时间
     * @return  通知单集合
     */
    public List<PfUserUpgradeNotice> findUncancelNoticesByStatusAndDate(UpGradeUpStatus status, Date time) {
        return noticeMapper.selectUncalcelByUpStatusAndDate(status.getCode(), time);
    }

    public int update(PfUserUpgradeNotice notice) {
        return noticeMapper.updateByPrimaryKey(notice);
    }

    @Transactional
    public void handleUnSolvedUpgradeNotice(PfUserUpgradeNotice notice) {
        try {
            if (notice == null) {
                throw new BusinessException("notice is null");
            }
            if(notice.getStatus().intValue() != UpGradeStatus.STATUS_Untreated.getCode().intValue()){
                String str = "升级单status不是{" + UpGradeStatus.STATUS_Untreated.getMessage() + "}状态";
                log.error(str);
                throw new BusinessException(str);
            }
            if(notice.getUpStatus().intValue() != UpGradeUpStatus.UP_STATUS_Untreated.getCode().intValue()){
                String str = "升级单upStatus不是{" + UpGradeUpStatus.UP_STATUS_Untreated.getMessage() + "}状态";
                log.error(str);
                throw new BusinessException(str);
            }
            // 将自身状态置为待支付状态
            notice.setStatus(UpGradeStatus.STATUS_NoPayment.getCode());
            // 将上级处理状态置为暂不升级
            notice.setUpStatus(UpGradeUpStatus.UP_STATUS_NotUpgrade.getCode());
            int res = update(notice);
            if (res != 1) {
                throw new BusinessException("更新升级单失败");
            }
        } catch (Exception e) {
            log.error("处理2天上级未处理升级单失败");
            throw new BusinessException(e);
        }
    }

    public PfUserUpgradeNotice findByPfBorderId(Long id) {
        return noticeMapper.selectByPfBorderId(id);
    }
}
