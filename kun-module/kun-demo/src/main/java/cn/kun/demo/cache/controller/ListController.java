package cn.kun.demo.cache.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.cache.entity.dto.ListDelDTO;
import cn.kun.demo.cache.entity.dto.ListGetByIndexDTO;
import cn.kun.demo.cache.entity.dto.ListGetDTO;
import cn.kun.demo.cache.entity.dto.ListSetDTO;
import cn.kun.demo.cache.entity.dto.ListSetIndexDTO;
import cn.kun.demo.cache.entity.dto.ListSetListDTO;
import cn.kun.demo.cache.entity.dto.ListSetListTimeDTO;
import cn.kun.demo.cache.entity.dto.ListSetTimeDTO;
import cn.kun.demo.cache.service.ListService;
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

import java.util.List;

/**
 * 列表缓存-控制层
 *
 * @author SkySailStar
 * @date 2023-01-07 20:44
 */
@Tag(name = "缓存-列表")
@RestController
@RequestMapping("cache/list")
public class ListController extends BaseController {

    @Resource
    private ListService listService;

    @Operation(summary = "取值")
    @PostMapping("get/{key}")
    public BaseResult<List<Object>> get(@PathVariable String key) {
        return success(listService.get(key));
    }
    
    @Operation(summary = "取值")
    @PostMapping("get")
    public BaseResult<List<Object>> get(@RequestBody ListGetDTO dto) {
        return success(listService.get(dto));
    }

    @Operation(summary = "获取长度")
    @GetMapping("get_size/{key}")
    public BaseResult<Long> getSize(@PathVariable String key) {
        return success(listService.getSize(key));
    }

    @Operation(summary = "通过索引取值")
    @PostMapping("get_by_index")
    public BaseResult<Object> getByIndex(@RequestBody ListGetByIndexDTO dto) {
        return success(listService.getByIndex(dto));
    }

    @Operation(summary = "设值")
    @PostMapping("set")
    public BaseResult<Void> set(@RequestBody ListSetDTO dto) {
        listService.set(dto);
        return success();
    }

    @Operation(summary = "设值并设置生效时间")
    @PostMapping("set_time")
    public BaseResult<Void> set(@RequestBody ListSetTimeDTO dto) {
        listService.set(dto);
        return success();
    }

    @Operation(summary = "列表设值")
    @PostMapping("set_list")
    public BaseResult<Void> set(@RequestBody ListSetListDTO dto) {
        listService.set(dto);
        return success();
    }

    @Operation(summary = "列表设值并设置生效时间")
    @PostMapping("set_list_time")
    public BaseResult<Void> set(@RequestBody ListSetListTimeDTO dto) {
        listService.set(dto);
        return success();
    }

    @Operation(summary = "通过索引设值")
    @PostMapping("set_index")
    public BaseResult<Void> set(@RequestBody ListSetIndexDTO dto) {
        listService.set(dto);
        return success();
    }

    @Operation(summary = "删除")
    @DeleteMapping
    public BaseResult<Long> del(@RequestBody ListDelDTO dto) {
        listService.del(dto);
        return success();
    }
    
    @Operation(summary = "弹出")
    @DeleteMapping("{key}")
    public BaseResult<Void> pop(@PathVariable String key) {
        listService.pop(key);
        return success();
    }
}