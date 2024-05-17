package cn.kun.auth.user.controller;

import com.sevnce.auth.user.entity.dto.UserJobDTO;
import com.sevnce.auth.user.service.SysUserJobInnerService;
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
 * 内部用户职位表 前端控制器
 * </p>
 *
 * @author 胡鹤龄
 * @date 2022-12-15 10:08
 */
@Tag(name = "内部用户职位")
@RestController
@RequestMapping("/system/sysUserJobInner")
public class SysUserJobInnerController extends BaseController {
    @Autowired
    private SysUserJobInnerService sysUserJobInnerService;

    /**
     * 保存数据
     * @param dto
     * @return
     */
    @Operation(summary = "保存")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> save(@RequestBody @Valid UserJobDTO dto) {
        sysUserJobInnerService.save(dto);
        return success();
    }
}
