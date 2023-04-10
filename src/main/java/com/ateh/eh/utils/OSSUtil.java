package com.ateh.eh.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.ateh.eh.common.OSSConstants;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * <p>
 * 类说明：OSS对象存储工具类
 * <p>
 * 类名称: OSSUtil.java
 *
 * @author huang.yijie
 * 时间: 2023/3/20 23:20
 * <p>
 * Modification History:
 * Date Author Version Description
 * ------------------------------------------------------------
 * @version v1.0.0
 */
public class OSSUtil {

    private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI5tR3JndFBNAGCYJ2jbF5";
    private static final String accessKeySecret = "YGI2Aikw8udMzb0zqbz9mi61ZDUDmn";

    /**
     * Bucket名称
     */
    private static final String bucketName = "yeasy-helpj";

    private static OSS ossClient = null;

    public static void main(String[] args) {
        // 填写字符串
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        System.out.println(fileName);
    }

    /**
     * 上传文件到OSS上
     *
     * @param prefix 文件目录位置
     * @param inputStream 输入流
     * @param suffix 文件扩展名
     * @return 文件在OSS上的路径
     */
    public static String uploadFile(String prefix, ByteArrayInputStream inputStream, String suffix) {

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ObjectMetadata metadata = new ObjectMetadata();
            String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            // 对象路径与名称
            String objectName = prefix + fileName + suffix;
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
            // 设置文件权限
            metadata.setObjectAcl(CannedAccessControlList.PublicRead);
            metadata.setContentType(getContentType(suffix));

            putObjectRequest.setMetadata(metadata);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 上传字符串。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            ResponseMessage response = result.getResponse();
            if (Objects.equals(OSSConstants.VALID_RESPONSE, response.getStatusCode())) {
                // 获取文件路径
                return response.getUri();
            }
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }



    //通过该方法快速获取文件类型
    public static String getContentType(String suffix) {
        suffix = suffix.toLowerCase(Locale.ROOT);
        if (".jpg".equals(suffix) || ".jpeg".equals(suffix) || ".png".equals(suffix)) {
            return "image/jpg";
        }
        return "image/jpg";
    }
}
