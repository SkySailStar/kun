package cn.kun.dispatch.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.api.entity.dispatch.dto.LogPageDTO;
import cn.kun.base.api.entity.dispatch.vo.LogPageVO;
import cn.kun.dispatch.job.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志-控制层
 *
 * @author 廖航
 */
@Tag(name = "日志")
@RestController
@RequestMapping("log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;
    
    @Operation(summary = "分页")
    @PostMapping("page")
    public BaseResult<Page<LogPageVO>> page(@RequestBody LogPageDTO dto) {
        return success(logService.page(dto));
    }

}