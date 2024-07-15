package cn.kun.demo.file.service;

import cn.kun.demo.file.entity.vo.ItemVO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件-服务层
 *
 * @author 天航星
 * @date 2023-01-13 10:53
 */
public interface FileService {

    /**
     * 上传
     * @param file 文件
     * @return 文件路径
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 预览
     * @param name 名称
     * @return 预览路径
     */
    String preview(String name) throws Exception;

    /**
     * 下载
     * @param name 名称
     * @param res 响应
     */
    void download(String name, HttpServletResponse res) throws Exception;

    /**
     * 查看桶里所有项
     *
     * @return 桶里所有项
     */
    List<ItemVO> listObjects() throws Exception;

    /**
     * 删除
     * @param name 名称
     */
    void delFile(String name) throws Exception;

}
