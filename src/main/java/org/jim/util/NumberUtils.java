package org.jim.util;

import java.math.BigDecimal;

/**
 * 数值的工具类
 *
 * @author Jim
 */
public class NumberUtils {

    /**
     * 用于处理除零异常
     *
     * @param val
     * @return
     */
    public static boolean isZero(BigDecimal val) {
        return (val == null || val.compareTo(BigDecimal.ZERO) == 0);
    }
    public static boolean notZero(BigDecimal val) {
        return (val != null && val.compareTo(BigDecimal.ZERO) != 0);
    }


}
