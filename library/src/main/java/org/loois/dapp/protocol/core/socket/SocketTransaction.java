package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.Transaction;

import java.util.List;

public class SocketTransaction extends SocketResponse<List<Transaction>>{

    public List<Transaction> getTransactions() {
        return data;
    }
}
