package cn.kun.base.core.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 全局配置
 *
 * @author lujun
 */
@Component
public class GlobalConfig {

    /**
     * 机器码长度限制
     */
    private static final int MACHINE_CODE_LENGTH = 2;

    /**
     * 机器码
     */
    private static Integer machineCode;
    @Value("${sys.machineCode}")
    public void setMachineCode(Integer machineCode) {
        GlobalConfig.machineCode = machineCode;
    }

    /**
     * 获取机器码。机器码超长后，只取前几位
     * @return 机器码
     */
    public static Integer getMachineCode() {
        if (machineCode == null) {
            machineCode = 1;
        } else if (machineCode.toString().length() > MACHINE_CODE_LENGTH) {
            // 限制机器码长度
            machineCode = Integer.parseInt(machineCode.toString().substring(0, MACHINE_CODE_LENGTH));
        }
        return machineCode;
    }

}