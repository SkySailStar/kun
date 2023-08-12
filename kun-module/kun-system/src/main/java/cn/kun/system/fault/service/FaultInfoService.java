package cn.kun.system.fault.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.system.fault.entity.dto.FaultInfoAddDTO;
import cn.kun.system.fault.entity.dto.FaultInfoPageDTO;
import cn.kun.system.fault.entity.po.FaultInfo;
import cn.kun.system.fault.entity.vo.FaultInfoDetailVO;
import cn.kun.system.fault.entity.vo.FaultInfoPageVO;

/**
 * <p>
 * 故障信息 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:24
 */
public interface FaultInfoService extends IService<FaultInfo> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<FaultInfoPageVO> page(FaultInfoPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    FaultInfoDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    FaultInfo add(FaultInfoAddDTO dto);
    
}
