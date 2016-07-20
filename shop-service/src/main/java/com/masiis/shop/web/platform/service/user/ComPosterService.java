package com.masiis.shop.web.platform.service.user;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
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

    private final static Log log = LogFactory.getLog(ComPosterService.class);

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

        log.info("查询参数[comPoster="+comPoster+"];");
        log.info("旧海报数据[oldComPoster="+oldComPoster+"];");
        if(oldComPoster == null){
            comPosterMapper.insert(comPoster);
            log.info("保存海报数据[comPoster="+comPoster+"]");
        }else{
            comPoster.setId(oldComPoster.getId());
            comPosterMapper.updateByPrimaryKey(comPoster);
            log.info("更新海报数据[comPoster="+comPoster+"]");

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
