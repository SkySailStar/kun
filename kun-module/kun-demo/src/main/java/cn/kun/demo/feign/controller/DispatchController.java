package cn.kun.demo.feign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.vo.LogPageVO;
import cn.kun.base.api.service.dispatch.RemoteLogService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调度服务
 *
 * @author SkySailStar
 * @date 2023-06-07 10:49
 */
@Tag(name = "调度服务")
@RestController
@RequestMapping("dispatch")
public class DispatchController extends BaseController {
    
    @Resource
    private RemoteLogService remoteLogService;
    
    @Operation(summary = "日志-分页列表")
    @PostMapping("log/page")
    public BaseResult<Page<LogPageVO>> page(@RequestBody LogPageDTO dto) {
        return success(remoteLogService.page(dto).getData());
    }
    
}
