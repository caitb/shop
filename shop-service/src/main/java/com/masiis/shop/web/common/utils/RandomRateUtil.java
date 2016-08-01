package com.masiis.shop.web.common.utils;

import java.util.Map;

/**
 * Created by hzzh on 2016/8/2.
 */
public class RandomRateUtil {

    public void initRate(Map<Integer,Double> map){
        for (Integer key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }
    }


    /**
     * 0出现的概率为%50
     */
    public static double rate0 = 0.00;
    /**
     * 1出现的概率为%20
     */
    public static double rate1 = 0.00;
    /**
     * 2出现的概率为%15
     */
    public static double rate2 = 0.00;
    /**
     * 3出现的概率为%10
     */
    public static double rate3 = 0.00;
    /**
     * 4出现的概率为%4
     */
    public static double rate4 = 0.00;
    /**
     * 5出现的概率为%1
     */
    public static double rate5 = 1.00;

    /**
     * Math.random()产生一个double型的随机数，判断一下
     * 例如0出现的概率为%50，则介于0到0.50中间的返回0
     * @return int
     *
     */
    public static  int percentageRandom()
    {
        double randomNumber;
        randomNumber = Math.random();
        System.out.println("------------randomNumber--------"+randomNumber);
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
        return -1;
    }
}
