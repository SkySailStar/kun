package cn.kun.demo.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kun.demo.file.entity.vo.ItemVO;
import cn.kun.base.core.global.util.date.LocalDateTimeHelp;
import cn.kun.base.core.file.util.MinioHelp;
import cn.kun.demo.file.service.FileService;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件-服务层实现类
 *
 * @author SkySailStar
 * @date 2023-01-13 10:57
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file) throws Exception {
        
        return MinioHelp.upload(file);
    }

    @Override
    public String preview(String name) throws Exception {
        
        return MinioHelp.preview(name);
    }

    @Override
    public void download(String name, HttpServletResponse res) throws Exception {
        
        MinioHelp.download(name, res);
    }

    @Override
    public List<ItemVO> listObjects() throws Exception {
        
        List<Item> items = MinioHelp.listObjects();
        if (CollUtil.isEmpty(items)) {
            return null;
        }
        return items.stream().map(this::cast).toList();
    }

    @Override
    public void delFile(String name) throws Exception {
        
        MinioHelp.delFile(name);
    }

    /**
     * 转换
     * @param item 项
     * @return 桶内项-返回值
     */
    private ItemVO cast(Item item) {
        ItemVO vo = new ItemVO();
        // 名称
        vo.setName(item.objectName());
        // 大小
        vo.setSize(item.size());
        // 所属人
        vo.setOwner(item.owner().displayName());
        // 更新时间
        vo.setUpdateDate(LocalDateTimeHelp.castChina(item.lastModified().toLocalDateTime()));
        // 是否文件夹
        vo.setDir(item.isDir());
        return vo;
    }
}
