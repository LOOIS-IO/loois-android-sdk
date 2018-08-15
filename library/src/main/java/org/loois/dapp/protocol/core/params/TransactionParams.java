package org.loois.dapp.protocol.core.params;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.LooisConfig;

public class TransactionParams {
    public String owner;
    public String delegateAddress = Loois.getOptions().getDelegateAddress();
    public String thxHash;
    public String symbol;
    public String status;
    public String txType;
    public int pageIndex;
    public int pageSize;

    public TransactionParams(String owner, String thxHash, String symbol, String status, String txType, int pageIndex, int pageSize) {
        this.thxHash = thxHash;
        this.owner = owner;
        this.symbol = symbol;
        this.status = status;
        this.txType = txType;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public TransactionParams(String owner, String thxHash, String symbol, String status,
                             String txType, int pageIndex, int pageSize, String delegateAddress) {
        this.thxHash = thxHash;
        this.owner = owner;
        this.symbol = symbol;
        this.status = status;
        this.txType = txType;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.delegateAddress = delegateAddress;
    }
}