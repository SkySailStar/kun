package cn.kun.system.error.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.kun.base.core.mq.entity.dto.ErrorInfoMqDTO;
import cn.kun.system.error.entity.dto.ErrorInfoPageDTO;
import cn.kun.system.error.entity.po.ErrorInfo;
import cn.kun.system.error.entity.vo.ErrorInfoDetailVO;
import cn.kun.system.error.entity.vo.ErrorInfoPageVO;

/**
 * <p>
 * 错误编码 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-11 17:22
 */
public interface ErrorInfoService extends IService<ErrorInfo> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<ErrorInfoPageVO> page(ErrorInfoPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    ErrorInfoDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     */
    void add(ErrorInfoMqDTO dto);
    
}
