package cn.kun.system.param.mapper;

import cn.kun.system.param.entity.po.ParamConfig;
import cn.kun.system.param.entity.vo.ParamConfigPageVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.system.param.entity.dto.ParamConfigPageDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 参数配置 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-12 16:16
 */
public interface ParamConfigMapper extends BaseMapper<ParamConfig> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<ParamConfigPageVO> selectPage(Page<ParamConfig> page, @Param("dto") ParamConfigPageDTO dto);
    
}
