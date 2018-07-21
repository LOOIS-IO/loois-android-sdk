package org.loois.dapp.protocol.core.socket;


import org.loois.dapp.protocol.Config;

public class DepthBody {

    public String market;
    public String delegateAddress = Config.DELEGATE_ADDRESS;
    public int length = 20;

    public DepthBody(String market) {
        this.market = market;
    }

    public DepthBody(String market, int length) {
        this.market = market;
        this.length = length;
    }

    public DepthBody(String market, String delegateAddress, int length) {
        this.market = market;
        this.delegateAddress = delegateAddress;
        this.length = length;
    }
}
