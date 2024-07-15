package cn.kun.system.dict.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.dict.entity.dto.DictTypeAddDTO;
import cn.kun.system.dict.entity.dto.DictTypeEditDTO;
import cn.kun.system.dict.entity.dto.DictTypePageDTO;
import cn.kun.system.dict.entity.po.DictType;
import cn.kun.system.dict.entity.vo.DictTypeDetailVO;
import cn.kun.system.dict.entity.vo.DictTypePageVO;
import cn.kun.system.dict.service.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author 天航星
 * @since 2023-03-23 10:24
 */
@Tag(name = "字典类型")
@RestController
@RequestMapping("/dict/dict-type")
public class DictTypeController extends BaseController {

    @Resource
    private DictTypeService dictTypeService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<DictTypePageVO>> page(@RequestBody @Valid DictTypePageDTO dto) {
        return success(dictTypeService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<DictTypeDetailVO> detail(@PathVariable Long id) {
        return success(dictTypeService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<DictType> add(@RequestBody @Valid DictTypeAddDTO dto) {
        return success(dictTypeService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<DictType> edit(@RequestBody @Valid DictTypeEditDTO dto) {
        return success(dictTypeService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        dictTypeService.remove(id);
        return success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        dictTypeService.removeBatch(dto);
        return success();
    }
    
}
