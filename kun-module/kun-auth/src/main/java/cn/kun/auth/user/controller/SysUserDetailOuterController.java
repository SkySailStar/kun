package cn.kun.auth.user.controller;

import com.sevnce.auth.user.entity.po.SysUserDetailOuter;
import com.sevnce.auth.user.service.SysUserDetailOuterService;
import com.sevnce.base.core.global.controller.BaseController;
import com.sevnce.base.core.global.entity.BaseResult;
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
    @Autowired
    private SysUserDetailOuterService sysUserDetailOuterService;


    /**
     * 保存外部用户详情数据
     * @param sysUserDetailOuter
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public BaseResult<SysUserDetailOuter> saveOrUpdate(@RequestBody SysUserDetailOuter sysUserDetailOuter){
        sysUserDetailOuterService.saveOrUpdate(sysUserDetailOuter);
        return success(sysUserDetailOuter);
    }

    /**
     * 删除外部用户详情数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public BaseResult<Boolean> delete(@PathVariable("id") String id){
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
    public BaseResult<SysUserDetailOuter> get(@RequestBody SysUserDetailOuter sysUserDetailOuter){
        SysUserDetailOuter result = sysUserDetailOuterService.getById(sysUserDetailOuter);
        if (result == null){
            return fail(result,"查询数据失败");
        }
        return success(result,"查询数据成功");
    }
}
