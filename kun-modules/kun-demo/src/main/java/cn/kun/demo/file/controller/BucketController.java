package cn.kun.demo.file.controller;

import cn.kun.demo.file.entity.vo.BucketVO;
import cn.kun.demo.file.service.BucketService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.file.util.MinioHelp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 桶-控制层
 *
 * @author SkySailStar
 */
@Tag(name = "桶")
@RestController
@RequestMapping("bucket")
public class BucketController extends BaseController {

    @Autowired
    private BucketService bucketService;

    @Operation(summary = "是否存在")
    @GetMapping("has/{bucketName}")
    public BaseResult<Boolean> hasBucket(@PathVariable String bucketName) throws Exception {
        
        return success(MinioHelp.hasBucket(bucketName));
    }

    @Operation(summary = "创建")
    @PostMapping("{bucketName}")
    public BaseResult<Boolean> createBucket(@PathVariable String bucketName) throws Exception {

        MinioHelp.createBucket(bucketName);
        return success();
    }

    @Operation(summary = "删除")
    @DeleteMapping("{bucketName}")
    public BaseResult<Boolean> delBucket(@PathVariable String bucketName) throws Exception {

        MinioHelp.delBucket(bucketName);
        return success();
    }

    @Operation(summary = "获取全部")
    @GetMapping("all")
    public BaseResult<List<BucketVO>> getAllBuckets() throws Exception {
        
        return success(bucketService.getAll());
    }
}
