package org.loois.dapp.utils;

import android.text.TextUtils;
import android.util.Log;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class HexHandleUtil {

    public static String hexDecode(String amount) {
        BigInteger bigInteger = Numeric.decodeQuantity(amount);
        return bigInteger.toString();
    }

    public static String hexEncodeFee(int num) {
        return Numeric.encodeQuantity(new BigInteger(String.valueOf(num * 10)));
    }


    public static String hexEncodeFee(String num) {
        return Numeric.encodeQuantity(new BigInteger(num));
    }


    public static String hexEncodeLrcFee(String num) {
        if (!TextUtils.isEmpty(num)) {
            String[] split = num.split("\\.");
            String first = "0";
            String end = "0";
            if (!TextUtils.isEmpty(split[0])) {
                first = Integer.toHexString(Integer.parseInt(split[0]));
            }
            if (!TextUtils.isEmpty(split[1])) {
                String s1 = "0." + split[1];
                end = intToHex(Float.parseFloat(s1));
            }
            return first + "." + end;
        }
        return "0";
    }


    private void initDate() {
        float a = (float) 0.46;
        Log.e("dddd", "initDate: _    " + Integer.toHexString(0).toUpperCase() + "." + intToHex((float) 0.125));
    }

    private static String intToHex(float n) {
        StringBuilder sb = new StringBuilder("");
        float c = n;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        boolean bb = true;
        int fff = 2; // 小数点后精确到几位
        for (int i = 0; i < fff; i++) {
            sb = sb.append(b[(int) (c * 16 / 1)]);
            c = c * 16 % 1;
            String s = c + "";
            String[] split = s.split("\\.");
            for (int i1 = 0; i1 < split[1].length(); i1++) {
                String c1 = split[1].charAt(i1) + "";
                if (c1.equals("0")) {
                    bb = true;
                } else {
                    bb = false;
                    break;
                }
            }
            if (bb) {
                break;
            }
        }
        return sb.toString();
    }

}
