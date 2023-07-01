package cn.kun.demo.crud.controller;

import cn.kun.demo.crud.service.AreaService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.demo.crud.entity.dto.AreaAddDTO;
import cn.kun.demo.crud.entity.dto.AreaEditDTO;
import cn.kun.demo.crud.entity.dto.AreaPageDTO;
import cn.kun.demo.crud.entity.vo.AreaDetailVO;
import cn.kun.demo.crud.entity.vo.AreaPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 行政区划 前端控制器
 * </p>
 *
 * @author 廖航
 * @since 2023-04-06 18:08
 */
@Tag(name = "区域")
@Validated
@RestController
@RequestMapping("area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<AreaPageVO>> page(@RequestBody AreaPageDTO dto) {
        return success(areaService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<AreaDetailVO> detail(@PathVariable Long id) {
        return success(areaService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Long> add(@RequestBody @Valid AreaAddDTO dto) {
        return success(areaService.add(dto));
    }

    @Operation(summary = "批量添加")
    @PostMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> addBatch(@RequestBody @Valid List<AreaAddDTO> dtoList) {
        return success(areaService.addBatch(dtoList));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> edit(@RequestBody @Valid AreaEditDTO dto) {
        return success(areaService.edit(dto));
    }

    @Operation(summary = "批量修改")
    @PutMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> editBatch(@RequestBody @Valid List<AreaEditDTO> dtoList) {
        return success(areaService.editBatch(dtoList));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> remove(@PathVariable Long id) {
        return success(areaService.remove(id));
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        return success(areaService.removeBatch(dto));
    }
    
}
