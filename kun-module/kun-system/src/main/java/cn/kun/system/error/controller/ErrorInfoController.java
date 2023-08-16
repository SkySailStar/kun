package cn.kun.system.error.controller;

import cn.kun.system.error.service.ErrorInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.mq.entity.dto.ErrorInfoMqDTO;
import cn.kun.system.error.entity.dto.ErrorInfoPageDTO;
import cn.kun.system.error.entity.vo.ErrorInfoDetailVO;
import cn.kun.system.error.entity.vo.ErrorInfoPageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

/**
 * <p>
 * 错误信息 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:22
 */
@Tag(name = "错误信息")
@RestController
@RequestMapping("/error/error-info")
public class ErrorInfoController extends BaseController {

    @Resource
    private ErrorInfoService errorInfoService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<ErrorInfoPageVO>> page(@RequestBody @Valid ErrorInfoPageDTO dto) {
        return success(errorInfoService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ErrorInfoDetailVO> detail(@PathVariable Long id) {
        return success(errorInfoService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    public BaseResult<Void> add(@RequestBody @Valid ErrorInfoMqDTO dto) {
        errorInfoService.add(dto);
        return success();
    }
}
