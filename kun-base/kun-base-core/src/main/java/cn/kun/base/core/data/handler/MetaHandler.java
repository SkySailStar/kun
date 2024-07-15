package cn.kun.base.core.data.handler;

import cn.kun.base.core.security.util.AuthUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 *
 * @author 廖航
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     * @param metaObject 数据对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        
        // 当前登录用户id
        String userId = AuthUtils.getUserId();
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 创建人
        this.setFieldValByName("createPerson", userId, metaObject);
        // 创建时间
        this.setFieldValByName("createTime", now, metaObject);
        // 更新人
        this.setFieldValByName("updatePerson", userId, metaObject);
        // 更新时间
        this.setFieldValByName("updateTime", now, metaObject);
    }

    /**
     * 更新数据执行
     * @param metaObject 数据对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        
        // 更新者
        this.setFieldValByName("updatePerson", AuthUtils.getLoginName(), metaObject);
        // 更新时间
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}