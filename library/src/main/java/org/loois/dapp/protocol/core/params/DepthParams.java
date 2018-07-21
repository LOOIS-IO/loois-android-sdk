package org.loois.dapp.protocol.core.params;

public class DepthParams {

    public String delegateAddress;

    public String market;

    public int length;

    public DepthParams(String market, int length, String delegateAddress) {
        this.market = market;
        this.length = length;
        this.delegateAddress = delegateAddress;
    }
}
