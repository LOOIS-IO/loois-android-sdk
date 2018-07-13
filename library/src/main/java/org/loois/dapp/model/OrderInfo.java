package org.loois.dapp.model;


public class OrderInfo {

    public String address;
    public boolean isSell;
    public String tokenA;
    public String tokenB;
    public String orderTokenSum;
    public String currentCoinNum;
    public String lrcFee;
    public String protocolS;
    public String tokenS;

    public String amountS;
    public String amountB;

    public String protocolB;
    public String decimalS;
    public String decimalB;

    //Order validate duration, in seconds
    public long validateDuration;
    public String tokenPrice;
    public String lowestOrderFee;
    //If eth network is block and gas fee is high, tag it.
    public boolean showHighFeeTips;

}
