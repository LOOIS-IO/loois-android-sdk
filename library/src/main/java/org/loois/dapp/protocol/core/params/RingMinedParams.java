package org.loois.dapp.protocol.core.params;

public class RingMinedParams {


    public String ringHash;

    public String delegateAddress;

    public int pageIndex;

    public int pageSize;

    public RingMinedParams(String ringHash, String delegateAddress, int pageIndex, int pageSize) {
        this.ringHash = ringHash;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.delegateAddress = delegateAddress;
    }

}
