package cn.kun.base.api.service.auth.factory;

import cn.kun.base.api.entity.auth.dto.ProjectNoListDTO;
import cn.kun.base.api.entity.auth.dto.UserDTO;
import cn.kun.base.api.entity.auth.vo.ProjectDetailVO;
import cn.kun.base.api.entity.auth.vo.ProjectParentVO;
import cn.kun.base.api.entity.auth.vo.SysProjectInfoVO;
import cn.kun.base.api.service.auth.RemoteProjectService;
import cn.kun.base.core.global.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 远程认证服务-降级处理
 *
 * @author eric
 * @date 2023/5/17 16:26
 */
@Slf4j
@Component
public class RemoteProjectFallbackFactory implements FallbackFactory<RemoteProjectService> {

    @Override
    public RemoteProjectService create(Throwable cause) {

        log.error("远程认证服务：{}", cause.getMessage());
        return new RemoteProjectService() {

            @Override
            public BaseResult<SysProjectInfoVO> getProjectInfoByProjectNo(String projectNo) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<ProjectParentVO>> projectInfo(UserDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<ProjectDetailVO> projectDetail(String projectNo) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<SysProjectInfoVO>> queryListByProjectNos(ProjectNoListDTO dto) {
                return BaseResult.fail();
            }

        };
    }
}