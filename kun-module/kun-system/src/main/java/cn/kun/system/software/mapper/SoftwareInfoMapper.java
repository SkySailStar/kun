package cn.kun.system.software.mapper;

import cn.kun.system.software.entity.dto.SoftwareInfoPageDTO;
import cn.kun.system.software.entity.po.SoftwareInfo;
import cn.kun.system.software.entity.vo.SoftwareInfoPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 软件服务信息 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-07 10:37
 */
public interface SoftwareInfoMapper extends BaseMapper<SoftwareInfo> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<SoftwareInfoPageVO> selectPage(Page<SoftwareInfo> page, @Param("dto") SoftwareInfoPageDTO dto);
    
}
