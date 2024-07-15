package cn.kun.dispatch.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;
import cn.kun.dispatch.job.service.ExecutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 执行器-控制层
 *
 * @author 廖航
 * @date 2023-06-06 10:50
 */
@Tag(name = "执行器")
@RestController
@RequestMapping("executor")
public class ExecutorController extends BaseController {
    
    @Resource
    private ExecutorService executorService;
    
    @Operation(summary = "分页")
    @PostMapping("page")
    public BaseResult<Page<ExecutorPageVO>> page(@RequestBody ExecutorPageDTO dto) {
        return success(executorService.page(dto));
    }
    
    @Operation(summary = "详情")
    @GetMapping("{id}")
    public BaseResult<ExecutorDetailVO> detail(@PathVariable Integer id) {
        return success(executorService.detail(id));
    }
    
}
