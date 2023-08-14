package cn.kun.auth.system.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.auth.system.company.service.SysCompanyInnerService;
import cn.kun.auth.system.dept.entity.dto.DeptEditDTO;
import cn.kun.auth.system.dept.mapper.SysDeptInnerMapper;
import cn.kun.auth.system.dept.service.SysDeptInnerService;
import cn.kun.auth.system.job.entity.po.SysJobInner;
import cn.kun.auth.system.job.service.SysJobInnerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.kun.auth.system.dept.entity.dto.DeptAddDTO;
import cn.kun.auth.system.dept.entity.dto.DeptPageDTO;
import cn.kun.auth.system.dept.entity.po.SysDeptInner;
import cn.kun.auth.system.dept.entity.vo.DeptDetailVO;
import cn.kun.auth.system.dept.entity.vo.DeptPageVO;
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
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 内部公司部门表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
@Slf4j
public class SysDeptInnerServiceImpl extends ServiceImpl<SysDeptInnerMapper, SysDeptInner> implements SysDeptInnerService {
    @Resource
    private SysJobInnerService sysJobInnerService;
    @Resource
    private SysCompanyInnerService sysCompanyInnerService;

    @Override
    public Page<DeptPageVO> page(DeptPageDTO dto) {
        log.info("进入内部部门分页");
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
            throw new BusinessException(ErrorCodeConstants.NULL,"内部部门id不能为空");
        }
        return baseMapper.detail(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptInner add(DeptAddDTO dto) {

        log.info("内部部门-开始添加：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        /* 传入值复制到数据库对象 */
        SysDeptInner sysDeptInner = new SysDeptInner();
        BeanUtil.copyProperties(dto, sysDeptInner);
        sysDeptInner.setCompanyInnerId(dto.getCompanyId());
        // 校验是否有重名
        QueryWrapper<SysDeptInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysDeptInner::getCompanyInnerId,dto.getCompanyId())
                .eq(SysDeptInner::getName,dto.getName());
        List<SysDeptInner> list = list(queryWrapper);

        if(CollectionUtil.isNotEmpty(list)){
            log.warn("内部部门-添加失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.REPEAT,"部门重名");
        }
        // 添加对象成功后删除对应公司的缓存信息
        boolean add = save(sysDeptInner);
        if (!add) {
            log.warn("内部部门-添加失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.ADD_FAIL,"部门-添加失败");
        }
        log.info("内部部门-成功添加");
//        RedisUtils.delHash(AuthCacheConstants.COMPANY_INNER_HASH, Convert.toStr(sysDeptInner.getCompanyInnerId()));
        log.info("内部公司-成功删除缓存");
        sysCompanyInnerService.selectCompanyPowerInfo(dto.getCompanyId());
        log.info("内部公司-重新加载内部公司缓存信息");
        return sysDeptInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDeptInner edit(DeptEditDTO dto) {

        log.info("内部部门-开始修改：{}", dto);
        // 校验上级是否为空
        dto.setParentId(ParentHelp.checkParentId(dto.getParentId()));
        // 校验所有上级是否为空
        dto.setParentIds(ParentHelp.checkParentIds(dto.getParentIds()));

        /* 获取数据库对象 */
        SysDeptInner sysDeptInner = getById(dto.getId());
        if (ObjUtil.isNull(sysDeptInner)) {
            log.warn("内部部门-修改的数据不存在，无法修改");
            throw new BusinessException(ErrorCodeConstants.WITHOUT,"部门-修改的数据不存在，无法修改");
        }
        // 传入值复制到数据库对象
        BeanUtil.copyProperties(dto, sysDeptInner);
        sysDeptInner.setCompanyInnerId(dto.getCompanyId());

        // 校验是否有重名
        QueryWrapper<SysDeptInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysDeptInner::getCompanyInnerId,dto.getCompanyId())
                .eq(SysDeptInner::getName,dto.getName());
        List<SysDeptInner> list = list(queryWrapper);
        if(CollUtil.isNotEmpty(list) && !ObjUtil.equals(dto.getId(),list.get(0).getId())){
            log.warn("内部部门-添加失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.REPEAT,"部门重名");
        }

        // 修改对象
        boolean edit = updateById(sysDeptInner);
        // 修改成功后更新缓存
        if (!edit) {
            log.warn("内部部门-修改失败#{}",dto);
            throw new BusinessException(ErrorCodeConstants.EDIT_FAIL,"部门-修改失败");
        }
        log.info("内部部门-成功修改");
//        RedisUtils.delHash(AuthCacheConstants.COMPANY_INNER_HASH, Convert.toStr(sysDeptInner.getCompanyInnerId()));
        log.info("内部公司-成功删除缓存");
        sysCompanyInnerService.selectCompanyPowerInfo(dto.getCompanyId());
        log.info("内部公司-重新加载内部公司缓存信息");
        return sysDeptInner;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {

        log.info("内部部门-开始删除：{}", id);
        // 判空
        if (ObjUtil.isNull(id)) {
            log.warn("部门id不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "部门id不能为空");
        }

        // 删除部门前需要进行校验。1、判断该部门是否有职位，有则不能删除，反之即可；2、判断该部门是否有下部门，有则不能删除，反之即可。
        QueryWrapper<SysJobInner> queryJob = new QueryWrapper<>();
        queryJob.lambda().eq(SysJobInner::getDeptInnerId,id);
        List<SysJobInner> jobList = sysJobInnerService.list(queryJob);

        /* 职位列表为空，判断是否有下级部门 */
        if (CollUtil.isEmpty(jobList)){

            QueryWrapper<SysDeptInner> queryDept = new QueryWrapper<>();
            queryDept.lambda().eq(SysDeptInner::getParentId,id);
            List<SysDeptInner> deptList = list(queryDept);

            if (CollUtil.isEmpty(deptList)){
                // 需要提前查询公司id，防止删除部门之后不能查询公司
                SysDeptInner sysDeptInner = getById(id);

                // 删除内部部门信息
                boolean remove = removeById(id);
                // 删除后删除缓存
                if (remove) {
                    log.info("内部部门-成功删除");
                }

                /* 查询当前部门信息，获取公司id，便于删除公司缓存 */
                RedisHelp.delHash(AuthCacheConstants.COMPANY_INNER_HASH, Convert.toStr(sysDeptInner.getCompanyInnerId()));
                log.info("内部部门-成功删除缓存");
            }
            else {
                log.warn("内部部门-删除失败");
                throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前部门有下级部门，不能删除当前部门信息");
            }
        }
        else {
            log.warn("内部部门-删除失败");
            throw new BusinessException(ErrorCodeConstants.DEL_FAIL, "当前部门有职位信息，不能删除当前部门信息");
        }
    }

    /**
     * 通过公司id查询部门信息
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
    public List<BaseSelectVO> selectInnerParent(Long companyId){
        log.info("进入内部部门-获取上级部门信息");
        if(ObjUtil.isEmpty(companyId)){
            log.warn("公司id不能为空#{}",companyId);
            throw new BusinessException(ErrorCodeConstants.NULL,"公司id不能为空");
        }
        QueryWrapper<SysDeptInner> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysDeptInner::getCompanyInnerId,companyId);

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

        List<Long> companyIds = new ArrayList<>();

        List<BaseSelectVO> selectDeptByCompanyIds = null;
        List<BaseSelectVO> companyList = null;
        // 如果是超级管理员走单独逻辑
        if (StrUtil.equals(AuthConstants.SYS_ADMIN, AuthHelp.getLoginName())) {
            companyList = sysCompanyInnerService.selectCompanyInfoByLoginName(null);
            selectDeptByCompanyIds = selectDeptByCompanyIds(null);
            selectDeptByCompanyIds.addAll(companyList);
        } else {
            // 获取当前登陆人用户的所有公司信息
            companyList = sysCompanyInnerService.selectInnerParent();
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
        if (obj instanceof SysDeptInner sysDeptInner) {
            vo.setValue(Convert.toStr(sysDeptInner.getId()));
            vo.setLabel(sysDeptInner.getName());
            vo.setParentValue(Convert.toStr(sysDeptInner.getParentId()));
            vo.setParentValues(sysDeptInner.getParentIds());
        }
        return vo;
    }
}
