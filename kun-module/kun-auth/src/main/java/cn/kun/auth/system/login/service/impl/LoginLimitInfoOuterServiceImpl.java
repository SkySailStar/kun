package cn.kun.auth.system.login.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.login.entity.dto.CheckDTO;
import cn.kun.auth.system.login.entity.dto.LoginLimitAddDTO;
import cn.kun.auth.system.login.entity.dto.LoginLimitEditDTO;
import cn.kun.auth.system.login.entity.dto.LoginLimitPageDTO;
import cn.kun.auth.system.login.entity.dto.SetStatusDTO;
import cn.kun.auth.system.login.entity.po.LoginLimitInfoOuter;
import cn.kun.auth.system.login.entity.vo.CheckResultVO;
import cn.kun.auth.system.login.entity.vo.LoginLimitPageVO;
import cn.kun.auth.system.login.mapper.LoginLimitInfoOuterMapper;
import cn.kun.auth.system.login.service.LoginLimitInfoOuterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.dict.type.AuthDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 外部登录限制表 服务实现类
 * </p>
 *
 * @author 胡鹤龄
 * @since 2023-02-27 10:38
 */
@Service
@Slf4j
public class LoginLimitInfoOuterServiceImpl extends ServiceImpl<LoginLimitInfoOuterMapper, LoginLimitInfoOuter> implements LoginLimitInfoOuterService {

    @Resource
    private BaseDictService baseDictService;

