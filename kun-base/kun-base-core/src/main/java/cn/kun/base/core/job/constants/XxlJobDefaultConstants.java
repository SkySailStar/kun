package cn.kun.base.core.job.constants;

/**
 * xxl-job默认值-常量类
 *
 * @author 廖航
 * @date 2023-06-06 17:56
 */
public class XxlJobDefaultConstants {

    /**
     * 执行器路由策略
     */
    public static final String EXECUTOR_ROUTE_STRATEGY = "FIRST";

    /**
     * 调度过期策略
     */
    public static final String MISFIRE_STRATEGY = "DO_NOTHING";

    /**
     * 阻塞处理策略
     */
    public static final String EXECUTOR_BLOCK_STRATEGY = "SERIAL_EXECUTION";

    /**
     * 任务执行超时时间
     */
    public static final int EXECUTOR_TIMEOUT = 0;

    /**
     * 失败重试次数
     */
    public static final int EXECUTOR_FAIL_RETRY_COUNT = 0;

    /**
     * GLUE备注
     */
    public static final String GLUE_REMARK = "GLUE代码初始化";

    /**
     * GLUE_JAVA示例代码
     */
    public static final String GLUE_JAVA_SOURCE = """
            package com.xxl.job.service.handler;
                        
            import com.xxl.job.core.context.XxlJobHelper;
            import com.xxl.job.core.handler.IJobHandler;
                        
            public class DemoGlueJobHandler extends IJobHandler {
                        
            	@Override
            	public void execute() throws Exception {
            		XxlJobHelper.log("XXL-JOB, Hello World.");
            	}
                        
            }
            """;

    /**
     * GLUE_SHEL示例代码
     */
    public static final String GLUE_SHELL_SOURCE = """
            #!/bin/bash
            echo "xxl-job: hello shell"
                        
            echo "${I18n.jobinfo_script_location}：$0"
            echo "${I18n.jobinfo_field_executorparam}：$1"
            echo "${I18n.jobinfo_shard_index} = $2"
            echo "${I18n.jobinfo_shard_total} = $3"
            <#--echo "参数数量：$#"
            for param in $*
            do
                echo "参数 : $param"
                sleep 1s
            done-->
                        
            echo "Good bye!"
            exit 0
            """;

    /**
     * GLUE_PYTHON示例代码
     */
    public static final String GLUE_PYTHON_SOURCE = """
            #!/usr/bin/python
            # -*- coding: UTF-8 -*-
            import time
            import sys
            
            print "xxl-job: hello python"
            
            print "${I18n.jobinfo_script_location}：", sys.argv[0]
            print "${I18n.jobinfo_field_executorparam}：", sys.argv[1]
            print "${I18n.jobinfo_shard_index}：", sys.argv[2]
            print "${I18n.jobinfo_shard_total}：", sys.argv[3]
            <#--for i in range(1, len(sys.argv)):
                time.sleep(1)
                print "参数", i, sys.argv[i]-->
            
            print "Good bye!"
            exit(0)
            <#--
            import logging
            logging.basicConfig(level=logging.DEBUG)
            logging.info("脚本文件：" + sys.argv[0])
            -->
            """;

    /**
     * GLUE_PHP示例代码
     */
    public static final String GLUE_PHP_SOURCE = """
            <?php
                        
                echo "xxl-job: hello php  \\n";
                        
                echo "${I18n.jobinfo_script_location}：$argv[0]  \\n";
                echo "${I18n.jobinfo_field_executorparam}：$argv[1]  \\n";
                echo "${I18n.jobinfo_shard_index} = $argv[2]  \\n";
                echo "${I18n.jobinfo_shard_total} = $argv[3]  \\n";
                        
                echo "Good bye!  \\n";
                exit(0);
                        
            ?>
            """;

    /**
     * GLUE_NODEJS示例代码
     */
    public static final String GLUE_NODEJS_SOURCE = """
            #!/usr/bin/env node
            console.log("xxl-job: hello nodejs")
            
            var arguments = process.argv
            
            console.log("${I18n.jobinfo_script_location}: " + arguments[1])
            console.log("${I18n.jobinfo_field_executorparam}: " + arguments[2])
            console.log("${I18n.jobinfo_shard_index}: " + arguments[3])
            console.log("${I18n.jobinfo_shard_total}: " + arguments[4])
            <#--for (var i = 2; i < arguments.length; i++){
                console.log("参数 %s = %s", (i-1), arguments[i]);
            }-->
            
            console.log("Good bye!")
            process.exit(0)
            """;

    /**
     * GLUE_POWERSHELL示例代码
     */
    public static final String GLUE_POWERSHELL_SOURCE = """
            Write-Host "xxl-job: hello powershell"

            Write-Host "${I18n.jobinfo_script_location}: " $MyInvocation.MyCommand.Definition
            Write-Host "${I18n.jobinfo_field_executorparam}: "
                if ($args.Count -gt 2) { $args[0..($args.Count-3)] }
            Write-Host "${I18n.jobinfo_shard_index}: " $args[$args.Count-2]
            Write-Host "${I18n.jobinfo_shard_total}: " $args[$args.Count-1]
            
            Write-Host "Good bye!"
            exit 0
            """;
}
