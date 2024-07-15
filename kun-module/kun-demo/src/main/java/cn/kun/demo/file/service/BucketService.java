package cn.kun.demo.file.service;

import cn.kun.demo.file.entity.vo.BucketVO;

import java.util.List;

/**
 * 桶-服务层
 *
 * @author 天航星
 * @date 2023-01-13 10:03
 */
public interface BucketService {

    /**
     * 是否存在
     * @param name 名称
     * @return 结果
     */
    boolean has(String name) throws Exception;

    /**
     * 创建
     * @param name 名称
     */
    void create(String name) throws Exception;

    /**
     * 删除
     * @param name 名称
     */
    void del(String name) throws Exception;

    /**
     * 获取全部
     * @return 全部
     */
    List<BucketVO> getAll() throws Exception;

}
