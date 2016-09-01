package com.masiis.shop.web.common.utils;

/**
 * Created by jiajinghao on 2016/8/8.
 */
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List按照指定字段排序工具类
 * @param <T>
 */
public class ListSortUtil<T> {
        public void sort(List<T> targetList, final String sortField, final String sortMode){
            Collections.sort(targetList, new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    int retVal = 0;
                    try {
                      //首字母转大写
                        String newStr = sortField.substring(0, 1).toUpperCase() + sortField.replaceFirst("\\w", "");
                        String methodStr = "get" + newStr;

                        Method method1 = ((T) obj1).getClass().getMethod(methodStr, null);
                        Method method2 = ((T) obj2).getClass().getMethod(methodStr, null);
                        if(method1.invoke(((T) obj2)) instanceof BigDecimal){
                            BigDecimal a = (BigDecimal)method1.invoke(((T) obj2), null);
                            BigDecimal b = (BigDecimal)method2.invoke(((T) obj1), null);
                            if (sortMode != null && "desc".equals(sortMode)) {
                                retVal = a.compareTo(b);//倒序
                            } else {
                                retVal = b.compareTo(a);//正序
                            }
                        }
                        if(method1.invoke(((T) obj2)) instanceof Integer){
                            Integer a = (Integer)method1.invoke(((T) obj2), null);
                            Integer b = (Integer)method2.invoke(((T) obj1), null);
                            if (sortMode != null && "desc".equals(sortMode)) {
                                retVal = a.compareTo(b);//倒序
                            } else {
                                retVal = b.compareTo(a);//正序
                            }
                        }

                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                    return retVal;
                }
            });
        }
}
