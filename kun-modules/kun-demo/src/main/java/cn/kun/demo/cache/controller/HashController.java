package cn.kun.demo.cache.controller;

import cn.kun.demo.cache.entity.dto.HashDelDTO;
import cn.kun.demo.cache.entity.dto.HashGetByKeyValueDTO;
import cn.kun.demo.cache.entity.dto.HashHasDTO;
import cn.kun.demo.cache.entity.dto.HashIncDecDTO;
import cn.kun.demo.cache.entity.dto.HashSetDTO;
import cn.kun.demo.cache.entity.dto.HashSetMapDTO;
import cn.kun.demo.cache.entity.dto.HashSetMapTimeDTO;
import cn.kun.demo.cache.entity.dto.HashSetTimeDTO;
import cn.kun.demo.cache.entity.dto.HashSetValueDTO;
import cn.kun.demo.cache.entity.dto.HashSetValueTimeDTO;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.cache.service.HashService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 哈希缓存-控制层
 *
 * @author 廖航
 * @date 2023-01-08 21:44
 */
@Tag(name = "缓存-哈希")
@RestController
@RequestMapping("cache/hash")
public class HashController extends BaseController {

    @Autowired
    private HashService hashService;

    @Operation(summary = "获取键对应的所有值")
    @GetMapping("{key}")
    public BaseResult<Map<Object, Object>> get(@PathVariable String key) {
        return success(hashService.get(key));
    }

    @Operation(summary = "根据键和项取值")
    @PostMapping("get")
    public BaseResult<Object> getByKeyValue(@RequestBody HashGetByKeyValueDTO dto) {
        return success(hashService.getByKeyValue(dto));
    }

    @Operation(summary = "设值")
    @PostMapping
    public BaseResult<Void> set(@RequestBody HashSetDTO dto) {
        hashService.set(dto);
        return success();
    }

    @Operation(summary = "设值并设值生效时间")
    @PostMapping("set_time")
    public BaseResult<Void> set(@RequestBody HashSetTimeDTO dto) {
        hashService.set(dto);
        return success();
    }
    
    @Operation(summary = "设多个值")
    @PostMapping("set_map")
    public BaseResult<Void> setMap(@RequestBody HashSetMapDTO dto) {
        hashService.set(dto);
        return success();
    }

    @Operation(summary = "设多个值并设值生效时间")
    @PostMapping("set_map_time")
    public BaseResult<Void> setMap(@RequestBody HashSetMapTimeDTO dto) {
        hashService.set(dto);
        return success();
    }

    @Operation(summary = "设值若不存在则创建")
    @PostMapping("set_value")
    public BaseResult<Void> set(@RequestBody HashSetValueDTO dto) {
        hashService.set(dto);
        return success();
    }

    @Operation(summary = "设值若不存在则创建并设置生效时间")
    @PostMapping("set_value_time")
    public BaseResult<Void> set(@RequestBody HashSetValueTimeDTO dto) {
        hashService.set(dto);
        return success();
    }

    @Operation(summary = "判断键和项是否存在")
    @PostMapping("has")
    public BaseResult<Boolean> has(@RequestBody HashHasDTO dto) {
        return success(hashService.has(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping
    public BaseResult<Void> del(@RequestBody HashDelDTO dto) {
        hashService.del(dto);
        return success();
    }

    @Operation(summary = "递增")
    @PostMapping("inc")
    public BaseResult<Double> inc(@RequestBody HashIncDecDTO dto) {
        return success(hashService.inc(dto));
    }

    @Operation(summary = "递减")
    @PostMapping("dec")
    public BaseResult<Double> dec(@RequestBody HashIncDecDTO dto) {
        return success(hashService.dec(dto));
    }
}
