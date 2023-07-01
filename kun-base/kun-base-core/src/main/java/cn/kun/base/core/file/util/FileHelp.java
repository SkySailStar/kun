package cn.kun.base.core.file.util;

import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.global.constant.file.type.FileTypeConstants;
import cn.kun.base.core.global.config.GlobalConfig;
import cn.kun.base.core.global.util.date.LocalDateTimeHelp;
import cn.kun.base.core.global.util.id.SeqHelp;
import cn.kun.base.core.global.util.str.StrHelp;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * 文件工具类
 *
 * @author lujun
 */
public class FileHelp {
    
    /**
     * 构建文件名称
     *
     * @param file 文件信息
     * @return 文件名称
     */
    public static String buildFileNameByFile(MultipartFile file) {
        
        // 当前时间
        String time = LocalDateTimeHelp.format(LocalDateTime.now(), LocalDateTimeHelp.YYYYMMDDHHMMSS);
        // 文件名称
        String fileName = getPrefixByFileName(file.getOriginalFilename());
        // 序列号
        String seqId = GlobalConfig.getMachineCode() + SeqHelp.getSeqNo();
        // 文件类型
        String fileType = getSuffixByFile(file);
        return StrHelp.format("{}_{}_{}.{}", time, fileName, seqId, fileType);
    }
    
    /**
     * 获取文件名后缀
     *
     * @param file 文件信息
     * @return 文件名后缀
     */
    public static String getSuffixByFile(MultipartFile file) {
        
        String fileName = getSuffixByFile(file.getOriginalFilename());
        if (StrHelp.isEmpty(fileName)) {
            return "";
        }
        return getSuffixByFileName(fileName);
    }

    /**
     * 根据路径获取前缀
     *
     * @param path 路径
     * @return 路径前缀
     */
    public static String getPrefixByPath(String path) {
        
        if (StrUtil.isBlank(path)) {
            return "";
        }
        return path.substring(0, path.lastIndexOf("/") + 1);
    }

    /**
     * 通过路径获得文件名
     *
     * @param path 路径
     * @return 文件名
     */
    public static String getFileNameByPath(String path) {
        
        if (StrUtil.isBlank(path)) {
            return "";
        }
        return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * 通过文件名获得前缀
     *
     * @param fileName 文件名
     * @return 文件名前缀
     */
    public static String getPrefixByFileName(String fileName) {
        if (StrUtil.isBlank(fileName)) {
            return "";
        }
        return fileName.substring(0, fileName.indexOf("."));
    }

    /**
     * 通过文件名获得后缀
     *
     * @param fileName 文件名
     * @return 文件名后缀
     */
    public static String getSuffixByFileName(String fileName) {
        
        if (StrUtil.isBlank(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 通过文件名获得后缀
     *
     * @param source 来源
     * @return 文件后缀
     */
    public static String getSuffixByFile(String source) {
        
        if (StrUtil.isBlank(source)) {
            return "";
        }
        return source.substring(source.lastIndexOf(".") + 1);
    }

    /**
     * 通过文件后缀构建文件类型
     * @param fileName 文件名称
     * @return 文件类型
     */
    public static String buildTypeByName(String fileName) {
        
        String suffix = getSuffixByFileName(fileName);
        // 图片文件
        if (FileTypeConstants.IMG.contains(suffix)) {
            return cn.kun.base.core.global.constant.dict.data.FileTypeConstants.IMG;
        } 
        // 音频文件
        else if (FileTypeConstants.AUDIO.contains(suffix)) {
            return cn.kun.base.core.global.constant.dict.data.FileTypeConstants.AUDIO;
        } 
        // 视频文件
        else if (FileTypeConstants.VIDEO.contains(suffix)) {
            return cn.kun.base.core.global.constant.dict.data.FileTypeConstants.VIDEO;
        } 
        // 其他文件
        else {
            return cn.kun.base.core.global.constant.dict.data.FileTypeConstants.OTHER;
        }
    }
}