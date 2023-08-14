package cn.kun.demo.cache.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.cache.entity.dto.SetDelDTO;
import cn.kun.demo.cache.entity.dto.SetHashDTO;
import cn.kun.demo.cache.entity.dto.SetSetDTO;
import cn.kun.demo.cache.entity.dto.SetSetTimeDTO;
import cn.kun.demo.cache.service.SetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 集合缓存-控制层
 *
 * @author SkySailStar
 * @date 2023-01-08 21:20
 */
@Tag(name = "缓存-集合")
@RestController
@RequestMapping("cache/set")
public class SetController extends BaseController {

    @Resource
    private SetService setService;

    @Operation(summary = "取值")
    @GetMapping("{key}")
    public BaseResult<Set<Object>> get(@PathVariable String key) {
        return success(setService.get(key));
    }

    @Operation(summary = "随机取值")
    @GetMapping("random/{key}")
    public BaseResult<Object> randomGet(@PathVariable String key) {
        return success(setService.randomSet(key));
    }
    
    @Operation(summary = "获取长度")
    @GetMapping("get_size/{key}")
    public BaseResult<Long> getSize(@PathVariable String key) {
        return success(setService.getSize(key));
    }

    @Operation(summary = "设值")
    @PostMapping
    public BaseResult<Void> set(@RequestBody SetSetDTO dto) {
        setService.set(dto);
        return success();
    }

    @Operation(summary = "设值并设值生效时间")
    @PostMapping("set_time")
    public BaseResult<Void> set(@RequestBody SetSetTimeDTO dto) {
        setService.set(dto);
        return success();
    }

    @Operation(summary = "根据值查询")
    @PostMapping("has")
    public BaseResult<Boolean> has(@RequestBody SetHashDTO dto) {
        return success(setService.has(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping
    public BaseResult<Void> del(@RequestBody SetDelDTO dto) {
        setService.del(dto);
        return success();
    }
}
