package com.masiis.shop.api.service.user;

import com.masiis.shop.api.bean.user.City;
import com.masiis.shop.api.bean.user.Country;
import com.masiis.shop.api.bean.user.Province;
import com.masiis.shop.dao.platform.user.ComAreaMapper;
import com.masiis.shop.dao.po.ComArea;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzzh on 2016/3/7.
 */
@Service
@Transactional
public class ComAreaService {

    @Resource
    private ComAreaMapper comAreaMapper;

    public static List<ComArea> comAreas;

    public List<ComArea> queryComAreasByParams(ComArea comArea){
        return comAreaMapper.queryComAreasByParam(new ComArea());
    }

    public List<Province> generateComAreaJson(){
        List<ComArea> provinces = comAreaMapper.queryAllProvince();
        List<Province> returnProvince = new ArrayList<Province>();
        for(ComArea province : provinces){
            List<ComArea> citys = comAreaMapper.queryCityByProviceId(province.getId());
            Province _province = new Province();
            _province.setId(province.getId());
            _province.setName(province.getName());
            _province.setPid(province.getPid());
            List<City> returnCity = new ArrayList<>();
            for (ComArea city: citys){
                List<ComArea> countrys = comAreaMapper.queryCountyByCityId(city.getId());
                City _city = new City();
                _city.setId(city.getId());
                _city.setName(city.getName());
                _city.setPid(city.getPid());
                List<Country> returnContry = new ArrayList<>();
                for (ComArea country:countrys){
                    Country _country = new Country();
                    _country.setId(country.getId());
                    _country.setPid(country.getPid());
                    _country.setName(country.getName());
                    returnContry.add(_country);
                }
                _city.setCountries(returnContry);
                returnCity.add(_city);
            }
            _province.setCities(returnCity);
            returnProvince.add(_province);
        }
        return returnProvince;
    }




    /**
     * 查询所有的省
     * @author  hanzengzhi
     * @date  2016/3/20 20:35
     */
    public List<ComArea> queryAllProvince(){
        return comAreaMapper.queryAllProvince();
    }

    /**
     * 根据省的id查询市
     * @author  hanzengzhi
     * @date  2016/3/20 21:07
     */
    public List<ComArea> queryCityByProviceId(Integer provinceId){
        return comAreaMapper.queryCityByProviceId(provinceId);
    }

    /**
     * 根据市的id查询县区
     * @param cityId
     * @return
     */
    public List<ComArea> queryCountyByCityId(Integer cityId){
        return comAreaMapper.queryCountyByCityId(cityId);
    }
}
