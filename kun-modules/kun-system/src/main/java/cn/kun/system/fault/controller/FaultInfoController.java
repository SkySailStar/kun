package cn.kun.system.fault.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.system.fault.entity.dto.FaultInfoPageDTO;
import cn.kun.system.fault.entity.vo.FaultInfoDetailVO;
import cn.kun.system.fault.entity.vo.FaultInfoPageVO;
import cn.kun.system.fault.service.FaultInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * <p>
 * 故障信息 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:24
 */
@Tag(name = "故障信息")
@RestController
@RequestMapping("/fault/fault-info")
public class FaultInfoController extends BaseController {

    @Autowired
    private FaultInfoService faultInfoService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<FaultInfoPageVO>> page(@RequestBody @Valid FaultInfoPageDTO dto) {
        return success(faultInfoService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<FaultInfoDetailVO> detail(@PathVariable Long id) {
        return success(faultInfoService.detail(id));
    }
    
}
