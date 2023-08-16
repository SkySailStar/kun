package cn.kun.system.monitor.controller;

import cn.kun.system.monitor.service.MonitorConfigService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigAddDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigEditDTO;
import cn.kun.system.monitor.entity.dto.MonitorConfigPageDTO;
import cn.kun.system.monitor.entity.po.MonitorConfig;
import cn.kun.system.monitor.entity.vo.MonitorConfigDetailVO;
import cn.kun.system.monitor.entity.vo.MonitorConfigPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 监控项配置 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-10 10:08
 */
@Tag(name = "监控项配置")
@RestController
@RequestMapping("/monitor/monitor-config")
public class MonitorConfigController extends BaseController {

    @Autowired
    private MonitorConfigService monitorConfigService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<MonitorConfigPageVO>> page(@RequestBody @Valid MonitorConfigPageDTO dto) {
        return success(monitorConfigService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<MonitorConfigDetailVO> detail(@PathVariable Long id) {
        return success(monitorConfigService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<MonitorConfig> add(@RequestBody @Valid MonitorConfigAddDTO dto) {
        return success(monitorConfigService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<MonitorConfig> edit(@RequestBody @Valid MonitorConfigEditDTO dto) {
        return success(monitorConfigService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        monitorConfigService.remove(id);
        return success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        monitorConfigService.removeBatch(dto);
        return success();
    }
    
}
