package cn.kun.base.api.service.system;

import cn.kun.base.api.entity.system.dto.FileEditDTO;
import cn.kun.base.api.entity.system.po.File;
import cn.kun.base.api.entity.system.vo.FileInfoVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.api.service.system.factory.RemoteFileFallbackFactory;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import java.util.List;

/**
 * 远程文件服务
 *
 * @author SkySailStar
 * @date 2023-03-29 14:10
 */
@FeignClient(value = ServiceConstants.SYSTEM, fallbackFactory = RemoteFileFallbackFactory.class)
public interface RemoteFileService {

    /**
     * 使文件生效
     * @param id 文件ID
     * @return 结果
     */
    @PutMapping("file/enable/{id}")
    BaseResult<Boolean> enable(@PathVariable("id") Long id);
    
    /**
     * 通过ID获取路径
     * @param id 文件ID
     * @return 文件路径
     */
    @GetMapping("file/path/{id}")
    BaseResult<String> getPathById(@PathVariable("id") Long id);

    /**
     * 修改
     * @param dto 传入值
     * @return 结果
     */
    @PutMapping
    BaseResult<File> edit(@RequestBody FileEditDTO dto);

    /**
     * 通过ID获取信息
     * @param id 文件ID
     * @return 文件信息
     */
    @GetMapping("info/{id}")
    BaseResult<FileInfoVO> getInfoById(@PathVariable("id") Long id);

    /**
     * 通过ID列表获取信息列表
     * @param dto 文件ID列表
     * @return 文件信息列表
     */
    @PostMapping("infos")
    BaseResult<List<FileInfoVO>> getInfoList(@RequestBody @Valid BaseIdListDTO dto);
    
}
