package cn.kun.base.generate;

import cn.hutool.core.util.StrUtil;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码快速生成
 *
 * @author 廖航
 */
public class CodeGenerate {

    /**
     * 作者名
     */
    public static final String AUTHOR = "廖航";
    
    /**
     * 生成的表名（多个表用英文逗号分隔）
     */
    private static final String TABLES = "process_instance";

    /**
     * 业务名（一般取下划线分割的第一个单词）
     */
    private static final String BUSINESS = "process";

    /**
     * 项目所在目录
     */
    private static final String PROJECT = "D:\\Project\\SEVNCE\\sevnce-cloud-pop";
    
    /* ------------------------以下为固定配置，非必要不更改--------------------------- */
    
    /**
     * 数据库地址
     */
    public static final String DATABASE_URL = "jdbc:mysql://8.137.108.163:45100";

    /**
     * 数据库
     */
    private static final String DATABASE = "sevnce_pop";

    /**
     * 数据库账号
     */
    public static final String DATABASE_USERNAME = "root";

    /**
     * 数据库密码
     */
    public static final String DATABASE_PASSWORD = "robot@db";
    
    /**
     * 模块名
     */
    private static final String MODEL = "sevnce-modules\\sevnce-pop";

    /**
     * 包名
     */
    private static final String PACKAGE = "com.sevnce.pop";

    /**
     * 公共包
     */
    public static final String BASE = "base";

    /**
     * 代码主路径
     */
    public static final String MAIN = "src\\main\\java";

    /**
     * mapperXml 路径
     */
    public static final String MAPPER_XML_PATH = "src\\main\\resources\\mapper";

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
    public static final String CREATE_DATE = "create_time";

    /**
     * 更新时间字段名（数据库）
     */
    public static final String UPDATE_DATE = "update_time";

    /**
     * 控制层
     */
    public static final String CONTROLLER = "controller";

    /**
     * 服务层
     */
    public static final String SERVICE = "service";

    /**
     * 服务层实现类
     */
    public static final String SERVICE_IMPL = "service.impl";

    /**
     * 持久层
     */
    public static final String MAPPER = "mapper";

    /**
     * 实体类
     */
    public static final String ENTITY = "domain";

    /**
     * DTO
     */
    public static final String DTO = "dto";

    /**
     * VO
     */
    public static final String VO = "vo";

    /**
     * 父类公共字段
     */
    public static final List<String> COMMON_COLUMNS = new ArrayList<>(Arrays.asList(
            "create_by",
            "create_time",
            "update_by",
            "update_time"
    ));
    
