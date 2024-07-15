package cn.kun.demo.crud.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.demo.crud.entity.dto.AreaPageDTO;
import cn.kun.demo.crud.entity.po.Area;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kun.demo.crud.entity.vo.AreaPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 行政区划 Mapper 接口
 * </p>
 *
 * @author 天航星
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
    
}
