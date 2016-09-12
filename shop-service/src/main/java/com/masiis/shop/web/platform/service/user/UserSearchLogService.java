package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.dao.platform.user.PfUserSearchLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    }
}
