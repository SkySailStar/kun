package cn.kun.auth.system.role.controller;

import cn.kun.auth.system.role.service.SysRoleTemplateOuterService;
import cn.kun.auth.system.role.entity.po.SysRoleTemplateOuter;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

/**
 * <p>
 * 外部角色模板表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "外部角色模板")
@RestController
@RequestMapping("/system/sysRoleTemplateOuter")
public class SysRoleTemplateOuterController extends BaseController {
    @Resource
    private SysRoleTemplateOuterService sysRoleTemplateOuterService;


    /**
     * 保存外部角色模板数据
     * @param sysRoleTemplateOuter
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public BaseResult saveOrUpdate(@RequestBody SysRoleTemplateOuter sysRoleTemplateOuter){
        sysRoleTemplateOuterService.saveOrUpdate(sysRoleTemplateOuter);
        return success(sysRoleTemplateOuter);
    }

    /**
     * 删除外部角色模板数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public BaseResult delete(@PathVariable("id") String id){
        boolean b = sysRoleTemplateOuterService.removeById(id);
        if (!b){
            return fail(b,"删除内部用户数据失败");
        }
        return success(b,"删除内部用户数据成功");
    }

    /**
     * 查询外部角色模板数据
     * @param sysRoleTemplateOuter
     * @return
     */
    @RequestMapping(value = "/get")
    public BaseResult get(@RequestBody SysRoleTemplateOuter sysRoleTemplateOuter){
        SysRoleTemplateOuter result = sysRoleTemplateOuterService.getById(sysRoleTemplateOuter);
        if (result == null){
            return fail(result,"查询数据失败");
        }
        return success(result,"查询数据成功");
    }
}
