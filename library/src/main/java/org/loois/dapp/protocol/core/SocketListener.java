package org.loois.dapp.protocol.core;

import org.loois.dapp.protocol.core.socket.SocketBalance;
import org.loois.dapp.protocol.core.socket.SocketDepth;
import org.loois.dapp.protocol.core.socket.SocketLooisTickers;
import org.loois.dapp.protocol.core.socket.SocketMarketCap;
import org.loois.dapp.protocol.core.socket.SocketPendingTx;
import org.loois.dapp.protocol.core.socket.SocketTickers;

public class SocketListener {

    public void onBalance(SocketBalance result) {
    }

    public void onPendingTx(SocketPendingTx result) {
    }

    public void onMarketCap(SocketMarketCap result) {
    }

    public void onDepth(SocketDepth socketDepth) {
    }

    public void onTickers(SocketTickers socketTickers) {
    }

    public void onLooisTickers(SocketLooisTickers socketLoopringTickers) {
    }

}
