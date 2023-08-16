package cn.kun.auth.system.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.company.service.SysCompanyOuterService;
import cn.kun.auth.system.dept.entity.dto.DeptEditDTO;
import cn.kun.auth.system.dept.mapper.SysDeptOuterMapper;
import cn.kun.auth.system.dept.service.SysDeptOuterService;
import cn.kun.auth.system.job.entity.po.SysJobOuter;
import cn.kun.auth.system.job.service.SysJobOuterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.dept.entity.dto.DeptAddDTO;
import cn.kun.auth.system.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.system.dept.entity.po.SysDeptOuter;
import cn.kun.auth.system.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.system.dept.entity.vo.DeptPageVO;
import cn.kun.auth.system.dept.entity.vo.SysDeptInfoVO;
import cn.kun.base.api.entity.auth.vo.DeptInfoVO;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.constant.AuthConstants;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.cache.constant.AuthCacheConstants;
import cn.kun.base.core.global.entity.BaseTreeBuild;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.check.ParentHelp;
import cn.kun.base.core.global.util.convert.ConvertHelp;
import cn.kun.base.core.security.util.AuthHelp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 外部公司部门表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysDeptOuterServiceImpl extends ServiceImpl<SysDeptOuterMapper, SysDeptOuter> implements SysDeptOuterService {
    @Autowired
    private SysCompanyOuterService sysCompanyOuterService;

    @Autowired
    private SysJobOuterService sysJobOuterService;
    @Override
    public Page<DeptPageVO> page(DeptPageDTO dto) {
        log.info("进入外部部门分页");
        // 判断是超级用户还是普通用户
        String loginName = AuthHelp.getLoginName();
        if (!StrUtil.equals(AuthConstants.SYS_ADMIN,loginName)){
            dto.setUserId(ConvertHelp.toLong(AuthHelp.getUserId()));
        }
        Page<DeptPageVO> page = Page.of(dto.getPageNo(), dto.getPageSize());
        List<DeptPageVO> result = baseMapper.selectPage(dto);
        // 分页查询
        List<DeptPageVO> list = new BaseTreeBuild(result).buildTree();
        page.setTotal(list.size());
        page.setRecords(list.stream().skip((long) (dto.getPageNo() - 1) * dto.getPageSize()).limit(dto.getPageSize()).collect(Collectors.toList()));
        return page;
    }

    @Override
    public DeptDetailVO detail(Long id) {
        log.info("进入内部部门详情");
        // 查询详情
        if(ObjUtil.isEmpty(id)){
            log.warn("内部部门id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL,"部门id不能为空");
        }
        return baseMapper.detail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptOuter add(DeptAddDTO dto) {

        log.info("外部部门-开始添加：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        /* 传入值复制到数据库对象 */
        SysDeptOuter sysDeptOuter = new SysDeptOuter();
        BeanUtil.copyProperties(dto, sysDeptOuter);
        sysDeptOuter.setCompanyOuterId(dto.getCompanyId());
        // 校验是否有重名
        QueryWrapper<SysDeptOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysDeptOuter::getCompanyOuterId,dto.getCompanyId())
                .eq(SysDeptOuter::getName,dto.getName());
        List<SysDeptOuter> list = list(queryWrapper);

        if(CollectionUtil.isNotEmpty(list)){
            log.warn("外部部门-添加失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.REPEAT,"部门重名");
        }
        // 添加对象成功后删除对应公司的缓存信息
        boolean add = save(sysDeptOuter);
        if (!add) {
            log.warn("外部部门-添加失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"部门-添加失败");
        }
        log.info("外部部门-成功添加");
//        RedisUtils.delHash(AuthCacheConstants.COMPANY_OUTER_HASH, Convert.toStr(sysDeptOuter.getCompanyOuterId()));
        log.info("外部公司-成功删除缓存");
        sysCompanyOuterService.getCompanyCacheInfoByCompanyId(dto.getCompanyId());
        log.info("外部公司-重新加载外部公司缓存！");
        return sysDeptOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptOuter edit(DeptEditDTO dto) {

        log.info("外部部门-开始修改：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        /* 获取数据库对象 */
        SysDeptOuter sysDeptOuter = getById(dto.getId());
        if (ObjUtil.isNull(sysDeptOuter)) {
            log.warn("外部部门-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"部门-修改的数据不存在，无法修改");
        }
        // 校验是否有重名
        QueryWrapper<SysDeptOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysDeptOuter::getCompanyOuterId,dto.getCompanyId())
                .eq(SysDeptOuter::getName,dto.getName());
        List<SysDeptOuter> list = list(queryWrapper);
        if(CollUtil.isNotEmpty(list) && !ObjUtil.equals(dto.getId(),list.get(0).getId())){
            log.warn("外部部门-添加失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.REPEAT,"部门重名");
        }
        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto, sysDeptOuter);
        sysDeptOuter.setCompanyOuterId(dto.getCompanyId());
        // 修改对象
        boolean edit = updateById(sysDeptOuter);
        // 修改成功后更新缓存
        if (!edit) {
            log.warn("外部部门-修改失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"部门-修改修改");
        }
        log.info("外部部门-成功修改");
//        RedisUtils.delHash(AuthCacheConstants.COMPANY_OUTER_HASH, Convert.toStr(sysDeptOuter.getCompanyOuterId()));
        log.info("外部公司-成功删除缓存");
        sysCompanyOuterService.selectCompanyOuterPowerInfo(dto.getCompanyId());
        log.info("外部公司-重新加载外部公司缓存！");
        return sysDeptOuter;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("外部部门-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "主键不能为空");
        }

        // 删除部门前需要进行校验。1、判断该部门是否有职位，有则不能删除，反之即可；2、判断该部门是否有下部门，有则不能删除，反之即可。
        QueryWrapper<SysJobOuter> queryJob = new QueryWrapper<>();
        queryJob.lambda().eq(SysJobOuter::getDeptOuterId,id);
        List<SysJobOuter> jobList = sysJobOuterService.list(queryJob);

        /* 职位列表为空，判断是否有下级部门 */
        if (CollUtil.isEmpty(jobList)){
            QueryWrapper<SysDeptOuter> queryDept = new QueryWrapper<>();
            queryDept.lambda().eq(SysDeptOuter::getParentId,id);
            List<SysDeptOuter> deptList = list(queryDept);

            if (CollUtil.isEmpty(deptList)){
                // 需要提前查询公司id，防止删除部门之后不能查询公司
                SysDeptOuter sysDeptOuter = getById(id);
                // 删除外部部门信息
                boolean remove = removeById(id);
                // 删除后删除缓存
                if (remove) {
                    log.info("外部部门-成功删除");
                    RedisHelp.delHash(AuthCacheConstants.COMPANY_INNER_HASH, Convert.toStr(sysDeptOuter.getCompanyOuterId()));
                    log.info("外部公司-成功删除缓存");
                }
            }else {
                log.warn("内部部门-删除失败");
                throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前部门有下级部门，不能删除当前部门信息");
            }
        }else {
            log.warn("外部部门-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前部门有职位信息，不能删除当前部门信息");
        }
    }

    /**
     * 通过部门id查询部门缓存信息
     * @param deptId
     * @return
     */
    @Override
    public SysDeptInfoVO getDeptCacheInfoByDeptId(String deptId) {
//        SysDeptInfoVO sysDeptInfoVo = (SysDeptInfoVO) RedisUtils.getHash(CacheConstants.CACHE_DEPT_INFO_CACHE, CacheConstants.DEPT_OUTER_ID_ + deptId);
//        if (ObjUtil.isNull(sysDeptInfoVo)){
//            SysDeptInfoVO deptInfoVo = new SysDeptInfoVO();
//            // 查询部门信息
//            SysDeptOuter sysDeptOuter = baseMapper.selectById(deptId);
//            // 查询职位列表信息
//            QueryWrapper<SysJobOuter> queryWrapper = new QueryWrapper<>();
//            queryWrapper.lambda().select(SysJobOuter::getName).eq(SysJobOuter::getDeptOuterId,deptId);
//            List<SysJobOuter> jobInfoList = sysJobOuterService.list(queryWrapper);
//            // 获取职位名称列表
//            List<String> jobNameList = jobInfoList.stream().map(SysJobOuter::getName).toList();
//            // 获取职位id列表
//            List<Long> jobIdList = jobInfoList.stream().map(BaseEntity::getId).toList();
//            ArrayList<String> jobMenuList = new ArrayList<>();
//            // 循环遍历职位id
//            for (Long jobId: jobIdList) {
//                // 通过职位id查询菜单信息
//                List<SysMenu> sysMenuList = sysMenuService.selectMenuInfoByJobOuterId(jobId);
//                // 获取该职位的菜单权限
//                List<String> menuPermsList = sysMenuList.stream().map(each -> each.getPermission()).toList();
//                jobMenuList.addAll(menuPermsList);
//            }
//            // 去重菜单权限列表
//            List<String> menuPermsList = jobMenuList.stream().distinct().toList();
//            deptInfoVo.setDeptId(deptId);
//            deptInfoVo.setDeptName(sysDeptOuter.getName());
//            deptInfoVo.setJobNameList(jobNameList);
//            deptInfoVo.setMenuPermsList(menuPermsList);
//            RedisUtils.setHash(CacheConstants.CACHE_DEPT_INFO_CACHE, CacheConstants.DEPT_INNER_ID_ + deptId,deptInfoVo);
//            return deptInfoVo;
//        }
//        return sysDeptInfoVo;
        return null;
    }

    /**
     * 通过公司id获取公司部门信息
     *
     * @param companyId 公司id
     * @return
     */
    @Override
    public List<DeptInfoVO> selectDeptCacheInfoByCompanyId(Long companyId){
        return baseMapper.selectDeptCacheInfoByCompanyId(companyId);
    }

    /**
     *  查看内部上级部门信息
     * @return
     */
    @Override
    public List<BaseSelectVO> selectOuterParent(Long companyId){
        log.info("进入外部部门-获取上级部门信息");
//        String loginName = AuthUtils.getLoginName();

        if(ObjUtil.isEmpty(companyId)){
            log.warn("公司id不能为空#{}",companyId);
            throw new BusinessException(ErrorCodeConstants.NULL,"公司id不能为空");
        }
        QueryWrapper<SysDeptOuter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysDeptOuter::getCompanyOuterId,companyId);
        return list(queryWrapper).stream().map(this::cast).toList();
    }


    /**
     *  获取所有部门
     * @param companyIds  公司id
     * @return
     */
    @Override
    public List<BaseSelectVO> selectDeptByCompanyIds(List<Long> companyIds){
        return baseMapper.selectDeptByCompanyIds(companyIds);
    }


    /**
     *  部门左侧树状图
     * @return
     */
    @Override
    public List<BaseSelectVO> tree() {
        log.info("进入内部部门树状图");

        List<BaseSelectVO> companyList = null;
        List<Long> companyIds = new ArrayList<>();

        List<BaseSelectVO> selectDeptByCompanyIds = null;
        // 如果是超级管理员走单独逻辑
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            companyList = sysCompanyOuterService.selectCompanyInfoByLoginName(null);
            selectDeptByCompanyIds = selectDeptByCompanyIds(null);
            selectDeptByCompanyIds.addAll(companyList);
        } else {
            // 获取当前登陆人用户的所有公司信息
            companyList = sysCompanyOuterService.selectOuterParent();
            selectDeptByCompanyIds = selectDeptByCompanyIds(companyIds);
            selectDeptByCompanyIds.addAll(companyList);
        }

        return selectDeptByCompanyIds;
    }
    /**
     * 转换
     * @param obj 对象
     * @return 公用下拉框-返回值
     */
    private BaseSelectVO cast(Object obj) {
        BaseSelectVO vo = new BaseSelectVO();
        if (obj instanceof SysDeptOuter sysDeptOuter) {
            vo.setValue(Convert.toStr(sysDeptOuter.getId()));
            vo.setLabel(sysDeptOuter.getName());
            vo.setParentValue(Convert.toStr(sysDeptOuter.getParentId()));
            vo.setParentValues(sysDeptOuter.getParentIds());
        }
        return vo;
    }
}
