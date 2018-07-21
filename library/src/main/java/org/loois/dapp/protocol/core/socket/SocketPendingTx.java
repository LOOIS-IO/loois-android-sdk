package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.Transaction;

import java.util.List;

public class SocketPendingTx extends SocketResponse<List<Transaction>>{

    public List<Transaction> getPendingTx() {
        return data;
    }
}
