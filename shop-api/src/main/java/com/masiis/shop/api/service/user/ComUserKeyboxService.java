package com.masiis.shop.api.service.user;

import com.masiis.shop.dao.platform.user.ComUserKeyboxMapper;
import com.masiis.shop.dao.po.ComUserKeybox;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Date 2016/5/3
 * @Auther lzh
 */
@Service
public class ComUserKeyboxService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComUserKeyboxMapper keyboxMapper;

    public ComUserKeybox getComUserKeyboxByToken(String token){
        if(StringUtils.isBlank(token)){
            return null;
        }
        return keyboxMapper.selectByToken(token);
    }
}
