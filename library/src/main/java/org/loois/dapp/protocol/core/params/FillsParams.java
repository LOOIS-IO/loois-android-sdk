package org.loois.dapp.protocol.core.params;

public class FillsParams {

    public String market;
    public String delegateAddress;
    public String owner;
    public String orderHash;
    public String ringHash;
    public int pageIndex;
    public int pageSize;

    public FillsParams(String market, String delegateAddress, String owner, String orderHash, String ringHash, int pageIndex, int pageSize) {
        this.market = market;
        this.owner = owner;
        this.orderHash = orderHash;
        this.ringHash = ringHash;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.delegateAddress = delegateAddress;
    }

}
