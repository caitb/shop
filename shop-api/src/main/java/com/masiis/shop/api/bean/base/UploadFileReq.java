package com.masiis.shop.api.bean.base;

public class UploadFileReq extends BaseBusinessReq {

    private String rootDir;

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
}
