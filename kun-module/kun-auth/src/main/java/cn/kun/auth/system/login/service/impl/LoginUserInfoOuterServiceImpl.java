package cn.kun.auth.system.login.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.login.entity.dto.LoginUserAddDTO;
import cn.kun.auth.system.login.entity.dto.LoginUserPageDTO;
import cn.kun.auth.system.login.entity.po.LoginUserInfoOuter;
import cn.kun.auth.system.login.entity.vo.LoginUserPageVO;
import cn.kun.auth.system.login.mapper.LoginUserInfoOuterMapper;
import cn.kun.auth.system.login.service.LoginUserInfoOuterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.AuthDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 外部用户登录信息表 服务实现类
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:39
 */
@Service
@Slf4j
public class LoginUserInfoOuterServiceImpl extends ServiceImpl<LoginUserInfoOuterMapper, LoginUserInfoOuter> implements LoginUserInfoOuterService {
    @Autowired
    private BaseDictService baseDictService;

    @Override
    public Page<LoginUserPageVO> page(LoginUserPageDTO dto) {
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            log.info("当前用户不能查看用户登录信息");
            throw new BusinessException(ErrorCodeConstants.NO_AUTH,"未授权");
        }
        Page<LoginUserPageDTO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        Page<LoginUserPageVO> voList = baseMapper.selectPage(page, dto);
        for (LoginUserPageVO vo : voList.getRecords()) {
            vo.setLoginType(baseDictService.getLabel(AuthDictTypeConstants.LOGIN_TYPE,vo.getLoginType()));
        }
        return voList;
    }

    @Override
    public LoginUserInfoOuter add(LoginUserAddDTO dto) {
        log.info("用户登录信息-开始添加：{}", dto);
        QueryWrapper<LoginUserInfoOuter> qw = new QueryWrapper<>();
        qw.lambda().eq(LoginUserInfoOuter::getLoginName,dto.getLoginName());
        qw.lambda().eq(LoginUserInfoOuter::getLoginType,dto.getLoginType());
        LoginUserInfoOuter info = getOne(qw);
        if (ObjUtil.isNotEmpty(info)) {
            log.warn("登录类型重复");
            // 更新数据
            BeanUtil.copyProperties(dto,info);
            // 添加对象
            boolean update = updateById(info);
            if (update) {
                log.info("用户登录信息-成功更新");
            }
            else {
                log.warn("用户登录信息-更新失败");
                throw new BusinessException(ErrorCodeConstants.EDIT_FAIL, "用户登录信息-更新失败");
            }
            return info;
        }

        /* 传入值复制到数据库对象 */
        LoginUserInfoOuter loginUserInfoOuter = new LoginUserInfoOuter();
        BeanUtil.copyProperties(dto,loginUserInfoOuter);

        // 添加对象
        boolean add = save(loginUserInfoOuter);
        if (add) {
            log.info("用户登录信息-成功添加");
        }
        else {
            log.warn("用户登录信息-添加失败");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL, "用户登录信息-添加失败");
        }
        return loginUserInfoOuter;
    }

    @Override
    public void remove(Long id) {
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            log.info("当前登录用户不能删除用户登录信息");
            throw new BusinessException(ErrorCodeConstants.NO_AUTH,"未授权");
        }
        log.info("用户登录信息-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }
        // 删除
        boolean remove = removeById(id);
        // 判断是否删除
        if (remove) {
            log.info("用户登录信息-成功删除");
        }
        else{
            log.warn("用户登录信息-删除失败");
        }
    }

}
