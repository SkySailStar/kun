package cn.kun.demo.feign.controller;

import cn.kun.base.api.service.system.RemoteAreaService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 行政区划 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-06 18:08
 */
@Tag(name = "远程区域")
@RestController
@RequestMapping("area")
public class SystemAreaController extends BaseController {

    @Autowired
    private RemoteAreaService remoteAreaService;
    
    @Operation(summary = "通过id获取名称")
    @GetMapping("name/{id}")
    public BaseResult<String> getNameById(@PathVariable Long id) {
        return success(remoteAreaService.getNameById(id).getData());
    }
    
}
