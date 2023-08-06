package cn.kun.system.fault.mapper;

import cn.kun.system.fault.entity.dto.FaultInfoPageDTO;
import cn.kun.system.fault.entity.po.FaultInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kun.system.fault.entity.vo.FaultInfoPageVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 故障信息 Mapper 接口
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:24
 */
public interface FaultInfoMapper extends BaseMapper<FaultInfo> {

    /**
     * 查询分页列表
     * @param page 分页参数
     * @param dto 分页列表-传入值
     * @return 分页列表
     */
    Page<FaultInfoPageVO> selectPage(Page<FaultInfo> page, @Param("dto") FaultInfoPageDTO dto);
    
}
