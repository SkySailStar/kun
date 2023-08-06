package cn.kun.system.log.service;

import cn.kun.system.log.entity.dto.LoginLogAddDTO;
import cn.kun.system.log.entity.dto.LoginLogPageDTO;
import cn.kun.system.log.entity.po.LoginLog;
import cn.kun.system.log.entity.vo.LoginLogDetailVO;
import cn.kun.system.log.entity.vo.LoginLogPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 分页
     * @param dto 分页-传入值
     * @return 分页-返回值
     */
    Page<LoginLogPageVO> page(LoginLogPageDTO dto);

    /**
     * 详情
     * @param id 主键
     * @return 详情-返回值
     */
    LoginLogDetailVO detail(Long id);

    /**
     * 添加
     * @param dto 添加-传入值
     * @return 添加的数据
     */
    LoginLog add(LoginLogAddDTO dto);
    
}
