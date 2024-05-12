package cn.kun.base.core.data.util;

import cn.kun.base.core.global.config.GlobalConfig;
import cn.kun.base.core.global.util.date.LocalDateTimeHelp;
import cn.kun.base.core.global.util.str.StrHelp;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 序列号工具类<br>
 * 获取序列号。当前系统中，一定并发下保证唯一
 *
 * @author lujun
 */
public class SeqHelp {

    /**
     * 递增数值位数
     */
    private static final int SEQ_LENGTH = 4;

    /**
     * 通用序列类型
     */
    private static final String COMMON_SEQ_TYPE = "COMMON";

    /**
     * 通用序列数
     */
    private static final AtomicInteger COMM_SEQ = new AtomicInteger(1);

    /**
     * 数据上传序列
     */
    private static final String UPLOAD_SEQ_TYPE = "UPLOAD";

    /**
     * 数据上传序列数
     */
    private static final AtomicInteger UPLOAD_SEQ = new AtomicInteger(1);

    /**
     * Description: 获取通用序列号
     *
     * @return 序列号 ：yyyyMMddHHmmssSSS + 机器标识 + length长度循环递增字符串
     * @author lujun
     * @date 2022/12/15 13:54
     **/
    public static String getSeq() {
        return getSeq(COMMON_SEQ_TYPE);
    }


    /**
     * Description:根据序列类型获取序列号
     *
     * @param seqType 序列类型，为空时默认为通用序列
     * @return 序列号：yyyyMMddHHmmssSSS + 机器标识 + length长度循环递增字符串
     * @author lujun
     * @date 2022/12/15 15:07
     **/
    public static String getSeq(String seqType) {
        
        //默认为通用序列
        AtomicInteger atomicInteger = COMM_SEQ;
        if (UPLOAD_SEQ_TYPE.equals(seqType)) {
            //数据上传序列
            atomicInteger = UPLOAD_SEQ;
        }
        return getSeq(atomicInteger, SEQ_LENGTH);
    }

    /**
     * Description: 获取序列号
     *
     * @param atomicInteger 序列数
     * @param length        循环递增数据长度，小于等于0时，默认为3位
     * @return 序列号 ：yyyyMMddHHmmssSSS + 机器标识 + length长度循环递增字符串
     * @author lujun
     * @date 2022/12/15 15:09
     **/
    public static String getSeq(AtomicInteger atomicInteger, int length) {
        
        // 时间戳
        String timeStr = LocalDateTimeHelp.nowStr(LocalDateTimeHelp.YYYYMMDDHHMMSS);
        // 机器码
        Integer machineCode = GlobalConfig.getMachineCode();
        // 序列数
        String seq = getSeqNo(atomicInteger, length <= 0 ? SEQ_LENGTH : length);
        return timeStr + machineCode + seq;
    }

    /**
     * 获取序列数
     *
     * @return 序列数
     **/
    public synchronized static String getSeqNo() {
        
        // 先取值再+1
        int value = COMM_SEQ.getAndIncrement();
        // 如果更新后值>=10 的 (length)幂次方则重置为1
        int maxSeq = (int) Math.pow(10, SEQ_LENGTH);
        if (COMM_SEQ.get() >= maxSeq) {
            COMM_SEQ.set(1);
        }
        // 转字符串，用0左补齐、
        return StrHelp.padl(value, SEQ_LENGTH);
    }

    /**
     * Description: 获取序列数
     *
     * @param atomicInteger 序列数
     * @param length        循环递增数据长度
     * @return 序列数
     * @author lujun
     * @date 2022/12/15 15:12
     **/
    private synchronized static String getSeqNo(AtomicInteger atomicInteger, int length) {
        
        // 先取值再+1
        int value = atomicInteger.getAndIncrement();

        // 如果更新后值>=10 的 (length)幂次方则重置为1
        int maxSeq = (int) Math.pow(10, length);
        if (atomicInteger.get() >= maxSeq) {
            atomicInteger.set(1);
        }
        // 转字符串，用0左补齐、
        return StrHelp.padl(value, length);
    }

}