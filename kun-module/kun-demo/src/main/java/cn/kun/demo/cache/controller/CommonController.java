package cn.kun.demo.cache.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.cache.entity.dto.ExpireDTO;
import cn.kun.demo.cache.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 通用缓存 前端控制类
 * </p>
 *
 * @author SkySailStar
 */
@Tag(name = "缓存-通用")
@RestController
@RequestMapping("cache/common")
public class CommonController extends BaseController {

    @Resource
    private CommonService commonService;
    
    @Operation(summary = "切换数据库")
    @PutMapping("setDatabase/{dbIndex}")
    public BaseResult<Void> setDatabase(@PathVariable int dbIndex) {
        commonService.setDatabase(dbIndex);
        return success();
    }

    @Operation(summary = "设置缓存失效时间")
    @PostMapping("expire")
    public BaseResult<Boolean> expire(@RequestBody ExpireDTO dto) {
        return success(commonService.expire(dto));
    }

    @Operation(summary = "获取失效时间")
    @GetMapping("get_expire/{key}")
    public BaseResult<Long> getExpire(@PathVariable String key) {
        return success(commonService.getExpire(key));
    }

    @Operation(summary = "判断键是否存在")
    @GetMapping("has/{key}")
    public BaseResult<Boolean> has(@PathVariable String key) {
        return success(commonService.has(key));
    }

    @Operation(summary = "删除缓存")
    @DeleteMapping("del/{key}")
    public BaseResult<Void> del(@PathVariable String key) {
        commonService.del(key);
        return success();
    }

}
