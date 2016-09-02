package com.masiis.shop.admin.service.user;

import com.masiis.shop.dao.platform.user.PfUserOrganizationMapper;
import com.masiis.shop.dao.po.PfUserOrganization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiajinghao on 2016/8/4.
 * 组织（家族，团队）service
 */
@Service
@Transactional
public class PfUserOrganizationService {

    @Resource
    private PfUserOrganizationMapper pfUserOrganizationMapper;


    public PfUserOrganization getByUserIdAndBrandId(Long userId, Integer brandId) {
        return pfUserOrganizationMapper.selectByBrandIdAndUserId(brandId, userId);
    }

    public int update(PfUserOrganization pfUserOrganization) {
        return pfUserOrganizationMapper.updateByPrimaryKey(pfUserOrganization);
    }
    public String handlerName(String name, String suffix){
        StringBuilder sb = new StringBuilder(name);
        Pattern p = Pattern.compile(suffix);
        Matcher m = p.matcher(sb);
        while (m.find()){
            sb.delete(m.start(), m.start()+suffix.length());
            m = p.matcher(sb);
        }

        //sb.append(suffix);

        return sb.toString();
    }

}
