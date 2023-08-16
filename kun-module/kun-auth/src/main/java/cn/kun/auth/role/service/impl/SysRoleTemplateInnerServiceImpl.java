package cn.kun.auth.role.service.impl;

import cn.kun.auth.role.mapper.SysRoleTemplateInnerMapper;
import cn.kun.auth.role.entity.po.SysRoleTemplateInner;
import cn.kun.auth.role.service.SysRoleTemplateInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 内部角色模板表 服务实现类
 * </p>
 *
 * @author 杨忠卫
 * @date 2022-11-29 11:48
 */
@Service
public class SysRoleTemplateInnerServiceImpl extends ServiceImpl<SysRoleTemplateInnerMapper, SysRoleTemplateInner> implements SysRoleTemplateInnerService {

//    @Override
//    public IPage<RoleTemplatePageVO> page(RoleTemplatePageDTO dto) {
//        // 判断是超级用户还是普通用户
//        String userId = JwtUtil.getLoginUser().getUserInfo().getId();
//        if (!ObjUtil.equals(AuthConstants.SYS_ADMIN",userId)){
//            dto.setUserId(userId);
//        }
//        IPage<ProjectPageDTO> page = Page.of(dto.getPageNo(), dto.getPageSize());
//        // 分页查询
//        return baseMapper.selectPage(page, dto);
//    }
//
//    @Override
//    public RoleTemplateDetailVO detail(String id) {
//        // 查询详情
//        SysProject sysProject = getById(id);
//        // 复制对象
//        ProjectDetailVO vo = new ProjectDetailVO();
//        BeanUtil.copyProperties(sysProject, vo);
//        return vo;
//    }
//
//    @Override
//    public SysRoleTemplateInner add(RoleTemplateAddDTO dto) {
//        // 复制对象
//        SysProject sysProject = new SysProject();
//        BeanUtil.copyProperties(dto,sysProject);
//        // 添加对象
//        save(sysProject);
//        return sysProject;
//    }
//
//    @Override
//    public SysRoleTemplateInner edit(String id, RoleTemplateEditDTO dto) {
//        // 复制对象
//        SysProject sysProject = getById(id);
//        BeanUtil.copyProperties(dto, sysProject);
//        // 修改对象
//        updateById(sysProject);
//        return sysProject;
//    }
}
