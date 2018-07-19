package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisTransactions extends Response<Page<Transaction>>{

    public List<Transaction> getTransactions() {
        return getResult().data;
    }

    public int getPageSize() {
        return getResult().pageSize;
    }

    public int getPageIndex() {
        return getResult().pageIndex;
    }

    public int getTotal() {
        return getResult().total;
    }

}
