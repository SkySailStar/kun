package cn.kun.generator.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代码生成常量
 *
 * @author SkySailStar
 * @date 2023-03-08 13:45
 */
public class GeneratorConstants {

    /**
     * 代码主路径
     */
    public static final String MAIN = "src\\main\\java";

    /**
     * mapperXml 路径
     */
    public static final String MAPPER = "src\\main\\resources\\mapper";

    /**
     * 父类公共字段
     */
    public static final List<String> COMMON_COLUMNS = new ArrayList<>(Arrays.asList("id", "create_by", "create_name", "create_date", "update_by", "update_name", "update_date", "del_flag", "remarks", "remark1", "remark2", "remark3", "remark4", "remark5"));

    /**
     * 注释时间格式
     */
    public static final String NOTE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * 服务类名称格式
     */
    public static final String SERVICE_NAME_FORMAT = "%sService";

    /**
     * 逻辑删除字段名（数据库）
     */
    public static final String LOGIC_DELETE_COLUMN_NAME = "del_flag";

    /**
     * 逻辑删除属性名（实体）
     */
    public static final String LOGIC_DELETE_PROPERTY_NAME = "delFlag";

    /**
     * 创建时间字段名（数据库）
     */
    public static final String CREATE_DATE = "create_date";

    /**
     * 更新时间字段名（数据库）
     */
    public static final String UPDATE_DATE = "update_date";

    /**
     * 选择全部表标识
     */
    public static final String ALL_TABLE_FLAG = "all";

    /**
     * 数据表实体类路径
     */
    public static final String ENTITY_PATH = "entity.po";

    /**
     * 数据库地址
     */
    public static final String DATABASE_URL = "jdbc:mysql://49.4.79.165:33306/";

    /**
     * 数据库账号
     */
    public static final String DATABASE_USERNAME = "root";

    /**
     * 数据库密码
     */
    public static final String DATABASE_PASSWORD = "robot@db";
}