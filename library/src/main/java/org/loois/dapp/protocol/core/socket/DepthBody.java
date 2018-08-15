package org.loois.dapp.protocol.core.socket;


import org.loois.dapp.Loois;
import org.loois.dapp.protocol.LooisConfig;

public class DepthBody {

    public String market;
    public String delegateAddress = Loois.getOptions().getDelegateAddress();
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
