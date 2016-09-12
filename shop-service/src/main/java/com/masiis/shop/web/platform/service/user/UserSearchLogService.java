package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserSearchLogMapper;
import com.masiis.shop.dao.po.PfUserSearchLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 搜索日志
 * Created by cai_tb on 16/9/12.
 */
@Service
public class UserSearchLogService {

    @Resource
    private PfUserSearchLogMapper pfUserSearchLogMapper;

    /**
     * 获取搜索内容列表
     * @param userId
     * @return
     */
    public List<String> listSearchContent(Long userId){
        return pfUserSearchLogMapper.selectSearchContent(userId);
    }

    public void deleteContent(String content, Long userId){
        pfUserSearchLogMapper.deleteContent(content, userId);
    }

    public void clearContent(Long userId){
        pfUserSearchLogMapper.clearContent(userId);
    }

    /**
     * 保存搜索日志
     * @param content
     * @param userId
     */
    public void save(String content, Long userId){
        PfUserSearchLog oldPfUserSearchLog = pfUserSearchLogMapper.selectByContent(content, userId);
        if(oldPfUserSearchLog != null && oldPfUserSearchLog.getDeleted().intValue() ==1){
            oldPfUserSearchLog.setDeleted(0);
            pfUserSearchLogMapper.updateByPrimaryKey(oldPfUserSearchLog);
        }
        if(oldPfUserSearchLog != null){
            return;
        }

        PfUserSearchLog pfUserSearchLog = new PfUserSearchLog();
        pfUserSearchLog.setCreateTime(new Date());
        pfUserSearchLog.setContent(content);
        pfUserSearchLog.setDeleted(0);
        pfUserSearchLog.setUserId(userId);

        pfUserSearchLogMapper.insert(pfUserSearchLog);
    }
}
