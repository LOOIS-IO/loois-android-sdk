package org.loois.dapp;

import org.junit.Test;
import org.web3j.utils.Numeric;

import java.util.Arrays;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/18
 * Brief Desc :
 * </pre>
 */
public class UtilTest {

    String mnemonic = "traffic buddy void three pink bronze radar rule science grab orbit surge";

    @Test
    public void testHead32(){
        byte[] bytes = mnemonic.getBytes();
        byte[] result = head32(bytes);
        for (byte b : result) {
            System.out.print((b&0xff)+",");
        }
    }

    @Test
    public void testTail32(){
        byte[] bytes = mnemonic.getBytes();
        byte[] result = tail32(bytes);
        for (byte b : result) {
            System.out.print((b&0xff)+",");
        }
    }

    @Test
    public void testConcat(){
        final byte[] privateKey = new byte[82];
        byte[] b = new byte[3];
        b[0] = 10;
        b[1] = 11;
        b[2] = 12;
        ByteArrayWriter writer = new ByteArrayWriter(privateKey);
        writer.concat((byte) 1);
        writer.concat(b);
        writer.concatSer32(5);
        String hex = Numeric.toHexString(privateKey);
        System.out.println(hex);
        System.out.println(hex.length());
    }


    static byte[] tail32(final byte[] bytes64) {
        final byte[] ir = new byte[bytes64.length - 32];
        System.arraycopy(bytes64, 32, ir, 0, ir.length);
        return ir;
    }

    static byte[] head32(final byte[] bytes64) {
        return Arrays.copyOf(bytes64, 32);
    }
}
