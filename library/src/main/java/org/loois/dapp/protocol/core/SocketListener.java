package org.loois.dapp.protocol.core;

import org.loois.dapp.protocol.core.socket.SocketBalance;
import org.loois.dapp.protocol.core.socket.SocketDepth;
import org.loois.dapp.protocol.core.socket.SocketMarketCap;
import org.loois.dapp.protocol.core.socket.SocketPendingTx;

public class SocketListener {

    public void onBalance(SocketBalance result) {
    }

    public void onPendingTx(SocketPendingTx result) {
    }

    public void onMarketCap(SocketMarketCap result) {
    }

    public void onDepth(SocketDepth socketDepth) {

    }
}
