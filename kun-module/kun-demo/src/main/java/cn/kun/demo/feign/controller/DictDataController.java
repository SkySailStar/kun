package cn.kun.demo.feign.controller;

import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典数据-控制层
 *
 * @author 天航星
 * @date 2023-03-23 17:35
 */
@Tag(name = "字典数据")
@RestController
@RequestMapping("dict/data")
public class DictDataController extends BaseController {

    @Resource
    private BaseDictService baseDictService;
    
    @Operation(summary = "根据类型获取列表")
    @GetMapping("list/{type}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<List<BaseSelectVO>> list(@PathVariable String type) {
        return success(baseDictService.list(type));
    }

    @Operation(summary = "根据类型和键值获取标签")
    @GetMapping("label/{type}/{value}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<String> getLabel(@PathVariable String type, @PathVariable String value) {
        return success(baseDictService.getLabel(type, value));
    }
    
}
