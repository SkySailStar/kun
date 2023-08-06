package cn.kun.system.area.mapper;

import cn.kun.system.area.entity.dto.AreaPageDTO;
import cn.kun.system.area.entity.po.Area;
import cn.kun.system.area.entity.vo.AreaPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 行政区划 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-06 18:08
 */
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<AreaPageVO> selectPage(Page<Area> page, @Param("dto") AreaPageDTO dto);

    /**
     * 查询列表
     * @return 列表
     */
    List<BaseSelectVO> selectAllList();
    
}
