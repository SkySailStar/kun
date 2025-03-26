package cn.kun.dispatch.job.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import cn.kun.base.api.service.system.DictService;
import cn.kun.base.core.global.constant.ErrorCodeConstants;
import cn.kun.base.core.global.constant.HttpStatusConstants;
import cn.kun.base.core.global.constant.dict.type.DispatchDictTypeConstants;
import cn.kun.base.core.global.exception.BusinessException;
import cn.kun.base.core.global.util.obj.ObjUtils;
import cn.kun.base.core.global.util.str.StrUtils;
import cn.kun.base.api.entity.dispatch.dto.ExecutorPageDTO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorDetailVO;
import cn.kun.base.api.entity.dispatch.vo.ExecutorPageVO;
import cn.kun.base.job.service.XxlJobService;
import cn.kun.dispatch.job.service.ExecutorService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * xxl-job 执行器-服务层实现类
 *
 * @author 廖航
 * @date 2023-06-06 10:27
 */
@Slf4j
@Service
public class ExecutorServiceImpl implements ExecutorService {
    
    @Resource
    private XxlJobService xxlJobService;
    
    @Resource
    private DictService baseDictService;
    
    @Override
    public Page<ExecutorPageVO> page(ExecutorPageDTO dto) {

        log.info("执行器-分页：{}", dto);
        // 分页数据
        Map<String, Object> resultMap = xxlJobService.pageExecutor(dto);
        // 失败情况
        if (StrUtils.equals(Convert.toStr(resultMap.get("code")), HttpStatusConstants.ERROR)) {
            log.warn("定时任务-分页-失败：{}", Convert.toStr(resultMap.get("msg")));
            throw new BusinessException(ErrorCodeConstants.QUERY_FAIL, "定时任务-分页-失败：" + Convert.toStr(resultMap.get("msg")));
        }
        // 定义返回值
        Page<ExecutorPageVO> voPage = Page.of(dto.getPageNo(), dto.getPageSize());
        voPage.setTotal(Convert.toInt(resultMap.get("recordsTotal")));
        if (resultMap.get("data") instanceof List<?> list) {
            voPage.setRecords(list.stream().map(this::cast).toList());
            return voPage;
        }
        return null;
    }

    @Override
    public ExecutorDetailVO detail(Integer id) {

        log.info("执行器-详情：{}", id);
        if (ObjUtils.isNull(id)) {
            log.warn("执行器-详情：主键不能为空");
            throw new BusinessException(ErrorCodeConstants.NULL, "执行器-详情：主键不能为空");
        }
        ReturnT<?> returnT = xxlJobService.detailExecutor(id);
        if (ObjUtil.isNull(returnT) || returnT.getCode() == ReturnT.FAIL_CODE) {
            log.warn("执行器-详情：失败");
            throw new BusinessException(ErrorCodeConstants.QUERY_FAIL, "执行器-详情：失败");
        }
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(returnT.getContent()));
        ExecutorDetailVO vo = new ExecutorDetailVO();
        // 应用编码
        vo.setAppCode(Convert.toStr(jsonObject.get("appname")));
        // 应用名称
        vo.setAppName(Convert.toStr(jsonObject.get("title")));
        // 执行器地址类型
        String addressType = Convert.toStr(jsonObject.get("addressType"));
        vo.setAddressType(addressType);
        // 执行器地址类型名称
        vo.setAddressTypeName(baseDictService.getLabel(DispatchDictTypeConstants.ADDRESS_TYPE, addressType));
        // 执行器地址列表，多地址逗号分隔
        vo.setAddressList(Convert.toStr(jsonObject.get("addressList")));
        // 更新时间
        vo.setUpdateTime(Convert.toLocalDateTime(jsonObject.get("updateTime")));
        return vo;
    }

    /**
     * 转换
     * @param obj 对象
     * @return 执行器-分页-返回值
     */
    private ExecutorPageVO cast(Object obj) {

        JSONObject json = JSON.parseObject(JSON.toJSONString(obj));
        ExecutorPageVO vo = new ExecutorPageVO();
        // 主键
        vo.setId(Convert.toInt(json.get("id")));
        // 应用编码
        vo.setAppCode(Convert.toStr(json.get("appname")));
        // 应用名称
        vo.setAppName(Convert.toStr(json.get("title")));
        // 执行器地址类型
        String addressType = Convert.toStr(json.get("addressType"));
        vo.setAddressType(addressType);
        // 执行器地址类型名称
        vo.setAddressTypeName(baseDictService.getLabel(DispatchDictTypeConstants.ADDRESS_TYPE, addressType));
        // 执行器地址列表，多地址逗号分隔
        vo.setAddressList(Convert.toStr(json.get("addressList")));
        // 更新时间
        vo.setUpdateTime(Convert.toLocalDateTime(json.get("updateTime")));
        return vo;
    }
    
}
