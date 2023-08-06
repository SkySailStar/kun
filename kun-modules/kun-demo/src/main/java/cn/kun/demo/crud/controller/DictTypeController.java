package cn.kun.demo.crud.controller;

import cn.kun.demo.crud.service.DictTypeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.demo.crud.entity.dto.DictTypeAddDTO;
import cn.kun.demo.crud.entity.dto.DictTypeEditDTO;
import cn.kun.demo.crud.entity.dto.DictTypePageDTO;
import cn.kun.demo.crud.entity.vo.DictTypeDetailVO;
import cn.kun.demo.crud.entity.vo.DictTypePageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * <p>
 * 字典类型表 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-03-23 10:24
 */
@Tag(name = "字典类型")
@RestController
@RequestMapping("/dict/dict-type")
public class DictTypeController extends BaseController {

    @Autowired
    private DictTypeService dictTypeService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<DictTypePageVO>> page(@RequestBody DictTypePageDTO dto) {
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
    public BaseResult<Long> add(@RequestBody @Valid DictTypeAddDTO dto) {
        return success(dictTypeService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> edit(@RequestBody @Valid DictTypeEditDTO dto) {
        return success(dictTypeService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> remove(@PathVariable Long id) {
        return success(dictTypeService.remove(id));
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        return success(dictTypeService.removeBatch(dto));
    }
    
}
