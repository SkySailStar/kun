package cn.kun.base.api.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.kun.base.api.service.system.BaseDictService;
import cn.kun.base.api.service.system.RemoteDictService;
import cn.kun.base.core.cache.constant.SystemCacheConstants;
import cn.kun.base.core.cache.util.RedisHelp;
import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典-服务层实现类
 *
 * @author SkySailStar
 * @date 2023-03-23 17:39
 */
@SuppressWarnings("all")
@Slf4j
@Service
public class BaseDictServiceImpl implements BaseDictService {

    @Value("${spring.redis.database}")
    private int database;
    
    @Resource
    private RemoteDictService remoteDictService;
    
    @Override
    public List<BaseSelectVO> list(String type) {

        List<BaseSelectVO> voList = new ArrayList<>();
        // 如果类型为空，直接返回空列表
        if (StrUtil.isBlank(type)) {
            return voList;
        }
        // 根据类型获取列表
        Object obj = RedisHelp.getHash(SystemCacheConstants.DICT_DATA_HASH, type);
        if (ObjUtil.isNotNull(obj) && obj instanceof List list) {
            if (CollUtil.isNotEmpty(list)) {
                return list;
            }
        }
        // 缓存获取不到再调用远程服务获取
        return remoteDictService.list(type).getData();
    }

    @Override
    public String getLabel(String type, String value) {
        
        String result = StrUtil.EMPTY;
        // 如果类型或者值为空，直接返回空字符
        if (StrUtil.isBlank(type) || StrUtil.isBlank(value)) {
            return result;
        }
        // 通过类型获取字典列表
        List<BaseSelectVO> list = list(type);
        if (CollUtil.isEmpty(list)) {
            return result;
        }
        // 根据编码从字典列表中直接取值
        for (BaseSelectVO vo : list) {
            if (StrUtil.equals(vo.getValue(), value)) {
                return vo.getLabel();
            }
        }
        // 缓存获取不到再调用远程服务获取
        return remoteDictService.getLabel(type, value).getData();
    }
}
