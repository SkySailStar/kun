package cn.kun.base.core.file.util;

import cn.kun.base.core.file.config.MinioConfig;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Minio工具类
 *
 * @author 天航星
 */
@Component
@Slf4j
public class MinioHelp {

    private static MinioConfig minioConfig;

    private static MinioClient minioClient;

    @Resource
    public void setMinioConfig(MinioConfig minioConfig) {
        MinioHelp.minioConfig = minioConfig;
    }

    @Resource
    public void setMinioClient(MinioClient minioClient) {
        MinioHelp.minioClient = minioClient;
    }

    /**
     * 桶是否存在
     * @param name 名称
     * @return 结果
     */
    public static boolean hasBucket(String name) throws Exception {
        
        return minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(name)
                        .build()
        );
    }

    /**
     * 创建桶
     * @param name 名称
     */
    public static void createBucket(String name) throws Exception {
        
        minioClient.makeBucket(
                MakeBucketArgs.builder()
                        .bucket(name)
                        .build()
        );
    }

    /**
     * 删除桶
     * @param name 名称
     */
    public static void delBucket(String name) throws Exception {
        
        minioClient.removeBucket(
                RemoveBucketArgs.builder()
                        .bucket(name)
                        .build()
        );
    }

    /**
     * 获取全部桶
     * @return 全部桶
     */
    public static List<Bucket> getAllBuckets() throws Exception {
        
        return minioClient.listBuckets();
    }

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件路径
     */
    public static String upload(MultipartFile file) throws Exception {
        
        // 文件名称
        String fileName = FileHelp.buildFileNameByFile(file);
        // 上传参数
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        minioClient.putObject(args);
        return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }

    /**
     * 文件预览
     *
     * @param fileName 文件名
     * @return 预览路径
     */
    public static String preview(String fileName) throws Exception {
        
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .method(Method.GET)
                .build();
        return minioClient.getPresignedObjectUrl(build);
    }

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param res 相应
     */
    public static void download(String fileName, HttpServletResponse res) throws Exception {
        
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        }
    }

    /**
     * 查看桶里所有文件
     *
     * @return 桶里所有文件
     */
    public static List<Item> listObjects() throws Exception {
        
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .build()
        );
        List<Item> items = new ArrayList<>();
        for (Result<Item> result : results) {
            items.add(result.get());
        }
        return items;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名称
     */
    public static void delFile(String fileName) throws Exception {
        
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(fileName)
                        .build()
        );
    }
}