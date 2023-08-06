package cn.kun.system.external.controller;

import cn.kun.system.external.entity.dto.ExternalUrlAddDTO;
import cn.kun.system.external.entity.dto.ExternalUrlEditDTO;
import cn.kun.system.external.entity.dto.ExternalUrlPageDTO;
import cn.kun.system.external.entity.vo.ExternalUrlDetailVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.external.entity.po.ExternalUrl;
import cn.kun.system.external.entity.vo.ExternalUrlPageVO;
import cn.kun.system.external.service.ExternalUrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * <p>
 * 外部链接 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
@Tag(name = "外部链接")
@RestController
@RequestMapping("/external/external-url")
public class ExternalUrlController extends BaseController {

    @Autowired
    private ExternalUrlService externalUrlService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<ExternalUrlPageVO>> page(@RequestBody @Valid ExternalUrlPageDTO dto) {
        return success(externalUrlService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ExternalUrlDetailVO> detail(@PathVariable Long id) {
        return success(externalUrlService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ExternalUrl> add(@RequestBody @Valid ExternalUrlAddDTO dto) {
        return success(externalUrlService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ExternalUrl> edit(@RequestBody @Valid ExternalUrlEditDTO dto) {
        return success(externalUrlService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> remove(@PathVariable Long id) {
        return success(externalUrlService.remove(id));
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Boolean> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        return success(externalUrlService.removeBatch(dto));
    }
    
}
