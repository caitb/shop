package com.masiis.shop.web.mall.service.message;

import com.masiis.shop.dao.beans.message.SfMessageDetail;
import com.masiis.shop.dao.mall.message.SfMessageDetailMapper;
import com.masiis.shop.dao.po.ComUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date 2016/7/6
 * @Author lzh
 */
@Service
public class SfMessageDetailService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private SfMessageDetailMapper detailMapper;


    public List<SfMessageDetail> queryMessageDetailFromUser(ComUser user, Long start, Integer size) {
        return detailMapper.queryFromUser(user.getId(), start, size);
    }
}
