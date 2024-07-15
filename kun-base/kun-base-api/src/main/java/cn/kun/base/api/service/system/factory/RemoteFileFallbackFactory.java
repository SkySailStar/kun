package cn.kun.base.api.service.system.factory;

import cn.kun.base.api.entity.system.dto.FileEditDTO;
import cn.kun.base.api.entity.system.po.File;
import cn.kun.base.api.entity.system.vo.FileInfoVO;
import cn.kun.base.api.service.system.RemoteFileService;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 远程文件服务-降级处理
 *
 * @author 天航星
 * @date 2023-04-27 16:01
 */
@Slf4j
@Component
public class RemoteFileFallbackFactory implements FallbackFactory<RemoteFileService> {
    
    @Override
    public RemoteFileService create(Throwable cause) {
        
        log.error("远程文件服务：{}", cause.getMessage());
        return new RemoteFileService() {
            
            @Override
            public BaseResult<Boolean> enable(Long id) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<String> getPathById(Long id) {
                
                return BaseResult.fail();
            }

            @Override
            public BaseResult<File> edit(FileEditDTO dto) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<FileInfoVO> getInfoById(Long id) {
                return BaseResult.fail();
            }

            @Override
            public BaseResult<List<FileInfoVO>> getInfoList(BaseIdListDTO dto) {
                return BaseResult.fail();
            }
        };
    }
}
