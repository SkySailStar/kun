package cn.kun.system.log.controller;

import cn.kun.system.log.entity.dto.RunLogPageDTO;
import cn.kun.system.log.entity.vo.RunLogDetailVO;
import cn.kun.system.log.entity.vo.RunLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.system.log.service.RunLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 运行日志 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
@Tag(name = "运行日志")
@RestController
@RequestMapping("/log/run-log")
public class RunLogController extends BaseController {

    @Autowired
    private RunLogService runLogService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<RunLogPageVO>> page(@RequestBody @Valid RunLogPageDTO dto) {
        return success(runLogService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<RunLogDetailVO> detail(@PathVariable Long id) {
        return success(runLogService.detail(id));
    }
    
}
