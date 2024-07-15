package cn.kun.system.dict.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.system.dict.entity.dto.DictDataAddDTO;
import cn.kun.system.dict.entity.dto.DictDataEditDTO;
import cn.kun.base.api.entity.system.dto.DictDataListDTO;
import cn.kun.system.dict.entity.dto.DictDataPageDTO;
import cn.kun.system.dict.entity.po.DictData;
import cn.kun.system.dict.entity.vo.DictDataDetailVO;
import cn.kun.system.dict.entity.vo.DictDataPageVO;
import cn.kun.base.api.entity.system.vo.DictDataListVO;
import cn.kun.system.dict.service.DictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author 天航星
 * @since 2023-03-23 10:32
 */
@Tag(name = "字典数据")
@RestController
@RequestMapping("/dict/dict-data")
public class DictDataController extends BaseController {

    @Resource
    private DictDataService dictDataService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<DictDataPageVO>> page(@RequestBody @Valid DictDataPageDTO dto) {
        return success(dictDataService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<DictDataDetailVO> detail(@PathVariable Long id) {
        return success(dictDataService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<DictData> add(@RequestBody @Valid DictDataAddDTO dto) {
        return success(dictDataService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<DictData> edit(@RequestBody @Valid DictDataEditDTO dto) {
        return success(dictDataService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        dictDataService.remove(id);
        return success();
    }

    @Operation(summary = "根据类型获取列表")
    @GetMapping("list/{type}")
    public BaseResult<List<BaseSelectVO>> list(@PathVariable String type) {
        return success(dictDataService.list(type));
    }

    @Operation(summary = "根据类型和键值获取标签")
    @GetMapping("label/{type}/{value}")
    public BaseResult<String> getLabel(@PathVariable String type, @PathVariable String value) {
        return success(dictDataService.getLabel(type, value));
    }

    @Operation(summary = "根据多个类型获取多个列表")
    @PostMapping("list")
    public BaseResult<List<DictDataListVO>> list(@RequestBody DictDataListDTO dto) {
        return success(dictDataService.list(dto));
    }
    
}
