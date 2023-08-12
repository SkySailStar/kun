package cn.kun.system.monitor.controller;

import cn.kun.system.monitor.service.MonitorDataService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.system.monitor.entity.dto.MonitorDataPageDTO;
import cn.kun.system.monitor.entity.vo.MonitorDataDetailVO;
import cn.kun.system.monitor.entity.vo.MonitorDataPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 监控数据 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:23
 */
@Tag(name = "监控数据")
@RestController
@RequestMapping("/monitor/monitor-data")
public class MonitorDataController extends BaseController {

    @Autowired
    private MonitorDataService monitorDataService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<MonitorDataPageVO>> page(@RequestBody @Valid MonitorDataPageDTO dto) {
        return success(monitorDataService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<MonitorDataDetailVO> detail(@PathVariable Long id) {
        return success(monitorDataService.detail(id));
    }
    
}
