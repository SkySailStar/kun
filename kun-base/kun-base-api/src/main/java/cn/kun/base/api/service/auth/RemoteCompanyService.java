package cn.kun.base.api.service.auth;

/**
 * @author eric
 * @date 2023/3/29 15:53
 */

import cn.kun.base.api.entity.auth.vo.SysCompanyInfoVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *  远程调用公司接口
 * @author eric
 * @date 2023/3/24 17:21
 */
@FeignClient(ServiceConstants.AUTH)
public interface RemoteCompanyService {

    /**
     *  获取内部公司信息、部门信息、职位信息
     * @param companyId 公司id
     * @return
     */
    @GetMapping(value = "system/sysCompanyInner/getCompanyCacheInfoByCompanyId/{companyId}")
    BaseResult<SysCompanyInfoVO> getCompanyInnerCacheInfoByCompanyId(@PathVariable("companyId") String companyId);

    /**
     *  获取内部公司信息、部门信息、职位信息
     * @param companyId 公司id
     * @return
     */
    @GetMapping(value = "system/sysCompanyOuter/getCompanyCacheInfoByCompanyId/{companyId}")
    BaseResult<SysCompanyInfoVO> getCompanyOuterCacheInfoByCompanyId(@PathVariable("companyId") String companyId);
}