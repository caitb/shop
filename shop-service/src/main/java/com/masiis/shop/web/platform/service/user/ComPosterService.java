package com.masiis.shop.web.platform.service.user;

import com.masiis.shop.common.util.OSSObjectUtils;
import com.masiis.shop.dao.platform.user.ComPosterMapper;
import com.masiis.shop.dao.po.ComPoster;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 海报
 * Created by cai_tb on 16/7/18.
 */
@Service
public class ComPosterService {

    @Resource
    private ComPosterMapper comPosterMapper;

    /**
     * 条件查询海报
     * @param comPoster
     * @return
     */
    public ComPoster findByCondition(ComPoster comPoster){
        return comPosterMapper.selectByCondition(comPoster);
    }

    /**
     * 保存海报
     */
    public void add(ComPoster comPoster){
        ComPoster oldComPoster = comPosterMapper.selectByCondition(comPoster);
        if(oldComPoster == null){
            comPosterMapper.insert(comPoster);
        }else{
            comPoster.setId(oldComPoster.getId());
            comPosterMapper.updateByPrimaryKey(comPoster);

            //删除旧海报
            OSSObjectUtils.deleteBucketFile("static/user/poster/"+oldComPoster.getPosterName());
        }
    }

    /**
     * 更新海报
     * @param comPoster
     */
    public void update(ComPoster comPoster){
        comPosterMapper.updateByPrimaryKey(comPoster);
    }
}
