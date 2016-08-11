package com.masiis.shop.web.common.utils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by hzzh on 2016/8/2.
 */
public class RandomRateUtil {

    private static Log log = LogFactory.getLog(RandomRateUtil.getInstance().getClass());

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


    private static void initRate(Map<Integer,Double> map,Map<Integer,Boolean> quantityEnoughMap,Map<Integer,Boolean> isGiftMap){
        for (Integer key : map.keySet()) {
            switch (key){
                case 0:
                    if (quantityEnoughMap.get(0)){
                        rate0 = map.get(key)/100;
                    }else if (isGiftMap.get(0)){
                        rate0 = 0;
                    }
                    log.info("rate0-------------"+rate0);
                    break;
                case 1:
                    if (quantityEnoughMap.get(1)){
                        rate1 = map.get(key)/100;
                    }else if (isGiftMap.get(1)){
                        rate1 = 0;
                    }
                    log.info("rate1-------------"+rate1);
                    break;
                case 2:
                    if (quantityEnoughMap.get(2)){
                        rate2 = map.get(key)/100;
                    }else if (isGiftMap.get(2)){
                        rate2 = 0;
                    }
                    log.info("rate2-------------"+rate2);
                    break;
                case 3:
                    if (quantityEnoughMap.get(3)){
                        rate3 = map.get(key)/100;
                    }else if (isGiftMap.get(3)){
                        rate3 = 0;
                    }
                    log.info("rate3-------------"+rate3);
                    break;
                case 4:
                    if (quantityEnoughMap.get(4)){
                        rate4 = map.get(key)/100;
                    }else if (isGiftMap.get(4)){
                        rate4 = 0;
                    }
                    log.info("rate4-------------"+rate4);
                    break;
                case 5:
                    if (quantityEnoughMap.get(5)){
                        rate5 = map.get(key)/100;
                    }else if (isGiftMap.get(5)){
                        rate5 = 0;
                    }
                    log.info("rate5-------------"+rate5);
                    break;
                case 6:
                    if (quantityEnoughMap.get(6)){
                        rate6 = map.get(key)/100;
                    }else if (isGiftMap.get(6)){
                        rate6 = 0;
                    }
                    log.info("rate6-------------"+rate6);
                    break;
                case 7:
                    if (quantityEnoughMap.get(7)){
                        rate7 = map.get(key)/100;
                    }else if (isGiftMap.get(7)){
                        rate7 = 0;
                    }
                    log.info("rate7-------------"+rate7);
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
    public static  int percentageRandom(Map<Integer,Double> rateMap,Map<Integer,Boolean> quantityEnoughMap ,Map<Integer,Boolean> isGiftMap,List<Integer> noGiftSorts)
    {
        double randomNumber;
        initRate(rateMap,quantityEnoughMap,isGiftMap);
        randomNumber = Math.random();
        log.info("randomNumber-------------"+randomNumber);
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
        }else{
            //从不是奖品中随机选取一个
            log.info("-----从不是奖品中随机选取一个------");
            return getRandowNumberFromList(noGiftSorts);
        }
    }

    public static int getRandowNumberFromList(List<Integer> noGiftSorts){
        Random rand = new Random();
        for (int i= 0;i<noGiftSorts.size();i++){
            log.info("-----不是奖品的序号-----"+noGiftSorts.get(i));
        }
        return noGiftSorts.get(rand.nextInt(noGiftSorts.size())) ;
    }

    //随机生成手机号
    public static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    public static void main(String[] args){
        for (int i=0;i<30;i++){
            int index=getNum(0,telFirst.length-1);
            String first=telFirst[index];
            String second="****";
            String thrid=String.valueOf(getNum(1,9100)+10000).substring(1);
            System.out.println(first+second+thrid);
        }
    }
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
}
