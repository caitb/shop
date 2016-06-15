package com.masiis.shop.admin.service.recommend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.admin.beans.recommend.Recommend;
import com.masiis.shop.dao.platform.product.ComSkuMapper;
import com.masiis.shop.dao.platform.user.ComUserMapper;
import com.masiis.shop.dao.platform.user.PfUserRecommenRelationMapper;
import com.masiis.shop.dao.platform.user.PfUserSkuMapper;
import com.masiis.shop.dao.po.ComSku;
import com.masiis.shop.dao.po.ComUser;
import com.masiis.shop.dao.po.PfUserRecommenRelation;
import com.masiis.shop.dao.po.PfUserSku;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cai_tb on 16/6/15.
 */
@Service
@Transactional
public class RecommendService {

    @Resource
    private PfUserRecommenRelationMapper pfUserRecommenRelationMapper;
    @Resource
    private ComUserMapper comUserMapper;
    @Resource
    private ComSkuMapper comSkuMapper;
    @Resource
    private PfUserSkuMapper pfUserSkuMapper;

    public Map<String, Object> list(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, PfUserRecommenRelation pfUserRecommenRelation){
        String sort = "create_time desc";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<PfUserRecommenRelation> pfUserRecommenRelations = pfUserRecommenRelationMapper.selectByCondition(pfUserRecommenRelation);
        PageInfo<PfUserRecommenRelation> pageInfo = new PageInfo<>(pfUserRecommenRelations);


        List<Recommend> recommends = new ArrayList<>();
        for(PfUserRecommenRelation userRecommenRelation : pfUserRecommenRelations){
            //推荐人
            ComUser recommendUser = comUserMapper.selectByPrimaryKey(userRecommenRelation.getUserPid());
            //被推荐人
            ComUser presenteeUser = comUserMapper.selectByPrimaryKey(userRecommenRelation.getUserId());
            //被推荐人上级
            PfUserSku pfUserSku = pfUserSkuMapper.selectByUserIdAndSkuId(userRecommenRelation.getUserId(), userRecommenRelation.getSkuId());
            ComUser presenteePUser = comUserMapper.selectByPrimaryKey(pfUserSku.getUserPid());
            //代理商品
            ComSku comSku = comSkuMapper.selectByPrimaryKey(userRecommenRelation.getSkuId());

            Recommend recommend = new Recommend();
            recommend.setRecommendUser(recommendUser);
            recommend.setPresenteeUser(presenteeUser);
            recommend.setPresenteePUser(presenteePUser);
            recommend.setComSku(comSku);
            recommend.setPfUserRecommenRelation(userRecommenRelation);

            recommends.add(recommend);
        }

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("rows", recommends);
        pageMap.put("total", pageInfo.getTotal());

        return pageMap;
    }
}
