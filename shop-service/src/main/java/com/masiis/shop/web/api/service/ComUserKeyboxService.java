package com.masiis.shop.web.api.service;

import com.masiis.shop.dao.platform.user.ComUserKeyboxMapper;
import com.masiis.shop.dao.po.ComUser;
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
        log.info("token = " + token);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return keyboxMapper.selectByToken(token);
    }

    public ComUserKeybox getKeyboxByUserId(Long userId) {
        return keyboxMapper.getByComUserId(userId);
    }

    public ComUserKeybox createKeyboxByUser(ComUser user) {
        ComUserKeybox keybox = new ComUserKeybox();
        return keybox;
    }

    public void insertKeybox(ComUserKeybox keybox) {
        keyboxMapper.insert(keybox);
    }

    public void updateKeybox(ComUserKeybox keybox) {
        keyboxMapper.updateByPrimaryKey(keybox);
    }
}
