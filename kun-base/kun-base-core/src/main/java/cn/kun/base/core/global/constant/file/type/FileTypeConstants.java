package cn.kun.base.core.global.constant.file.type;

import cn.kun.base.core.global.constant.file.suffix.AudioSuffixConstants;
import cn.kun.base.core.global.constant.file.suffix.VideoSuffixConstants;
import cn.kun.base.core.global.constant.file.suffix.ImgSuffixConstants;

import java.util.Arrays;
import java.util.List;

/**
 * 文件类型常量类
 *
 * @author 天航星
 * @date 2023-03-28 19:52
 */
public class FileTypeConstants {

    /**
     * 图片
     */
    public static final List<String> IMG = Arrays.asList(
            ImgSuffixConstants.PNG, 
            ImgSuffixConstants.JPG, 
            ImgSuffixConstants.JPEG, 
            ImgSuffixConstants.BMP, 
            ImgSuffixConstants.GIF
    );

    /**
     * 音频
     */
    public static final List<String> AUDIO = Arrays.asList(
            AudioSuffixConstants.MP3, 
            AudioSuffixConstants.WAV, 
            AudioSuffixConstants.WMA, 
            AudioSuffixConstants.MP2, 
            AudioSuffixConstants.FLAC, 
            AudioSuffixConstants.MIDI, 
            AudioSuffixConstants.RA, 
            AudioSuffixConstants.APE, 
            AudioSuffixConstants.AAC, 
            AudioSuffixConstants.CDA
    );

    /**
     * 视频
     */
    public static final List<String> VIDEO = Arrays.asList(
            VideoSuffixConstants.AVI,
            VideoSuffixConstants.MP4,
            VideoSuffixConstants.MPG,
            VideoSuffixConstants.MPEG,
            VideoSuffixConstants.WMV,
            VideoSuffixConstants.MOV,
            VideoSuffixConstants.RM,
            VideoSuffixConstants.RAM,
            VideoSuffixConstants.RMVB,
            VideoSuffixConstants.SWF,
            VideoSuffixConstants.FLV
    );
    
}
