package com.masiis.shop.common.util;

import java.io.File;

/**
 * @Date 2016/8/13
 * @Author lzh
 */
public class RootPathUtils {
    public static String getResourcesRootPath(){
        return RootPathUtils.class.getResource("/").getPath();
    }

    public static String getRootPath(){
        String resourcesPath = RootPathUtils.class.getResource("/").getPath();
        File file = new File(resourcesPath);
        String result = file.getParentFile().getParentFile().getPath();
        return result;
    }

    public static void main(String... args) {
        System.out.println(getRootPath());
    }
}
