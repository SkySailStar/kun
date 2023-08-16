package cn.kun.system.area.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.system.area.entity.dto.AreaAddDTO;
import cn.kun.system.area.entity.dto.AreaEditDTO;
import cn.kun.system.area.entity.dto.AreaPageDTO;
import cn.kun.system.area.entity.po.Area;
import cn.kun.system.area.entity.vo.AreaDetailVO;
import cn.kun.system.area.entity.vo.AreaPageVO;
import cn.kun.system.area.service.AreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>
 * 行政区划 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-06 18:08
 */
@Tag(name = "区域")
@RestController
@RequestMapping("area")
public class AreaController extends BaseController {

    @Resource
    private AreaService areaService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<AreaPageVO>> page(@RequestBody @Valid AreaPageDTO dto) {
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
    public BaseResult<Area> add(@RequestBody @Valid AreaAddDTO dto) {
        return success(areaService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Area> edit(@RequestBody @Valid AreaEditDTO dto) {
        return success(areaService.edit(dto));
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

    @Operation(summary = "列表")
    @GetMapping("list")
    public BaseResult<List<BaseSelectVO>> list() {
        return success(areaService.listAll());
    }
    
    @Operation(summary = "根据ID查询下级列表")
    @GetMapping("list/{id}")
    public BaseResult<List<BaseSelectVO>> list(@PathVariable Long id) {
        return success(areaService.list(id));
    }
    
    @Operation(summary = "通过id获取名称")
    @GetMapping("name/{id}")
    public BaseResult<String> getNameById(@PathVariable Long id) {
        return success(areaService.getNameById(id));
    }
}
