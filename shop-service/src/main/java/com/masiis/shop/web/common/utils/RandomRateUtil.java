package com.masiis.shop.web.common.utils;

import java.util.Map;

/**
 * Created by hzzh on 2016/8/2.
 */
public class RandomRateUtil {


    /**
     * 0出现的概率
     */
    private static   double rate0 = 0.00;
    /**
     * 1出现的概率
     */
    private static double rate1 = 0.00;
    /**
     * 2出现的概率
     */
    private static double rate2 = 0.00;
    /**
     * 3出现的概率
     */
    private static double rate3 = 0.00;
    /**
     * 4出现的概率
     */
    private static double rate4 = 0.00;
    /**
     * 5出现的概率
     */
    private static double rate5 = 0.00;
    /**
     * 6出现的概率
     */
    private static double rate6 = 0.00;
    /**
     * 7出现的概率
     */
    private static double rate7 = 0.00;


    private static RandomRateUtil instance;

    private RandomRateUtil (){}
    public static RandomRateUtil getInstance(){    //对获取实例的方法进行同步
        if (instance == null){
            synchronized(RandomRateUtil.class){
                if (instance == null)
                    instance = new RandomRateUtil();
            }
        }
        return instance;
    }


    private static void initRate(Map<Integer,Double> map){
        for (Integer key : map.keySet()) {
            switch (key){
                case 0:
                    rate0 = map.get(key);
                    break;
                case 1:
                    rate1 = map.get(key);
                    break;
                case 2:
                    rate2 = map.get(key);
                    break;
                case 3:
                    rate3 = map.get(key);
                    break;
                case 4:
                    rate4 = map.get(key);
                    break;
                case 5:
                    rate5 = map.get(key);
                    break;
                case 6:
                    rate6 = map.get(key);
                    break;
                case 7:
                    rate7 = map.get(key);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Math.random()产生一个double型的随机数，判断一下
     * 例如0出现的概率为%50，则介于0到0.50中间的返回0
     * @return int
     *
     */
    public static  int percentageRandom(Map<Integer,Double> map)
    {
        double randomNumber;
        initRate(map);
        randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber <= rate0)
        {
            return 0;
        }
        else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1)
        {
            return 1;
        }
        else if (randomNumber >= rate0 + rate1
                && randomNumber <= rate0 + rate1 + rate2)
        {
            return 2;
        }
        else if (randomNumber >= rate0 + rate1 + rate2
                && randomNumber <= rate0 + rate1 + rate2 + rate3)
        {
            return 3;
        }
        else if (randomNumber >= rate0 + rate1 + rate2 + rate3
                && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4)
        {
            return 4;
        }
        else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4
                && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
                + rate5)
        {
            return 5;
        }
        else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4 + rate5
                && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
                + rate5 +rate6)
        {
            return 6;
        }
        else if (randomNumber >= rate0 + rate1 + rate2 + rate3 + rate4 + rate5 + rate6
                && randomNumber <= rate0 + rate1 + rate2 + rate3 + rate4
                + rate5 + rate6 + rate7)
        {
            return 7;
        }
        return -1;
    }
}
