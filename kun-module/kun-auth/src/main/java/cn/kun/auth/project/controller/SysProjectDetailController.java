package cn.kun.auth.project.controller;

import com.sevnce.auth.project.entity.po.SysProjectDetail;
import com.sevnce.auth.project.service.SysProjectDetailService;
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
    @Autowired
    private SysProjectDetailService sysProjectDetailService;

    /**
     * 保存项目详情数据
     * @param sysProjectDetail
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public BaseResult<SysProjectDetail> saveOrUpdate(@RequestBody SysProjectDetail sysProjectDetail){
        sysProjectDetailService.saveOrUpdate(sysProjectDetail);
        return success(sysProjectDetail);
    }

    /**
     * 删除项目详情数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public BaseResult<Boolean> delete(@PathVariable("id") String id){
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
    public BaseResult<SysProjectDetail> get(@RequestBody SysProjectDetail sysProjectDetail){
        SysProjectDetail result = sysProjectDetailService.getById(sysProjectDetail);
        if (result == null){
            return fail(result,"查询数据失败");
        }
        return success(result,"查询数据成功");
    }

}
