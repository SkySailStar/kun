package cn.kun.auth.user.controller;

import cn.kun.auth.user.entity.po.SysUserDetailOuter;
import cn.kun.auth.user.service.SysUserDetailOuterService;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import cn.kun.base.core.global.controller.BaseController;

/**
 * <p>
 * 外部用户详情表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "外部用户详情")
@RestController
@RequestMapping("/system/sysUserDetailOuter")
public class SysUserDetailOuterController extends BaseController {
    @Resource
    private SysUserDetailOuterService sysUserDetailOuterService;


    /**
     * 保存外部用户详情数据
     * @param sysUserDetailOuter
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public BaseResult saveOrUpdate(@RequestBody SysUserDetailOuter sysUserDetailOuter){
        sysUserDetailOuterService.saveOrUpdate(sysUserDetailOuter);
        return success(sysUserDetailOuter);
    }

    /**
     * 删除外部用户详情数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public BaseResult delete(@PathVariable("id") String id){
        boolean b = sysUserDetailOuterService.removeById(id);
        if (!b){
            return fail(b,"删除内部用户数据失败");
        }
        return success(b,"删除内部用户数据成功");
    }

    /**
     * 查询外部用户详情数据
     * @param sysUserDetailOuter
     * @return
     */
    @RequestMapping(value = "/get")
    public BaseResult get(@RequestBody SysUserDetailOuter sysUserDetailOuter){
        SysUserDetailOuter result = sysUserDetailOuterService.getById(sysUserDetailOuter);
        if (result == null){
            return fail(result,"查询数据失败");
        }
        return success(result,"查询数据成功");
    }
}
