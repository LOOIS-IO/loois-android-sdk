package org.loois.dapp.protocol.core.response;

public class Fill {


    /**
     * protocol : 0x4c44d51CF0d35172fCe9d69e2beAC728de980E9D
     * owner : 0x66727f5DE8Fbd651Dc375BB926B16545DeD71EC9
     * ringIndex : 100
     * createTime : 1512631182
     * ringHash : 0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5
     * txHash : 0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5
     * orderHash : 0x2794f8e4d2940a2695c7ecc68e10e4f479b809601fa1d07f5b4ce03feec289d5
     * amountS : 0xde0b6b3a7640000
     * amountB : 0xde0b6b3a7640001
     * tokenS : WETH
     * tokenB : COSS
     * lrcReward : 0xde0b6b3a7640000
     * lrcFee : 0xde0b6b3a7640000
     * splitS : 0xde0b6b3a7640000
     * splitB : 0x0
     * market : LRC-WETH
     */

    public String protocol;
    public String owner;
    public int ringIndex;
    public long createTime;
    public String ringHash;
    public String txHash;
    public String orderHash;
    public String amountS;
    public String amountB;
    public String tokenS;
    public String tokenB;
    public String lrcReward;
    public String lrcFee;
    public String splitS;
    public String splitB;
    public String market;
}
