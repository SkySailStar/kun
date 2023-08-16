package cn.kun.system.server.controller;

import cn.kun.system.server.entity.dto.ServerInfoAddDTO;
import cn.kun.system.server.entity.dto.ServerInfoEditDTO;
import cn.kun.system.server.entity.dto.ServerInfoPageDTO;
import cn.kun.system.server.entity.vo.ServerInfoDetailVO;
import cn.kun.system.server.entity.vo.ServerInfoPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.system.server.entity.po.ServerInfo;
import cn.kun.system.server.service.ServerInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * <p>
 * 服务器信息 前端控制器
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:35
 */
@Tag(name = "服务器信息")
@RestController
@RequestMapping("/server/server-info")
public class ServerInfoController extends BaseController {

    @Resource
    private ServerInfoService serverInfoService;

    @Operation(summary = "分页")
    @PostMapping("page")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Page<ServerInfoPageVO>> page(@RequestBody @Valid ServerInfoPageDTO dto) {
        return success(serverInfoService.page(dto));
    }

    @Operation(summary = "详情")
    @GetMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ServerInfoDetailVO> detail(@PathVariable Long id) {
        return success(serverInfoService.detail(id));
    }

    @Operation(summary = "添加")
    @PostMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ServerInfo> add(@RequestBody @Valid ServerInfoAddDTO dto) {
        return success(serverInfoService.add(dto));
    }

    @Operation(summary = "修改")
    @PutMapping
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<ServerInfo> edit(@RequestBody @Valid ServerInfoEditDTO dto) {
        return success(serverInfoService.edit(dto));
    }

    @Operation(summary = "删除")
    @DeleteMapping("{id}")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> remove(@PathVariable Long id) {
        serverInfoService.remove(id);
        return success();
    }

    @Operation(summary = "批量删除")
    @DeleteMapping("batch")
    @PreAuthorize("@custom.hasAuthority('demo')")
    public BaseResult<Void> removeBatch(@RequestBody @Valid BaseIdListDTO dto) {
        serverInfoService.removeBatch(dto);
        return success();
    }
    
}
