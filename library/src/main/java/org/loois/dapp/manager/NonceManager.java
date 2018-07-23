package org.loois.dapp.manager;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigInteger;

//TODO: Determine if use it.
public class NonceManager {

    private static final String FILE_NAME = "loois";

    private void updateNonce(Context context, String address, BigInteger nonce) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(address, nonce.longValue()).apply();
    }

    private BigInteger getNonce(Context context, String address) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        long nonce = sharedPreferences.getLong(address, 0);
        return BigInteger.valueOf(nonce);
    }

    public void increase(Context context, String address) {
        BigInteger nonce = getNonce(context, address);
        BigInteger add = nonce.add(new BigInteger("1"));
        updateNonce(context, address, add);
    }

    public BigInteger getActualNonce(Context context, String address, BigInteger serverNonce) {
        return BigInteger.valueOf(0);
    }
}
