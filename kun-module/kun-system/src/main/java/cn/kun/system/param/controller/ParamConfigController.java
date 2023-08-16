package cn.kun.system.param.controller;

import cn.kun.system.param.entity.dto.ParamConfigAddDTO;
import cn.kun.system.param.entity.dto.ParamConfigEditDTO;
import cn.kun.system.param.entity.dto.ParamConfigPageDTO;
import cn.kun.system.param.entity.po.ParamConfig;
import cn.kun.system.param.entity.vo.ParamConfigDetailVO;
import cn.kun.system.param.entity.vo.ParamConfigPageVO;
import cn.kun.system.param.service.ParamConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 参数配置 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-12 16:16
 */
@Tag(name = "参数配置")
@RestController
@RequestMapping("/param/param-config")
public class ParamConfigController extends BaseController {

    @Resource
    private ParamConfigService paramConfigService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<ParamConfigPageVO>> page(@RequestBody @Valid ParamConfigPageDTO dto) {
        return success(paramConfigService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ParamConfigDetailVO> detail(@PathVariable Long id) {
        return success(paramConfigService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ParamConfig> add(@RequestBody @Valid ParamConfigAddDTO dto) {
        return success(paramConfigService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ParamConfig> edit(@RequestBody @Valid ParamConfigEditDTO dto) {
        return success(paramConfigService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> remove(@PathVariable Long id) {
        return success(paramConfigService.remove(id));
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        return success(paramConfigService.removeBatch(dto));
    }
    
}
