package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisOrders extends Response<Page<Order>>{

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
