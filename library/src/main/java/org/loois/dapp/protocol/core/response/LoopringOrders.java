package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LoopringOrders extends Response<OrderPage<Order>>{

    public List<Order> getOrders() {
        return getResult().data;
    }

    public int getPageIndex() {
        return getResult().pageIndex;
    }

    public int getPageSize() {
        return getResult().pageSize;
    }

    public int getTotal() {
        return getResult().total;
    }
}
