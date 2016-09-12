package com.masiis.shop.admin.service.poster;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.masiis.shop.dao.platform.user.ComPosterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/6.
 */

@Service
public class PosterService {

    @Resource
    private ComPosterMapper comPosterMapper;
    public Map<String,Object> posterList(Integer pageNumber, Integer pageSize, String sortName, String sortOrder, Map<String, Object> map) {
        String sort = "cp.id";
        if (sortName != null) sort = sortName + " " + sortOrder;

        PageHelper.startPage(pageNumber, pageSize, sort);
        List<Map<String, Object>> posterList = comPosterMapper.posterList(map);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(posterList);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("total", pageInfo.getTotal());
        pageMap.put("rows", posterList);
        return pageMap;
    }

    public void delete(Integer id) {
         comPosterMapper.delete(id);
    }
}
