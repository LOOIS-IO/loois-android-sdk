package org.loois.dapp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.reactivex.annotations.Nullable;

/**
 * jbw
 */
public class BigDecimalUtil {
    private static final int DEF_DIV_SCALE = 8; //这个类不能实例化

    private BigDecimalUtil() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2) {
        return add(v1, v2, DEF_DIV_SCALE, RoundingMode.HALF_UP);
    }


    public static String add(String v1, String v2, int scale, RoundingMode mode) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bigDecimal = b1.add(b2).setScale(DEF_DIV_SCALE, mode);
        return bigDecimal.toPlainString();
    }


    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2) {
        if (verifyNPE(v1, v2)) return "0";

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal subtract = b1.subtract(b2);
        return subtract.toPlainString();
    }


    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal subDecimal(String v1, String v2) {
        if (verifyNPE(v1, v2)) return new BigDecimal("0");

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2) {
        if (verifyNPE(v1, v2)) return "0";
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal obj = b1.multiply(b2).setScale(DEF_DIV_SCALE, RoundingMode.DOWN);
        String s1 = obj.toPlainString();
//        String s = String.valueOf(obj);
        return s1;
    }

    private static boolean verifyNPE(String v1, String v2) {
        if (isEmpty(v1) || isEmpty(v2)|| ".".equals(v1) || ".".equals(v2)) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(@Nullable CharSequence str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    public static BigDecimal mulByDecimal(String v1, String v2) {
        if (verifyNPE(v1, v2)) return new BigDecimal("0");
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal obj = b1.multiply(b2).setScale(DEF_DIV_SCALE, RoundingMode.DOWN);
        return obj;
    }


    public static String mul(String v1, String v2, int scale) {
        if (verifyNPE(v1, v2)) return "0";
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal obj = b1.multiply(b2).setScale(scale, RoundingMode.DOWN);
        String s = obj.toPlainString();
        return s;
    }


    public static String mul(String v1, String v2, int scale, RoundingMode mode) {
        if (verifyNPE(v1, v2)) return "0";
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal obj = b1.multiply(b2).setScale(scale, mode);
        String s = obj.toPlainString();
        return s;
    }

    //   {@code 1} if {@code this > val}, {@code -1} if {@code this < val},
//            *         {@code 0} if {@code this == val}.
    public static int compare(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }


    public static boolean isEqualsZero(String v1) {
        BigDecimal b1 = new BigDecimal(v1);
        int i = b1.compareTo(new BigDecimal(0));
        if (i == 0) {
            return true;
        }
        return false;
    }


    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String muls(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bigDecimal = b1.multiply(b2).setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
//        String s = String.valueOf(bigDecimal.doubleValue());
        String s = bigDecimal.toPlainString();
        return s;
    }


    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        if (verifyNPE(v1, v2)) return "0";
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal divide = b1.divide(b2, scale, RoundingMode.DOWN);
        String s = divide.toPlainString();
        return s;
    }

    public static String div(String v1, String v2, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, roundingMode).toPlainString();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
