package cn.kun.dispatch.job.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.api.entity.dispatch.dto.TaskAddDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskEditDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskNextTriggerTimeDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskPageDTO;
import cn.kun.base.api.entity.dispatch.dto.TaskTriggerDTO;
import cn.kun.base.api.entity.dispatch.vo.TaskPageVO;
import cn.kun.dispatch.job.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.List;

/**
 * xxl-job 控制层
 * 
 * @author 廖航
 * @date 2023-06-05 09:52
 */
@Tag(name = "定时任务")
@RestController
@RequestMapping("task")
public class TaskController extends BaseController {
    
    @Autowired
    private TaskService taskService;
    
    @Operation(summary = "分页")
    @PostMapping("page")
    public BaseResult<Page<TaskPageVO>> page(@RequestBody TaskPageDTO dto) {
        return success(taskService.page(dto));
    }
    
    @Operation(summary = "添加")
    @PostMapping
    public BaseResult<Integer> add(@RequestBody @Valid TaskAddDTO dto) {
        return success(taskService.add(dto));
    }
    
    @Operation(summary = "修改")
    @PutMapping
    public BaseResult<Boolean> edit(@RequestBody @Valid TaskEditDTO dto) {
        return success(taskService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    public BaseResult<Boolean> remove(@PathVariable Integer id) {
        return success(taskService.remove(id));
    }
    
    @Operation(summary = "启动")
    @PostMapping("start/{id}")
    public BaseResult<Boolean> start(@PathVariable Integer id) {
        return success(taskService.start(id));
    }
    
    @Operation(summary = "停止")
    @PostMapping("stop/{id}")
    public BaseResult<Boolean> stop(@PathVariable Integer id) {
        return success(taskService.stop(id));
    }
    
    @Operation(summary = "触发")
    @PostMapping("trigger")
    public BaseResult<Boolean> trigger(@RequestBody @Valid TaskTriggerDTO dto) {
        return success(taskService.trigger(dto));
    }
    
    @Operation(summary = "后续触发时间")
    @PostMapping("next-trigger-time")
    public BaseResult<List<String>> nextTriggerTime(@RequestBody @Valid TaskNextTriggerTimeDTO dto) {
        return success(taskService.nextTriggerTime(dto));
    }
    
}