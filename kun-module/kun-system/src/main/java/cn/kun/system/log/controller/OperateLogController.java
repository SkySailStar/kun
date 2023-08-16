package cn.kun.system.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.system.log.entity.dto.OperateLogPageDTO;
import cn.kun.system.log.entity.vo.OperateLogDetailVO;
import cn.kun.system.log.entity.vo.OperateLogPageVO;
import cn.kun.system.log.service.OperateLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 操作日志 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
@Tag(name = "操作日志")
@RestController
@RequestMapping("/log/operate-log")
public class OperateLogController extends BaseController {

    @Autowired
    private OperateLogService operateLogService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<OperateLogPageVO>> page(@RequestBody @Valid OperateLogPageDTO dto) {
        return success(operateLogService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<OperateLogDetailVO> detail(@PathVariable Long id) {
        return success(operateLogService.detail(id));
    }
    
}
