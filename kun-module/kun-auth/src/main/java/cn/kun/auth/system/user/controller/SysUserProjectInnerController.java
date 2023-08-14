package cn.kun.auth.system.user.controller;

import cn.kun.auth.system.user.entity.dto.UserProjectDTO;
import cn.kun.auth.system.user.service.SysUserProjectInnerService;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

import jakarta.validation.Valid;

/**
 * <p>
 * 内部用户项目表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部用户项目")
@RestController
@RequestMapping("/system/sysUserProjectInner")
public class SysUserProjectInnerController extends BaseController {
    @Resource
    private SysUserProjectInnerService sysUserProjectInnerService;


    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid UserProjectDTO dto) {
        sysUserProjectInnerService.save(dto);
        return success();
    }


    @PostMapping("getProjectIdsByUserId")
    public BaseResult getProjectIdsByUserId(@RequestBody UserProjectDTO dto){
        return success(sysUserProjectInnerService.getProjectNosByUserId(dto));
    }
}
