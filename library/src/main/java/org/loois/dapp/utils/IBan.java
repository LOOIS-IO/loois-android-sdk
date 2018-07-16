package org.loois.dapp.utils;

import java.math.BigInteger;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/5/5
 * Brief Desc :
 * </pre>
 */
public class IBan {

    private static boolean isDirect(String ibanAddress){
        return ibanAddress.length() == 34 || ibanAddress.length() == 35;
    }

    public static String toAddress(String ibanAddress){
        if (IBan.isDirect(ibanAddress)){
            String base36 = ibanAddress.substring(4);
            BigInteger asBn = new BigInteger(base36, 36);
            return IBan.padRight(asBn.toString(16),20,'\0');
        }
        return null;
    }

    /**
     *
     * String右边补 char
     */
    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }
    /**
     *
     * String左边补 char
     */
    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }



}
