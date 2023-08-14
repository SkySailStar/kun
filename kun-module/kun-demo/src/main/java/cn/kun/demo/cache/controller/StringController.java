package cn.kun.demo.cache.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.cache.entity.dto.StringIncDecDTO;
import cn.kun.demo.cache.entity.dto.StringSetDTO;
import cn.kun.demo.cache.entity.dto.StringSetIfAbsentDTO;
import cn.kun.demo.cache.entity.dto.StringSetTimeDTO;
import cn.kun.demo.cache.service.StringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字符串缓存-控制层
 *
 * @author SkySailStar
 * @date 2023-01-07 19:49
 */
@Tag(name = "缓存-字符串")
@RestController
@RequestMapping("cache/string")
public class StringController extends BaseController {

    @Resource
    private StringService stringService;

    @Operation(summary = "取值")
    @GetMapping("{key}")
    public BaseResult<String> get(@PathVariable String key) {
        return success(stringService.get(key));
    }

    @Operation(summary = "设值")
    @PostMapping("set")
    public BaseResult<Void> set(@RequestBody StringSetDTO dto) {
        stringService.set(dto);
        return success();
    }

    @Operation(summary = "设值并设置生效时间")
    @PostMapping("set_time")
    public BaseResult<Void> set(@RequestBody StringSetTimeDTO dto) {
        stringService.set(dto);
        return success();
    }
    
    @Operation(summary = "键不存在才设值")
    @PostMapping("set_if_absent")
    public BaseResult<Void> setIfAbsent(@RequestBody StringSetIfAbsentDTO dto) {
        stringService.setIfAbsent(dto);
        return success();
    }

    @Operation(summary = "递增")
    @PostMapping("inc")
    public BaseResult<Long> inc(@RequestBody StringIncDecDTO dto) {
        return success(stringService.inc(dto));
    }

    @Operation(summary = "递减")
    @PostMapping("dec")
    public BaseResult<Long> dec(@RequestBody StringIncDecDTO dto) {
        return success(stringService.dec(dto));
    }
}
