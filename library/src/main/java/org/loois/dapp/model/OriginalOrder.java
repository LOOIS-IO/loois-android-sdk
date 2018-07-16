package org.loois.dapp.model;

import java.io.Serializable;

public class OriginalOrder implements Serializable {
    /**
     * protocol : 0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78
     * delegateAddress : 0x17233e07c67d086464fD408148c3ABB56245FA64
     * address : 0x2915A80711C808723a188C61b3C27F9e0387e9A2
     * hash : 0x9e73ff615d0d0898dd91cdc9505fb7d90d5fa99e67cad625e0fc1e1c9f5e7369
     * tokenS : WETH
     * tokenB : VITE
     * amountS : 0x377f8ecbc736000
     * amountB : 0x3aa7c433517ca80000
     * validSince : 0x5aea9a10
     * validUntil : 0x5aebeb90
     * lrcFee : 0x4fefa17b7240000
     * buyNoMoreThanAmountB : true
     * marginSplitPercentage : 0x32
     * v : 0x1b
     * r : 0x39b21b3dde2cfcff38c145b75ecfe23a9df7cb661336d2f3201caec68d51e1c7
     * s : 0x69d56185a6d8184d568caec5a9e66444424ae0850514c16ab780a57cb0f2c8fe
     * walletAddress : 0xb94065482Ad64d4c2b9252358D746B39e820A582
     * authAddr : 0xfFc5Beb9e5A14b5FdEE17C2312A88775C6d77756
     * authPrivateKey : 0x562439de4737d94a72d759eaa45a2018f46bad3ad8b0a319d6bc7920c129ff90
     * market : VITE-WETH
     * side : buy
     * createTime : 1525324316
     */

    public String protocol;
    public String delegateAddress;
    public String address;
    public String hash;
    public String tokenS;
    public String tokenB;
    public String amountS;
    public String amountB;
    public String validSince;
    public String validUntil;
    public String lrcFee;
    public boolean buyNoMoreThanAmountB;
    public String marginSplitPercentage;
    public String v;
    public String r;
    public String s;
    public String walletAddress;
    public String authAddr;
    public String authPrivateKey;
    public String market;
    public String side;
    public int createTime;


    public boolean isBuySide(){
        return "buy".equalsIgnoreCase(side);
    }

    public boolean isBuyLRC(){
        return "lrc".equalsIgnoreCase(tokenB);
    }

    public boolean isSellLRC(){
        return "lrc".equalsIgnoreCase(tokenS);
    }


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getDelegateAddress() {
        return delegateAddress;
    }

    public void setDelegateAddress(String delegateAddress) {
        this.delegateAddress = delegateAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTokenS() {
        return tokenS;
    }

    public void setTokenS(String tokenS) {
        this.tokenS = tokenS;
    }

    public String getTokenB() {
        return tokenB;
    }

    public void setTokenB(String tokenB) {
        this.tokenB = tokenB;
    }

    public String getAmountS() {
        return amountS;
    }

    public void setAmountS(String amountS) {
        this.amountS = amountS;
    }

    public String getAmountB() {
        return amountB;
    }

    public void setAmountB(String amountB) {
        this.amountB = amountB;
    }

    public String getValidSince() {
        return validSince;
    }

    public void setValidSince(String validSince) {
        this.validSince = validSince;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getLrcFee() {
        return lrcFee;
    }

    public void setLrcFee(String lrcFee) {
        this.lrcFee = lrcFee;
    }

    public boolean isBuyNoMoreThanAmountB() {
        return buyNoMoreThanAmountB;
    }

    public void setBuyNoMoreThanAmountB(boolean buyNoMoreThanAmountB) {
        this.buyNoMoreThanAmountB = buyNoMoreThanAmountB;
    }

    public String getMarginSplitPercentage() {
        return marginSplitPercentage;
    }

    public void setMarginSplitPercentage(String marginSplitPercentage) {
        this.marginSplitPercentage = marginSplitPercentage;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getAuthAddr() {
        return authAddr;
    }

    public void setAuthAddr(String authAddr) {
        this.authAddr = authAddr;
    }

    public String getAuthPrivateKey() {
        return authPrivateKey;
    }

    public void setAuthPrivateKey(String authPrivateKey) {
        this.authPrivateKey = authPrivateKey;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}