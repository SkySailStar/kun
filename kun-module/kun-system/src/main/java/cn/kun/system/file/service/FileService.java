package cn.kun.system.file.service;

import cn.kun.system.file.entity.vo.FileVO;
import cn.kun.system.file.entity.vo.UploadVO;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.api.entity.system.dto.FileEditDTO;
import cn.kun.base.api.entity.system.po.File;
import cn.kun.base.api.entity.system.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件-服务层
 *
 * @author SkySailStar
 * @date 2023-01-13 10:53
 */
public interface FileService extends IService<File> {

    /**
     * 上传
     * @param file 文件
     * @return 返回值
     */
    UploadVO upload(MultipartFile file) throws Exception;

    /**
     * 修改
     * @param dto 传入值
     * @return 返回值
     */
    File edit(FileEditDTO dto);

    /**
     * 通过文件ID预览
     * @param id 文件ID
     * @return 预览路径
     */
    String previewById(Long id) throws Exception;
    
    /**
     * 通过文件名称预览
     * @param name 文件名称
     * @return 预览路径
     */
    String previewByName(String name) throws Exception;

    /**
     * 通过文件ID下载
     * @param id 文件ID
     * @param res 响应
     */
    void downloadById(Long id, HttpServletResponse res) throws Exception;
    
    /**
     * 通过文件名称下载
     * @param name 文件名称
     * @param res 响应
     */
    void downloadByName(String name, HttpServletResponse res) throws Exception;

    /**
     * 查看桶里所有项
     *
     * @return 桶里所有项
     */
    List<FileVO> listObjects() throws Exception;

    /**
     * 通过文件ID删除
     * @param id 文件ID
     * @return 结果
     */
    boolean delFileById(Long id) throws Exception;
    
    /**
     * 通过文件名称删除
     * @param name 文件名称
     * @return 结果
     */
    boolean delFileByName(String name) throws Exception;

    /**
     * 使文件生效
     * @param id 文件ID
     * @return 结果
     */
    boolean enable(Long id);

    /**
     * 通过ID获取路径
     * @param id 文件ID
     * @return 文件路径
     */
    String getPathById(Long id);

    /**
     * 通过ID获取信息
     * @param id 文件ID
     * @return 文件信息
     */
    FileInfoVO getInfoById(Long id);

    /**
     * 通过ID列表获取信息列表
     * @param dto 传入值
     * @return 文件信息列表
     */
    List<FileInfoVO> getInfoList(BaseIdListDTO dto);
}