    public static void main(String[] args) {

        // 快速代码生成
        FastAutoGenerator
                /* 数据库配置 */
                .create(
                        StrUtil.format("{}/{}", DATABASE_URL, DATABASE),
                        DATABASE_USERNAME, 
                        DATABASE_PASSWORD)
                /* 全局配置 */
                .globalConfig(builder -> builder
                        // 禁止打开输出目录
                        .disableOpenDir()
                        // 指定输出目录（默认值: windows:D:// linux or mac : /tmp）
                        .outputDir(PROJECT + File.separator + MODEL + File.separator + MAIN)
                        // 开启 SpringDoc 模式
                        .enableSpringdoc()
                        // 作者名
                        .author(AUTHOR)
                        // 时间策略（默认值: DateType.TIME_PACK，java8 新的时间类型）
                        .dateType(DateType.ONLY_DATE)
                        // 注释日期（默认值: yyyy-MM-dd）
                        .commentDate(NOTE_TIME_FORMAT))
                /* 包配置 */
                .packageConfig(builder -> builder
                        // 包名
                        .parent(PACKAGE)
                        // 业务名
                        .moduleName(BUSINESS)
                        // 控制层路径
                        .controller(CONTROLLER)
                        // 服务层路径
                        .service(SERVICE)
                        // 服务层实现类路径
                        .serviceImpl(SERVICE_IMPL)
                        // 持久层路径
                        .mapper(MAPPER)
                        // 数据表实体类路径
                        .entity(ENTITY)
                        // mapperXml 生成路径
                        .pathInfo(Collections.singletonMap(OutputFile.xml, StrUtil.format("{}{}{}{}{}", PROJECT, File.separator, MODEL, File.separator, MAPPER_XML_PATH))))
                /* 策略配置 */
                .strategyConfig(builder -> builder
                        // 设置需要生成的表名
                        .addInclude(TABLES)
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
                        .formatServiceFileName(SERVICE_NAME_FORMAT)
                        // 实体类策略
                        .entityBuilder()
                                // 设置父类
                                .superClass(BaseEntity.class)
                                // 添加父类公共字段
                                .addSuperEntityColumns(COMMON_COLUMNS)
                                // 开启 lombok 模型
                                .enableLombok()
                                // 开启生成实体时生成字段注解
                                .enableTableFieldAnnotation()
                                // 逻辑删除字段名（数据库）
                                .logicDeleteColumnName(LOGIC_DELETE_COLUMN_NAME)
                                // 逻辑删除属性名（实体）
                                .logicDeletePropertyName(LOGIC_DELETE_PROPERTY_NAME)
                                // 创建时间填充
                                .addTableFills(new Column(CREATE_DATE, FieldFill.INSERT))
                                // 更新时间填充
                                .addTableFills(new Column(UPDATE_DATE, FieldFill.INSERT_UPDATE))
                                // 是否允许覆盖现存文件
                                .enableFileOverride())
                /* 模板配置 */
                .templateConfig(builder -> builder
                        .entity("/templates/generate/entity.java.vm")
                        .service("/templates/generate/service.java.vm")
                        .serviceImpl("/templates/generate/serviceImpl.java.vm")
                        .mapper("/templates/generate/mapper.java.vm")
                        .xml("/templates/generate/mapper.xml.vm")
                        .controller("/templates/generate/controller.java.vm")
                )
                /* 自定义配置 */
                .injectionConfig(consumer -> {
                    // 参数配置
                    Map<String, Object> customMap = new HashMap<>();
                    // 公共包
                    customMap.put("base", StrUtil.format("{}.{}", PACKAGE, BASE));
                    // 公共实体类
                    customMap.put("baseEntity", StrUtil.format("{}.{}.{}", PACKAGE, BASE, ENTITY));
                    // 公共DTO
                    customMap.put("baseDto", StrUtil.format("{}.{}.{}.{}", PACKAGE, BASE, ENTITY, DTO));
                    // 公共VO
                    customMap.put("baseVo", StrUtil.format("{}.{}.{}.{}", PACKAGE, BASE, ENTITY, VO));
                    // 业务包
                    customMap.put("business", StrUtil.format("{}.{}", PACKAGE, BUSINESS));
                    // 业务服务层
                    customMap.put("service", StrUtil.format("{}.{}.{}", PACKAGE, BUSINESS, SERVICE));
                    // 业务DTO
                    customMap.put("dto", StrUtil.format("{}.{}.{}.{}", PACKAGE, BUSINESS, ENTITY, DTO));
                    // 业务VO
                    customMap.put("vo", StrUtil.format("{}.{}.{}.{}", PACKAGE, BUSINESS, ENTITY, VO));
                    consumer.customMap(customMap);
                    // 模板配置
                    List<CustomFile> customFiles = new ArrayList<>();
                    customFiles.add(new CustomFile.Builder()
                            .packageName(StrUtil.format("{}.{}", ENTITY, DTO))
                            .fileName("SaveDTO.java")
                            .templatePath("/templates/generate/dto.save.java.vm")
                            .build());
                    customFiles.add(new CustomFile.Builder()
                            .packageName(StrUtil.format("{}.{}", ENTITY, DTO))
                            .fileName("PageDTO.java")
                            .templatePath("/templates/generate/dto.page.java.vm")
                            .build());
                    customFiles.add(new CustomFile.Builder()
                            .packageName(StrUtil.format("{}.{}", ENTITY, VO))
                            .fileName("DetailVO.java")
                            .templatePath("/templates/generate/vo.detail.java.vm")
                            .build());
                    customFiles.add(new CustomFile.Builder()
                            .packageName(StrUtil.format("{}.{}", ENTITY, VO))
                            .fileName("PageVO.java")
                            .templatePath("/templates/generate/vo.page.java.vm")
                            .build());
                    consumer.customFile(customFiles);
                })
                // 模板引擎
                .templateEngine(new VelocityTemplateEngine())
                // 执行
                .execute();
    }
}