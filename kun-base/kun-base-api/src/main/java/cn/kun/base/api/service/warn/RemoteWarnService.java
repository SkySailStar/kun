package cn.kun.base.api.service.warn;

import cn.kun.base.api.entity.warn.dto.SaveAssetsWarnRuleDTO;
import cn.kun.base.api.entity.warn.vo.AssetsWarnRuleDetailVO;
import cn.kun.base.core.global.constant.ServiceConstants;
import cn.kun.base.core.global.entity.BaseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author eric
 * @date 2023/4/23 10:20
 */
@FeignClient(ServiceConstants.WARN)
public interface RemoteWarnService {
    /**
     * 保存台账预警信息
     *
     * @param dto
     * @return 结果
     */
    @PostMapping("warn/rule/config/saveAssetsWarnRuleInfo")
    BaseResult<Boolean> saveAssetsWarnRuleInfo(@RequestBody SaveAssetsWarnRuleDTO dto);

    /**
     * 根据台账获取预警规则信息
     *
     * @param assetsId 台账id
     * @return 结果
     */
    @GetMapping("warn/rule/config/selectAssetsWarnInfo/{assetsId}")
    BaseResult<List<AssetsWarnRuleDetailVO>> selectAssetsWarnInfo(@PathVariable("assetsId") Long assetsId);
}