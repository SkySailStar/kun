package cn.kun.auth.system.job.controller;

import cn.kun.auth.system.job.entity.dto.JobMenuDTO;
import cn.kun.auth.system.job.service.SysJobMenuOuterService;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.kun.base.core.global.controller.BaseController;

import javax.validation.Valid;

/**
 * <p>
 * 外部职位菜单表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "外部职位菜单")
@RestController
@RequestMapping("/system/sysJobMenuOuter")
public class SysJobMenuOuterController extends BaseController {
    @Autowired
    private SysJobMenuOuterService sysJobMenuOuterService;

    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid JobMenuDTO dto) {
        sysJobMenuOuterService.save(dto);
        return success();
    }


}