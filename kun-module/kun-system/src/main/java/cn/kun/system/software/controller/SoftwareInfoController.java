package cn.kun.system.software.controller;

import cn.kun.system.software.entity.dto.SoftwareInfoAddDTO;
import cn.kun.system.software.entity.dto.SoftwareInfoEditDTO;
import cn.kun.system.software.entity.dto.SoftwareInfoPageDTO;
import cn.kun.system.software.entity.po.SoftwareInfo;
import cn.kun.system.software.entity.vo.SoftwareInfoDetailVO;
import cn.kun.system.software.entity.vo.SoftwareInfoPageVO;
import cn.kun.system.software.service.SoftwareInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * <p>
 * 软件服务信息 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:37
 */
@Tag(name = "软件服务信息")
@RestController
@RequestMapping("/software/software-info")
public class SoftwareInfoController extends BaseController {

    @Autowired
    private SoftwareInfoService softwareInfoService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<SoftwareInfoPageVO>> page(@RequestBody @Valid SoftwareInfoPageDTO dto) {
        return success(softwareInfoService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<SoftwareInfoDetailVO> detail(@PathVariable Long id) {
        return success(softwareInfoService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<SoftwareInfo> add(@RequestBody @Valid SoftwareInfoAddDTO dto) {
        return success(softwareInfoService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<SoftwareInfo> edit(@RequestBody @Valid SoftwareInfoEditDTO dto) {
        return success(softwareInfoService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        softwareInfoService.remove(id);
        return success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        softwareInfoService.removeBatch(dto);
        return success();
    }
    
}
