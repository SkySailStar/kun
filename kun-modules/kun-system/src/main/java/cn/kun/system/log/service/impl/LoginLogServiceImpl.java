package cn.kun.system.log.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.system.dict.service.DictDataService;
import cn.kun.system.log.entity.vo.LoginLogDetailVO;
import cn.kun.system.log.entity.vo.LoginLogPageVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.SystemDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.bean.BeanHelp;
import cn.kun.base.core.global.util.dict.DictHelp;
import cn.kun.system.log.entity.dto.LoginLogAddDTO;
import cn.kun.system.log.entity.dto.LoginLogPageDTO;
import cn.kun.system.log.entity.po.LoginLog;
import cn.kun.system.log.mapper.LoginLogMapper;
import cn.kun.system.log.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author SkySailStar
 * @since 2023-04-09 16:48
 */
@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    private DictDataService dictDataService;
    
    @Override
    public Page<LoginLogPageVO> page(LoginLogPageDTO dto) {

        log.info("登录日志-分页-开始：{}", dto);
        // 封装分页参数
        Page<LoginLog> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 构建查询条件
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(LoginLog::getId,
                        LoginLog::getLoginName,
                        LoginLog::getLoginIp,
                        LoginLog::getLoginLocation,
                        LoginLog::getLoginTime,
                        LoginLog::getLoginWays,
                        LoginLog::getLoginBusiness,
                        LoginLog::getLoginFlag,
                        LoginLog::getBrowserType,
                        LoginLog::getOsType,
                        LoginLog::getMobileModel)
                .like(StrUtil.isNotBlank(dto.getLoginName()), LoginLog::getLoginName, dto.getLoginName())
                .like(StrUtil.isNotBlank(dto.getLoginIp()), LoginLog::getLoginIp, dto.getLoginIp())
                .like(StrUtil.isNotBlank(dto.getLoginLocation()), LoginLog::getLoginLocation, dto.getLoginLocation())
                .gt(ObjUtil.isNotNull(dto.getLoginStartTime()), LoginLog::getLoginTime, dto.getLoginStartTime())
                .lt(ObjUtil.isNotNull(dto.getLoginEndTime()), LoginLog::getLoginTime, dto.getLoginEndTime())
                .eq(StrUtil.isNotBlank(dto.getLoginWays()), LoginLog::getLoginWays, dto.getLoginWays())
                .eq(StrUtil.isNotBlank(dto.getLoginBusiness()), LoginLog::getLoginBusiness, dto.getLoginBusiness())
                .eq(StrUtil.isNotBlank(dto.getLoginFlag()), LoginLog::getLoginFlag, dto.getLoginFlag());
        // 构建排序条件
        if (ObjUtil.isNotNull(dto.getLoginTimeAsc())) {
            if (dto.getLoginTimeAsc()) {
                queryWrapper.lambda().orderByAsc(LoginLog::getLoginTime);
            } else {
                queryWrapper.lambda().orderByDesc(LoginLog::getLoginTime);
            }
        } else {
            queryWrapper.lambda().orderByDesc(LoginLog::getUpdateDate);
        }
        // 分页列表查询
        page = page(page, queryWrapper);
        return (Page<LoginLogPageVO>) page.convert(loginLog -> {
            LoginLogPageVO vo = new LoginLogPageVO();
            BeanHelp.copyProperties(loginLog, vo);
            // 登录方式名称
            vo.setLoginWaysName(dictDataService.getLabel(SystemDictTypeConstants.LOGIN_WAYS, loginLog.getLoginWays()));
            // 登录业务类型名称
            vo.setLoginBusinessName(dictDataService.getLabel(SystemDictTypeConstants.LOGIN_BUSINESS, loginLog.getLoginBusiness()));
            // 登录标识名称
            vo.setLoginFlagName(DictHelp.castSuccessFlag(loginLog.getLoginFlag()));
            // 浏览器类型名称
            vo.setBrowserTypeName(dictDataService.getLabel(SystemDictTypeConstants.BROWSER_TYPE, loginLog.getBrowserType()));
            // 操作系统名称
            vo.setOsTypeName(dictDataService.getLabel(SystemDictTypeConstants.OS_TYPE, loginLog.getOsType()));
            return vo;
        });
    }

    @Override
    public LoginLogDetailVO detail(Long id) {

        log.info("登录日志-详情-开始：{}", id);
        // 查询详情
        LoginLog loginLog = getById(id);
        if (ObjUtil.isNull(loginLog)) {
            log.warn("登录日志-详情数据不存在");
            throw new BusinessException(ErrorCodeConstants.WITHOUT, "登录日志-详情数据不存在");
        }
        // 复制到返回值
        LoginLogDetailVO vo = new LoginLogDetailVO();
        BeanHelp.copyProperties(loginLog, vo);
        // 登录方式名称
        vo.setLoginWaysName(dictDataService.getLabel(SystemDictTypeConstants.LOGIN_WAYS, loginLog.getLoginWays()));
        // 登录业务类型名称
        vo.setLoginBusinessName(dictDataService.getLabel(SystemDictTypeConstants.LOGIN_BUSINESS, loginLog.getLoginBusiness()));
        // 登录标识名称
        vo.setLoginFlagName(DictHelp.castSuccessFlag(loginLog.getLoginFlag()));
        // 浏览器类型名称
        vo.setBrowserTypeName(dictDataService.getLabel(SystemDictTypeConstants.BROWSER_TYPE, loginLog.getBrowserType()));
        // 操作系统名称
        vo.setOsTypeName(dictDataService.getLabel(SystemDictTypeConstants.OS_TYPE, loginLog.getOsType()));
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginLog add(LoginLogAddDTO dto) {

        log.info("登录日志-添加-开始：{}", dto);
        // 传入值复制到数据库对象
        LoginLog loginLog = new LoginLog();
        BeanHelp.copyProperties(dto, loginLog);
        // 添加
        save(loginLog);
        return loginLog;
    }
    
}