    @Override
    public Page<LoginLimitPageVO> page(LoginLimitPageDTO dto) {
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            log.info("当前登录用户不能查询用户限制登录信息");
            throw new BusinessException(ErrorCodeConstants.NO_AUTH,"未授权");
        }
        Page<LoginLimitPageDTO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        // 分页查询
        Page<LoginLimitPageVO> voList = baseMapper.selectPage(page, dto);
        for (LoginLimitPageVO vo : voList.getRecords()) {
            vo.setLoginType(baseDictService.getLabel(AuthDictTypeConstants.LOGIN_TYPE,vo.getLoginType()));
        }
        return voList;
    }

    @Override
    public LoginLimitInfoOuter add(LoginLimitAddDTO dto) {

        log.info("用户登录限制信息-开始添加：{}",dto);
        // 校验登录类型
        QueryWrapper<LoginLimitInfoOuter> qw = new QueryWrapper<>();
        qw.lambda().eq(LoginLimitInfoOuter::getLoginType, dto.getLoginType());
        qw.lambda().eq(LoginLimitInfoOuter::getLoginName,dto.getLoginName());
        long count = count(qw);
        if (count >= 1) {
            log.info("登录类型重复");
            throw new BusinessException(ErrorCodeConstants.REPEAT,"登录类型重复");
        }

        // 校验生效开始时间是否小于生效结束时间
        boolean verifyResult = belongCalendar(dto.getStartTime(), dto.getEndTime());
        if (verifyResult) {
            log.warn("生效开始时间不能大于生效结束时间，请重新选择时间");
            throw new BusinessException(ErrorCodeConstants.PARAM_ERROR, "生效开始时间不能大于生效结束时间，请重新选择时间");
        }

        // 校验限制登录开始时间是否小于限制登录结束时间
        boolean result = belongCalendar(dto.getStartDate(), dto.getEndDate());
        if (result) {
            log.warn("限制登录开始时间不能大于限制登录结束时间，请重新选择时间");
            throw new BusinessException(ErrorCodeConstants.PARAM_ERROR, "限制登录开始时间不能大于限制登录结束时间，请重新选择时间");
        }

        // 校验限制登录的开始时间是否大于生效开始时间
        boolean compareResult = belongCalendar(dto.getStartTime(), dto.getStartDate());
        if (compareResult) {
            log.warn("限制登录开始时间不能小于生效开始时间，请重新选择限制登陆开始时间");
            throw new BusinessException(ErrorCodeConstants.PARAM_ERROR, "限制登录开始时间不能小于生效开始时间，请重新选择时间");
        }

        /* 传入值复制到数据库对象 */
        LoginLimitInfoOuter loginLimitInfoOuter = new LoginLimitInfoOuter();
        BeanUtil.copyProperties(dto,loginLimitInfoOuter);
        loginLimitInfoOuter.setUserOuterIp(dto.getIp());
        // 保存数据
        boolean add = save(loginLimitInfoOuter);
        if (add) {
            log.info("用户登录限制信息-成功添加");
        }
        else {
            log.warn("用户登录限制信息-添加失败");
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"用户登录限制信息-添加失败");
        }
        return loginLimitInfoOuter;
    }

    @Override
    public LoginLimitInfoOuter edit(LoginLimitEditDTO dto) {
        log.info("用户登录限制信息-开始修改：{}", dto);
        /* 获取数据库对象 */
        LoginLimitInfoOuter loginLimit = getById(dto.getId());
        if (ObjUtil.isEmpty(loginLimit)){
            log.warn("用户登录限制信息-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"用户登录限制信息-修改的数据不存在，无法修改");
        }

        // 校验登录类型
        if (StrUtil.equals(loginLimit.getLoginType(),dto.getLoginType())) {
            log.info("登录类型重复");
            throw new BusinessException(ErrorCodeConstants.REPEAT,"登录类型重复");
        }

        // 校验生效开始时间是否小于生效结束时间
        boolean verifyResult = belongCalendar(dto.getStartTime(), dto.getEndTime());
        if (verifyResult) {
            log.warn("生效开始时间不能大于生效结束时间，请重新选择时间");
            throw new BusinessException(ErrorCodeConstants.PARAM_ERROR, "生效开始时间不能大于生效结束时间，请重新选择时间");
        }

        // 校验限制登录开始时间是否小于限制登录结束时间
        boolean result = belongCalendar(dto.getStartDate(), dto.getEndDate());
        if (result) {
            log.warn("限制登录开始时间不能大于限制登录结束时间，请重新选择时间");
            throw new BusinessException(ErrorCodeConstants.PARAM_ERROR, "限制登录开始时间不能大于限制登录结束时间，请重新选择时间");
        }

        // 校验限制登录的开始时间是否大于生效开始时间
        boolean compareResult = belongCalendar(dto.getStartTime(), dto.getStartDate());
        if (compareResult) {
            log.warn("限制登录开始时间不能小于生效开始时间，请重新选择限制登陆开始时间");
            throw new BusinessException(ErrorCodeConstants.PARAM_ERROR, "限制登录开始时间不能小于生效开始时间，请重新选择时间");
        }

        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto,loginLimit);
        boolean edit = updateById(loginLimit);
        // 修改成功后更新缓存
        if (edit) {
            log.info("用户登录限制信息-成功修改");
        }
        else {
            log.warn("用户登录限制信息-修改失败");
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"用户登录限制信息-修改失败");
        }
        return loginLimit;
    }

    @Override
    public void remove(Long id) {

        log.info("用户登录限制信息-开始删除：{}", id);
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            log.info("当前登录用户不能删除用户限制登录信息");
            throw new BusinessException(ErrorCodeConstants.NO_AUTH,"未授权");
        }
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }
        // 删除
        boolean remove = removeById(id);
        // 判断是否删除成功
        if (remove) {
            log.info("用户登录限制信息-成功删除");
        }
        else {
            log.warn("用户登录限制信息-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL,"用户登录限制信息-删除失败");
        }
    }

    /**
     * 校验当前用户是否被限制登录
     * @param dto
     * @return
     */
    @Override
    public CheckResultVO isLimited(CheckDTO dto) {

        log.warn("用户登录是否限制-开始验证：{}", dto);
        Boolean isLogin = true;
        String msg = "";
        CheckResultVO checkResultVO = new CheckResultVO();

        // 查询是否有开启的状态
        QueryWrapper<LoginLimitInfoOuter> qw = new QueryWrapper<>();
        qw.lambda().eq(LoginLimitInfoOuter::getStatus,"1");
        qw.lambda().isNull(LoginLimitInfoOuter::getLoginName);
        LoginLimitInfoOuter data = getOne(qw);
        // 获取当前时间
        LocalDateTime nowTime = LocalDateTime.now();
        // 开启状态不为空，判断是否有对所有登录用户有限制
        if (ObjUtil.isNotEmpty(data)) {
            LocalDateTime startDate = data.getStartDate();
            LocalDateTime endDate = data.getEndDate();
            LocalDateTime startTime = data.getStartTime();
            LocalDateTime endTime = data.getEndTime();

            // 判断当前时间是否在生效时间范围内
            if (startTime.isBefore(nowTime) && nowTime.isBefore(endTime)){
                // 登录名为空、ip为空、在限制时间范围内，就限制所有登录用户
                if (startDate.isBefore(nowTime) && nowTime.isBefore(endDate)){
                    isLogin = false;
                    msg = "所有登录用户在【"+ DateUtil.parse(String.valueOf(endTime))+"】前被限制登录";
                    checkResultVO.setIsLogin(isLogin);
                    checkResultVO.setMsg(msg);
                    return checkResultVO;
                }
                else if (StrUtil.equals(data.getLoginType(), dto.getLoginType())){
                    isLogin = false;
                    msg = "所有登录用户的登录类型已被限制登录";
                    checkResultVO.setIsLogin(isLogin);
                    checkResultVO.setMsg(msg);
                    return checkResultVO;
                }
                // 登录名为空、ip不为空，在限制时间范围内，ip限制所有登录用户
                else if (StrUtil.isNotBlank(data.getUserOuterIp())){
                    // 校验ip
                    String ip = dto.getIp();
                    String regex = "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(ip);
                    if (m.find()){
                        msg = "所有登录用户ip已被限制登录";
                        isLogin = false;
                        checkResultVO.setIsLogin(isLogin);
                        checkResultVO.setMsg(msg);
                        return checkResultVO;
                    }
                }
            }

        }

        // 清空查询条件
        qw.clear();
        qw.lambda().eq(LoginLimitInfoOuter::getLoginName,dto.getLoginName());
        qw.lambda().eq(LoginLimitInfoOuter::getStatus,"1");
        LoginLimitInfoOuter obj = getOne(qw);
        /* 判断当前登录用户是否单独被限制 */
        if (ObjUtil.isNotEmpty(obj)){
            // 有数据且ip为空，在当前限制时间内
            LocalDateTime startDate = obj.getStartDate();
            LocalDateTime endDate = obj.getEndDate();
            LocalDateTime startTime = obj.getStartTime();
            LocalDateTime endTime = obj.getEndTime();

            // 判断当前时间是否在生效时间范围内
            if (startTime.isBefore(nowTime) && nowTime.isBefore(endTime)){
                if (startDate.isBefore(nowTime) && nowTime.isBefore(endDate)) {
                    isLogin = false;
                    msg = "当前登录用户在【"+ DateUtil.parse(String.valueOf(endTime))+"】前被限制登录";
                    checkResultVO.setIsLogin(isLogin);
                    checkResultVO.setMsg(msg);
                    return checkResultVO;
                }
                else if (StrUtil.equals(obj.getLoginType(), dto.getLoginType())){
                    isLogin = false;
                    msg = "当前登录用户登录类型已被限制登录";
                    checkResultVO.setIsLogin(isLogin);
                    checkResultVO.setMsg(msg);
                    return checkResultVO;
                }
                else if (StrUtil.isNotBlank(obj.getUserOuterIp()) && StrUtil.equals(obj.getLoginType(), dto.getLoginType()) && (obj.getStartDate().isBefore(nowTime) && nowTime.isBefore(obj.getEndDate()))) {
                    // 校验ip
                    String ip = dto.getIp();
                    String regex = "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(ip);
                    if (m.find()){
                        msg = "当前登录用户ip已被限制登录";
                        isLogin = false;
                        checkResultVO.setIsLogin(isLogin);
                        checkResultVO.setMsg(msg);
                        return checkResultVO;
                    }
                }

            }

        }

        isLogin = true;
        msg = "当前登录用户可以登录";
        checkResultVO.setIsLogin(isLogin);
        checkResultVO.setMsg(msg);
        return checkResultVO;
    }

    /**
     * 比较开始时间、结束时间
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean belongCalendar(@NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate) {
        // 开始时间大于结束时间，返回true
        if (startDate.isAfter(endDate)) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void setStatus(SetStatusDTO dto) {

        log.info("登录限制状态-开始设置：{}", dto);
        // 创建更新对象
        UpdateWrapper<LoginLimitInfoOuter> uw = new UpdateWrapper<>();
        uw.lambda().eq(LoginLimitInfoOuter::getId, dto.getId());
        /* 对象赋值 */
        LoginLimitInfoOuter loginLimitInfoOuter = new LoginLimitInfoOuter();
        loginLimitInfoOuter.setStatus(dto.getStatus());
        // 修改状态
        boolean update = update(loginLimitInfoOuter, uw);
        if (update) {
            log.info("登录限制状态-成功修改");
        } else {
            log.warn("登录限制状态-修改失败");
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL, "登录限制状态-修改失败");
        }
    }
}
