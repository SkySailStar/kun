package cn.kun.auth.system.user.controller;

import cn.kun.auth.system.user.entity.po.SysUserDetailInner;
import cn.kun.auth.system.user.service.SysUserDetailInnerService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 内部用户详细表 前端控制器
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Tag(name = "内部用户详情")
@RestController
@RequestMapping("/system/sysUserDetailInner")
public class SysUserDetailInnerController extends BaseController {
    @Autowired
    private SysUserDetailInnerService sysUserDetailInnerService;


    /**
     * 保存内部用户详细数据
     * @param sysUserDetailInner
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public BaseResult saveOrUpdate(@RequestBody SysUserDetailInner sysUserDetailInner){
        sysUserDetailInnerService.saveOrUpdate(sysUserDetailInner);
        return success(sysUserDetailInner);
    }

    /**
     * 删除内部用户详细数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public BaseResult delete(@PathVariable("id") String id){
        boolean b = sysUserDetailInnerService.removeById(id);
        if (!b){
            return fail(b,"删除内部用户数据失败");
        }
        return success(b,"删除内部用户数据成功");
    }

    /**
     * 查询内部用户详细数据
     * @param sysUserDetailInner
     * @return
     */
    @RequestMapping(value = "/get")
    public BaseResult get(@RequestBody SysUserDetailInner sysUserDetailInner){
        SysUserDetailInner result = sysUserDetailInnerService.getById(sysUserDetailInner);
        if (result == null){
            return fail(result,"查询数据失败");
        }
        return success(result,"查询数据成功");
    }

}
