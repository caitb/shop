package com.masiis.shop.common.util;

import java.io.*;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

/**
 * @author jipengkun
 *         该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 *         <p/>
 *         该示例代码的执行过程是：
 *         1. 创建一个Bucket（如果已经存在，则忽略错误码）；
 *         2. 上传一个文件到OSS；
 *         3. 下载这个文件到本地；
 *         4. 清理测试资源：删除Bucket及其中的所有Objects。
 *         <p/>
 *         尝试运行这段示例代码时需要注意：
 *         1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 *         示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！
 *         2. 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量；
 *         3. 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
 *         修改常量downloadFilePath为下载文件的路径。
 *         4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
 */
public class OSSObjectUtils {

    private static final String ACCESS_ID = PropertiesUtils.getStringValue("oss.ACCESS_ID");
    private static final String ACCESS_KEY = PropertiesUtils.getStringValue("oss.ACCESS_KEY");
    public static final String ENDPOINT = PropertiesUtils.getStringValue("oss.ENDPOINT");

    public static final String BUCKET = PropertiesUtils.getStringValue("oss.BUCKET");
    /*public static final String OSS_URL = "http://static.masiis.com";
    public static final String CERTIFICATE_TEMPLATE = "/certificateTemplate";*/
    /* OSS下载身份证照片到本地用到的身份证key */
    public static final String OSS_DOWN_LOAD_IMG_KEY = PropertiesUtils.getStringValue("oss.OSS_DOWN_LOAD_IMG_KEY");

    public static final String OSS_CERTIFICATE_TEMP = PropertiesUtils.getStringValue("oss.OSS_CERTIFICATE_TEMP");//上传身份证临时文件目录

    // 删除一个Bucket和其中的Objects
    public static void deleteBucketFile(String fileName)
            throws OSSException, ClientException {

        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        ObjectListing ObjectListing = client.listObjects(BUCKET);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            if (objectName.equals(fileName)) {
                client.deleteObject(BUCKET, objectName);
            }
        }
    }

    // 上传文件
    public static void uploadFile(String key, String fileAbsolutePath, String imageFloder)
            throws OSSException, ClientException, FileNotFoundException {
        uploadFile(new File(fileAbsolutePath), imageFloder);
    }

    // 上传文件
    public static void uploadFile(File file, String imageFloder)
            throws OSSException, ClientException, FileNotFoundException {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
//        objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        client.putObject(BUCKET, imageFloder + file.getName(), input, objectMeta);

        uploadFile(file.getName(), file.length(), new FileInputStream(file), imageFloder);
    }

    // 上传文件
    public static void uploadFile(String fileName, long fileSize, InputStream input, String imageFloder)
            throws OSSException, ClientException, FileNotFoundException {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(fileSize);
        client.putObject(BUCKET, imageFloder + fileName, input, objectMeta);
    }
    //流上传
    public static void uploadFile( String key, InputStream is) {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        client.putObject(BUCKET, key, is);
    }

    // 下载文件
    public static void downloadFile(String key, String localPath)
            throws OSSException, ClientException {
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        client.getObject(new GetObjectRequest(BUCKET, key),
                new File(localPath));
    }

    public static void copyObject(String srcBucketName, String srcKey, String destBucketName, String destKey) {
        // 初始化OSSClient
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        // 拷贝Object
        CopyObjectResult result = client.copyObject(srcBucketName, srcKey, destBucketName, destKey);
        // 打印结果
        System.out.println("ETag: " + result.getETag() + " LastModified: " + result.getLastModified());
    }

    public static void deleteObject(String bucketName, String key) {
        // 初始化OSSClient
        OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
        // 删除Object
        client.deleteObject(bucketName, key);
    }
}
