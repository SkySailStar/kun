package cn.kun.demo.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kun.demo.file.entity.vo.BucketVO;
import cn.kun.base.core.global.util.date.LocalDateTimeUtils;
import cn.kun.base.core.file.util.MinioUtils;
import cn.kun.demo.file.service.BucketService;
import io.minio.messages.Bucket;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 桶-服务层实现类
 *
 * @author 天航星
 * @date 2023-01-13 10:04
 */
@Service
public class BucketServiceImpl implements BucketService {

    @Override
    public boolean has(String name) throws Exception {
        
        return MinioUtils.hasBucket(name);
    }

    @Override
    public void create(String name) throws Exception {
        
        MinioUtils.createBucket(name);
    }

    @Override
    public void del(String name) throws Exception {
        
        MinioUtils.delBucket(name);
    }

    @Override
    public List<BucketVO> getAll() throws Exception {
        
        List<Bucket> allBuckets = MinioUtils.getAllBuckets();
        if (CollUtil.isEmpty(allBuckets)) {
            return null;
        }
        return allBuckets.stream().map(this::cast).toList();
    }

    /**
     * 转换
     * @param bucket 桶
     * @return 桶返回值
     */
    private BucketVO cast(Bucket bucket) {
        
        BucketVO vo = new BucketVO();
        // 名称
        vo.setName(bucket.name());
        // 创建时间
        vo.setCreateDate(LocalDateTimeUtils.castChina(bucket.creationDate().toLocalDateTime()));
        return vo;
    }
}
