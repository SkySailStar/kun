package cn.kun.demo.crud.controller;

import cn.kun.demo.crud.service.SysJobMenuInnerService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.demo.crud.entity.dto.JobMenuInnerSaveDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>
 * 内部职位菜单表 前端控制器
 * </p>
 *
 * @author 天航星
 * @since 2023-02-27 17:54
 */
@Tag(name = "内部职位菜单")
@RestController
@RequestMapping("/crud/sysJobMenuInner")
public class SysJobMenuInnerController extends BaseController {

    @Resource
    private SysJobMenuInnerService sysJobMenuInnerService;

    @Operation(summary = "保存")
    @PostMapping
    public BaseResult<Boolean> save(@RequestBody @Valid JobMenuInnerSaveDTO dto) {
        return success(sysJobMenuInnerService.save(dto));
    }
    
}
