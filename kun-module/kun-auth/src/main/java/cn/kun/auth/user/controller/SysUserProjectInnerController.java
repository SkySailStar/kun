package cn.kun.auth.user.controller;

import com.sevnce.auth.user.entity.dto.UserProjectDTO;
import com.sevnce.auth.user.service.SysUserProjectInnerService;
import com.sevnce.base.core.global.controller.BaseController;
import com.sevnce.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

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
    @Autowired
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
    public BaseResult<String> getProjectIdsByUserId(@RequestBody UserProjectDTO dto){
        return success(sysUserProjectInnerService.getProjectNosByUserId(dto));
    }
}
