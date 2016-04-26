package com.masiis.shop.admin.service.wx;

import com.masiis.shop.dao.platform.user.ComWxUserMapper;
import com.masiis.shop.dao.po.ComWxUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Date:2016/4/8
 * @auth:lzh
 */
@Service
public class WxUserService {
    private Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ComWxUserMapper wxUserMapper;

    public List<ComWxUser> getWxUserByUnionid(String unionid) {
        return wxUserMapper.selectByUnionid(unionid);
    }

    public void insertWxUser(ComWxUser wxUser) {
        wxUserMapper.insert(wxUser);
    }

    public int updateWxUser(ComWxUser wxUser) {
        return wxUserMapper.updateByPrimaryKey(wxUser);
    }

    /**
     * 根据union和appid唯一确定一条wxUser记录
     *
     * @param unionid
     * @param appid
     * @return
     */
    public ComWxUser getUserByUnionidAndAppid(String unionid, String appid) {
        return wxUserMapper.selectByUnionidAndAppid(unionid, appid);
    }

    public ComWxUser getWxUserByOpenIdAndAppID(String openid, String appid) {
        return wxUserMapper.selectByOpenidAndAppid(openid, appid);
    }
}
