package cn.kun.base.api.service.auth;

/**
 * @author eric
 * @date 2023/3/29 15:43
 */

import cn.kun.base.api.entity.auth.dto.ProjectNoListDTO;
import cn.kun.base.api.entity.auth.dto.UserDTO;
import cn.kun.base.api.entity.auth.vo.ProjectDetailVO;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.system.factory.RemoteDictFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *  远程调用项目接口
 * @author eric
 * @date 2023/3/24 17:21
 */
@FeignClient(value = ServiceConstants.AUTH, fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteProjectService {

    /**
     * 根据项目编号获取项目信息
     * @param projectNo 项目编号
     * @return 项目信息
     */
    @GetMapping(value = "system/sysProject/getProjectInfoByProjectNo/{projectNo}")
    BaseResult<SysProjectInfoVO> getProjectInfoByProjectNo(@PathVariable("projectNo") String projectNo);

    /**
     * 获取用户当前项目信息
     * @param dto 传入值
     * @return 结果
     */
    @PostMapping("system/sysProject/projectInfo")
    BaseResult<List<ProjectParentVO>> projectInfo(@RequestBody UserDTO dto);

    /**
     * 根据项目编号获取项目详情
     * @param projectNo 项目编号
     * @return 项目详情
     */
    @GetMapping("system/sysProject/{projectNo}")
    BaseResult<ProjectDetailVO> projectDetail(@PathVariable("projectNo") String projectNo);

    /**
     * 通过项目编号列表查询项目信息
     * @param dto
     * @return
     */
    @PostMapping("system/sysProject/simple/queryListByProjectNos")
    BaseResult<List<SysProjectInfoVO>> queryListByProjectNos(@RequestBody ProjectNoListDTO dto);
    
}