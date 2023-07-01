package cn.kun.demo.cache.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.cache.entity.dto.ZsetDelDTO;
import cn.kun.demo.cache.entity.dto.ZsetGetDTO;
import cn.kun.demo.cache.entity.dto.ZsetIncDTO;
import cn.kun.demo.cache.entity.dto.ZsetRankDTO;
import cn.kun.demo.cache.entity.dto.ZsetSetDTO;
import cn.kun.demo.cache.entity.dto.ZsetSetSetDTO;
import cn.kun.demo.cache.service.ZsetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 有序集合缓存-控制层
 *
 * @author 廖航
 * @date 2023-03-10 17:32
 */
@Tag(name = "缓存-有序集合")
@RestController
@RequestMapping("cache/zset")
public class ZsetController extends BaseController {
    
    @Autowired
    private ZsetService zsetService;
    
    @Operation(summary = "添加元素")
    @PostMapping("set")
    public BaseResult<Void> set(@RequestBody ZsetSetDTO dto) {
        zsetService.set(dto);
        return success();
    }
    
    @Operation(summary = "添加集合")
    @PostMapping("set_set")
    public BaseResult<Void> setSet(@RequestBody ZsetSetSetDTO dto) {
        zsetService.setSet(dto);
        return success();
    }
    
    @Operation(summary = "获取元素集合")
    @PostMapping("get")
    public BaseResult<Set<Object>> get(@RequestBody ZsetGetDTO dto) {
        return success(zsetService.get(dto));
    }
    
    @Operation(summary = "删除元素")
    @DeleteMapping
    public BaseResult<Void> del(@RequestBody ZsetDelDTO dto) {
        zsetService.del(dto);
        return success();
    }
    
    @Operation(summary = "增加得分")
    @PostMapping("inc")
    public BaseResult<Double> inc(@RequestBody ZsetIncDTO dto) {
        return success(zsetService.inc(dto));
    }
    
    @Operation(summary = "获取排名")
    @GetMapping("rank")
    public BaseResult<Long> rank(@RequestBody ZsetRankDTO dto) {
        return success(zsetService.rank(dto));
    }
    
    @Operation(summary = "获取长度")
    @GetMapping("size/{key}")
    public BaseResult<Long> size(@PathVariable String key) {
        return success(zsetService.size(key));
    }
}