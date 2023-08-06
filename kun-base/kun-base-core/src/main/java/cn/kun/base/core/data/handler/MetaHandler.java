package cn.kun.base.core.data.handler;

import cn.kun.base.core.security.util.AuthHelp;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import cn.kun.base.core.global.constant.BaseConstants;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 *
 * @author SkySailStar
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject 数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 当前登录用户名
        String loginName = AuthHelp.getLoginName();
        // 当前登录用户名称
        String name = AuthHelp.getName();
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建者
        this.setFieldValByName("createBy", loginName, metaObject);
        // 创建者名称
        this.setFieldValByName("createName", name, metaObject);
        // 创建时间
        this.setFieldValByName("createDate", now, metaObject);
        // 更新者
        this.setFieldValByName("updateBy", loginName, metaObject);
        // 更新者名称
        this.setFieldValByName("updateName", name, metaObject);
        // 更新时间
        this.setFieldValByName("updateDate", now, metaObject);
        // 删除标记
        this.setFieldValByName("delFlag", BaseConstants.NO, metaObject);
    }

    /**
     * 更新数据执行
     * @param metaObject 数据对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新者
        this.setFieldValByName("updateBy", AuthHelp.getLoginName(), metaObject);
        // 更新者名称
        this.setFieldValByName("updateName", AuthHelp.getName(), metaObject);
        // 更新时间
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
    }

}