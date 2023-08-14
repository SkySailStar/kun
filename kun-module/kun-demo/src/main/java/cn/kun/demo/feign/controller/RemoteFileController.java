package cn.kun.demo.feign.controller;

import cn.kun.base.api.service.system.RemoteFileService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件-控制层
 *
 * @author lujun
 */
@Tag(name = "文件")
@RestController
@RequestMapping("file")
public class RemoteFileController extends BaseController {

    @Resource
    private RemoteFileService remoteFileService;
    
    @Operation(summary = "使文件生效")
    @PutMapping("enable/{id}")
    public BaseResult<Boolean> enable(@PathVariable Long id) {
        return remoteFileService.enable(id);
    }
    
    @Operation(summary = "通过ID获取路径")
    @GetMapping("path/{id}")
    public BaseResult<String> getPathById(@PathVariable Long id) {
        return remoteFileService.getPathById(id);
    }
}
