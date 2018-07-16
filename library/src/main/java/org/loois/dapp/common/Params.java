package org.loois.dapp.common;

import java.math.BigInteger;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/5/11
 * Brief Desc :
 * </pre>
 */
public interface Params {


    int DEFAULT_GAS = 25;


    interface Abi {

        String transfer = "0xa9059cbb"; // READY

        String cancelOrder = "0x8c59f7ca"; // READY

        String cancelAllOrders = "0xbd545f53"; // READY

        String deposit = "0xd0e30db0"; // READY

        String withdraw = "0x2e1a7d4d"; // READY

        String cancelAllOrdersByTradingPair = "0x8865cbd6"; // READY

        String bind = "0xca02620a";
    }

    interface GasLimit {

        BigInteger eth_transfer = BigInteger.valueOf(24000);
        BigInteger token_transfer = BigInteger.valueOf(60000);
//        BigInteger token_transfer = BigInteger.valueOf(200000);
        BigInteger approve = BigInteger.valueOf(200000);
        BigInteger withdraw = BigInteger.valueOf(200000);
        BigInteger deposit = BigInteger.valueOf(200000);
        BigInteger exchange = BigInteger.valueOf(60000);
        BigInteger cancelOrder = BigInteger.valueOf(200000);
        BigInteger cancelAllOrders = BigInteger.valueOf(200000);
        BigInteger cancelOrderByTokenPair = BigInteger.valueOf(200000);
        BigInteger bind = BigInteger.valueOf(200000);
        BigInteger submitRing = BigInteger.valueOf(500000);
    }
}
