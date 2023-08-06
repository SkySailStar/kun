package cn.kun.generator.util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.generator.constant.GeneratorConstants;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码快速生成
 *
 * @author SkySailStar
 */
public class FastGenerator {

    /**
     * 作者名
     */
    public static final String AUTHOR = "SkySailStar";
    
    /**
     * 生成的表名（多个表用英文逗号分隔，所有表输入 all）
     */
    private static final String TABLES = "warn_level";

    /**
     * 数据库
     */
    private static final String DATABASE = "rmp";

    /**
     * 项目所在目录
     */
    private static final String PROJECT = "D:\\Project\\kun\\kun-cloud";

    /**
     * 模块名
     */
    private static final String MODEL = "kun-modules\\kun-app";

    /**
     * 包名
     */
    private static final String PARENT = "cn.skysailstar.app";

    /**
     * 业务名
     */
    private static final String BUSINESS = "warn";
    
    public static void main(String[] args) {

        // 开始代码生成
        FastAutoGenerator.create(GeneratorConstants.DATABASE_URL + DATABASE, GeneratorConstants.DATABASE_USERNAME, GeneratorConstants.DATABASE_PASSWORD)
                // 全局配置
                .globalConfig(builder -> builder
                        // 禁止打开输出目录
                        .disableOpenDir()
                        // 指定输出目录（默认值: windows:D:// linux or mac : /tmp）
                        .outputDir(PROJECT + File.separator + MODEL + File.separator + GeneratorConstants.MAIN)
                        // 开启 SpringDoc 模式
                        .enableSpringdoc()
                        // 作者名
                        .author(AUTHOR)
                        // 时间策略（默认值: DateType.TIME_PACK，java8 新的时间类型）
                        .dateType(DateType.TIME_PACK)
                        // 注释日期（默认值: yyyy-MM-dd）
                        .commentDate(GeneratorConstants.NOTE_TIME_FORMAT))
                // 包配置
                .packageConfig(builder -> builder
                        // 包名
                        .parent(PARENT)
                        // 业务名
                        .moduleName(BUSINESS)
                        // 数据表实体类路径
                        .entity(GeneratorConstants.ENTITY_PATH)
                        // mapperXml 生成路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, PROJECT + File.separator + MODEL + File.separator + GeneratorConstants.MAPPER)))
                // 策略配置
                .strategyConfig(builder -> builder
                        // 设置需要生成的表名
                        .addInclude(getTables(TABLES))
                        // 控制层策略
                        .controllerBuilder()
                                // 开启驼峰转连字符
                                .enableHyphenStyle()
                                // 设置父类
                                .superClass(BaseController.class)
                                // 生成 @RestController
                                .enableRestStyle()
                        // 服务层策略
                        .serviceBuilder()
                                // 服务类名称格式
                                .formatServiceFileName(GeneratorConstants.SERVICE_NAME_FORMAT)
                        // 实体类策略
                        .entityBuilder()
                                // 开启 lombok 模型
                                .enableLombok()
                                // 开启生成实体时生成字段注解
                                .enableTableFieldAnnotation()
                                // 逻辑删除字段名（数据库）
                                .logicDeleteColumnName(GeneratorConstants.LOGIC_DELETE_COLUMN_NAME)
                                // 逻辑删除属性名（实体）
                                .logicDeletePropertyName(GeneratorConstants.LOGIC_DELETE_PROPERTY_NAME)
                                // 创建时间填充
                                .addTableFills(new Column(GeneratorConstants.CREATE_DATE, FieldFill.INSERT))
                                // 更新时间填充
                                .addTableFills(new Column(GeneratorConstants.UPDATE_DATE, FieldFill.INSERT_UPDATE))
                                // 是否允许覆盖现存文件
                                .enableFileOverride())
                // 模板配置
                .templateConfig(builder -> builder
                        .entity("/templates/entity.java.vm")
                        .service("/templates/service.java.vm")
                        .serviceImpl("/templates/serviceImpl.java.vm")
                        .mapper("/templates/mapper.java.vm")
                        .xml("/templates/mapper.xml.vm")
                        .controller("/templates/controller.java.vm")
                )
                // 执行
                .execute();
    }

    /**
     * 处理所有表的情况
     * @param tables 表名
     * @return 结果
     */
    private static List<String> getTables(String tables) {
        return GeneratorConstants.ALL_TABLE_FLAG.equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}
