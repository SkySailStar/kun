package cn.kun.system.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.file.entity.vo.FileVO;
import cn.kun.system.file.entity.vo.UploadVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.file.util.FileHelp;
import cn.kun.base.core.file.util.MinioUtils;
import cn.kun.base.core.global.constant.BaseConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.data.FileTypeConstants;
import cn.kun.base.core.global.constant.dict.data.StorageLocationConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.constant.file.FileSizeConstants;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanUtils;
import cn.kun.base.core.global.util.date.LocalDateTimeUtils;
import cn.kun.base.api.entity.system.dto.FileEditDTO;
import cn.kun.base.api.entity.system.po.File;
import cn.kun.base.api.entity.system.vo.FileInfoVO;
import cn.kun.system.file.mapper.FileMapper;
import cn.kun.system.file.service.FileService;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件-服务层实现类
 *
 * @author 天航星
 * @date 2023-01-13 10:57
 */
@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Resource
    private BaseDictService baseDictService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadVO upload(MultipartFile uploadFile) throws Exception {
        
        // 文件类型
        String fileType = FileHelp.buildTypeByName(uploadFile.getOriginalFilename());
        // 文件大小
        long fileSize = uploadFile.getSize();
        // 文件大小校验
        checkSize(fileType, fileSize);
        // 上传文件，获取文件路径
        String filePath = MinioUtils.upload(uploadFile);
        log.info("{}：上传成功", uploadFile.getOriginalFilename());
        // 文件名称
        String fileName = FileHelp.getFileNameByPath(filePath);
        // 添加文件数据
        File file = new File();
        file.setName(fileName);
        file.setType(fileType);
        file.setSize(fileSize);
        file.setPath(filePath);
        file.setStorageLocation(StorageLocationConstants.MINIO);
        file.setEnableFlag(BaseConstants.NO);
        save(file);
        // 返回值
        UploadVO vo = new UploadVO();
        vo.setFileId(file.getId());
        vo.setFilePath(filePath);
        return vo;
    }

    @Override
    public File edit(FileEditDTO dto) {

        log.info("文件-修改：{}", dto);
        // 获取数据库对象
        File file = getById(dto.getId());
        if (ObjUtil.isNull(file)) {
            log.warn("文件-修改：数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "文件-修改：数据不存在");
        }
        // 传入值复制到数据库对象（只复制不为空的属性）
        BeanUtils.copyPropertiesIgnoreNull(dto, file);
        // 修改
        updateById(file);
        return file;
    }

    @Override
    public String previewById(Long id) throws Exception {

        return previewByName(buildNameById(id));
    }

    @Override
    public String previewByName(String name) throws Exception {
        
        if (StrUtil.isBlank(name)) {
            log.warn("文件名称不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "文件名称不能为空");
        }
        return MinioUtils.preview(name);
    }

    @Override
    public void downloadById(Long id, HttpServletResponse res) throws Exception {
        
        downloadByName(buildNameById(id), res);
    }

    @Override
    public void downloadByName(String name, HttpServletResponse res) throws Exception {

        if (StrUtil.isBlank(name)) {
            log.warn("文件名称不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "文件名称不能为空");
        }
        MinioUtils.download(name, res);
    }

    @Override
    public List<FileVO> listObjects() throws Exception {
        List<Item> items = MinioUtils.listObjects();
        if (CollUtil.isEmpty(items)) {
            return null;
        }
        return items.stream().map(this::cast).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delFileById(Long id) throws Exception {
        
        return delFileByName(buildNameById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delFileByName(String name) throws Exception {

        if (StrUtil.isBlank(name)) {
            log.warn("文件名称不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "文件名称不能为空");
        }
        // 删除文件
        MinioUtils.delFile(name);
        // 删除文件表数据
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(File::getName, name);
        return remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enable(Long id) {

        // 检查主键
        checkId(id);
        // 构建修改条件
        UpdateWrapper<File> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(File::getEnableFlag, BaseConstants.YES)
                .eq(File::getId, id);
        // 修改
        return update(updateWrapper);
    }

    @Override
    public String getPathById(Long id) {

        // 检查主键
        checkId(id);
        // 构建查询条件
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(File::getPath)
                .eq(File::getId, id);
        // 查询
        File file = getOne(queryWrapper);
        if (ObjUtil.isNull(file)) {
            log.warn("数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "数据不存在");
        }
        return file.getPath();
    }

    @Override
    public FileInfoVO getInfoById(Long id) {

        // 检查主键
        checkId(id);
        // 构建查询条件
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(File::getPath,
                        File::getType,
                        File::getParam)
                .eq(File::getId, id);
        // 查询
        File file = getOne(queryWrapper);
        if (ObjUtil.isNull(file)) {
            log.warn("数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "数据不存在");
        }
        FileInfoVO vo = BeanUtils.copyProperties(file, FileInfoVO.class);
        vo.setTypeName(baseDictService.getLabel(SystemDictTypeConstants.FILE_TYPE, file.getType()));
        return vo;
    }

    @Override
    public List<FileInfoVO> getInfoList(BaseIdListDTO dto) {

        List<File> fileList = listByIds(dto.getIdList());
        return fileList.stream().map(this::cast).toList();
    }

    /**
     * 转换
     * @param item 项
     * @return 桶内项-返回值
     */
    private FileVO cast(Item item) {
        FileVO vo = new FileVO();
        // 名称
        vo.setName(item.objectName());
        // 大小
        vo.setSize(item.size());
        // 所属人
        vo.setOwner(item.owner().displayName());
        // 更新时间
        vo.setUpdateDate(LocalDateTimeUtils.castChina(item.lastModified().toLocalDateTime()));
        // 是否文件夹
        vo.setDir(item.isDir());
        return vo;
    }

    /**
     * 通过文件ID获取文件名称
     * @param id 文件ID
     * @return 文件名称
     */
    private String buildNameById(Long id) {

        if (ObjUtil.isNull(id)) {
            log.warn("文件ID不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "文件ID不能为空");
        }
        File file = getById(id);
        if (ObjUtil.isNull(file)) {
            log.warn("文件不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "文件不存在");
        }
        return file.getName();
    }

    /**
     * 文件大小校验
     * @param fileType 文件类型
     * @param fileSize 文件大小
     */
    private void checkSize(String fileType, long fileSize) {
        
        // 图片大小校验
        if (StrUtil.equals(fileType, FileTypeConstants.IMG)) {
            if (fileSize > FileSizeConstants.IMG) {
                log.warn("图片文件大小超过 " + FileSizeConstants.IMG / 1024 + " KB 限制");
                throw new BusinessException(ErrorCodeConstants.TOO_LONG, "图片文件大小超过 " + FileSizeConstants.IMG / 1024 + " KB 限制");
            }
        }
    }

    /**
     * 检查
     * @param id 文件ID
     */
    private void checkId(Long id) {
        
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }
    }

    /**
     * 转换
     * @param file 文件
     * @return 返回值
     */
    private FileInfoVO cast(File file) {
        
        FileInfoVO vo = BeanUtils.copyProperties(file, FileInfoVO.class);
        vo.setTypeName(baseDictService.getLabel(SystemDictTypeConstants.FILE_TYPE, file.getType()));
        return vo;
    }
    
}
