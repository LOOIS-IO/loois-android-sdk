package org.loois.dapp.protocol.core.params;

public class CutoffParams {

    public String address;

    public String delegateAddress;

    public String blockNumber;

    public CutoffParams(String address, String delegateAddress, String blockNumber) {
        this.address = address;
        this.delegateAddress = delegateAddress;
        this.blockNumber = blockNumber;
    }
}
