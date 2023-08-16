package cn.kun.auth.login.service;

import cn.kun.auth.login.entity.dto.CheckDTO;
import cn.kun.auth.login.entity.dto.LoginLimitAddDTO;
import cn.kun.auth.login.entity.dto.LoginLimitEditDTO;
import cn.kun.auth.login.entity.dto.LoginLimitPageDTO;
import cn.kun.auth.login.entity.dto.SetStatusDTO;
import cn.kun.auth.login.entity.po.LoginLimitInfoInner;
import cn.kun.auth.login.entity.vo.CheckResultVO;
import cn.kun.auth.login.entity.vo.LoginLimitPageVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 内部登录限制表 服务类
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:37
 */
public interface LoginLimitInfoInnerService extends IService<LoginLimitInfoInner> {
    /**
     * 分页列表
     * @param dto 登录限制分页列表-传入值
     * @return 登录限制分页列表-返回值
     */
    Page<LoginLimitPageVO> page(LoginLimitPageDTO dto);

    /**
     * 添加
     * @param dto 登录限制添加-传入值
     * @return 登录限制对象-返回值
     */
    LoginLimitInfoInner add(LoginLimitAddDTO dto);

    /**
     * 修改
     * @param dto 登录限制修改-传入值
     * @return 登录限制对象-返回值
     */
    LoginLimitInfoInner edit(LoginLimitEditDTO dto);

    /**
     * 根据主键删除
     * @param id 主键
     */
    void remove(Long id);

    /**
     * 是否限制登录
     * @param dto
     * @return
     */
    CheckResultVO isLimited(CheckDTO dto);

    /**
     * 设置限制登录状态
     * @param dto
     * @return
     */
    void setStatus(SetStatusDTO dto);

}
