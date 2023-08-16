package cn.kun.auth.system.project.controller;

import cn.kun.auth.system.project.entity.po.SysProjectDetail;
import cn.kun.auth.system.project.service.SysProjectDetailService;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

/**
 * <p>
 * 项目详情表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "项目详情")
@RestController
@RequestMapping("/system/sysProjectDetail")
public class SysProjectDetailController extends BaseController {
    @Resource
    private SysProjectDetailService sysProjectDetailService;

    /**
     * 保存项目详情数据
     * @param sysProjectDetail
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public BaseResult saveOrUpdate(@RequestBody SysProjectDetail sysProjectDetail){
        sysProjectDetailService.saveOrUpdate(sysProjectDetail);
        return success(sysProjectDetail);
    }

    /**
     * 删除项目详情数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public BaseResult delete(@PathVariable("id") String id){
        boolean b = sysProjectDetailService.removeById(id);
        if (!b){
            return fail(b,"删除内部用户数据失败");
        }
        return success(b,"删除内部用户数据成功");
    }

    /**
     * 查询项目详情数据
     * @param sysProjectDetail
     * @return
     */
    @RequestMapping(value = "/get")
    public BaseResult get(@RequestBody SysProjectDetail sysProjectDetail){
        SysProjectDetail result = sysProjectDetailService.getById(sysProjectDetail);
        if (result == null){
            return fail(result,"查询数据失败");
        }
        return success(result,"查询数据成功");
    }

}
