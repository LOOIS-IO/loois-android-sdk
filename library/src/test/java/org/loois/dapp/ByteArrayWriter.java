package org.loois.dapp;

import java.util.Arrays;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/20
 * Brief Desc :
 * </pre>
 */
public class ByteArrayWriter {

    private final byte[] bytes;
    private int idx = 0;

    ByteArrayWriter(final byte[] target) {
        this.bytes = target;
    }

    void concat(final byte[] bytesSource, final int length) {
        System.arraycopy(bytesSource, 0, bytes, idx, length);
        idx += length;
    }

    void concat(final byte[] bytesSource) {
        concat(bytesSource, bytesSource.length);
    }

    /**
     * ser32(i): serialize a 32-bit unsigned integer i as a 4-byte sequence, most significant byte first.
     *
     * @param i a 32-bit unsigned integer
     */
    void concatSer32(final int i) {
        concat((byte) (i >> 24));
        concat((byte) (i >> 16));
        concat((byte) (i >> 8));
        concat((byte) (i));
    }

    void concat(final byte b) {
        bytes[idx++] = b;
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
