package org.loois.dapp.protocol.core.params;

public class OrderParams {

    public String owner;

    public String orderHash;

    public String status;

    public String side;

    public String delegateAddress;

    public String market;

    public int pageIndex;

    public int pageSize;

    public String orderType;

    public OrderParams(String owner, String orderHash, String status, String side,
                       String market, int pageIndex, int pageSize, String orderType, String delegateAddress) {
        this.owner = owner;
        this.orderHash = orderHash;
        this.status = status;
        this.side = side;
        this.market = market;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.orderType = orderType;
        this.delegateAddress = delegateAddress;
    }
}