package org.loois.dapp.protocol;

import org.loois.dapp.protocol.core.SocketListener;

public interface LooisSocketApi {

    void onBalance(String owner);
    void offBalance();

    void onPendingTx(String owner);
    void offPendingTx();

    void onMarketCap(String currency);
    void offMarketCap();

    void onDepth(String market);
    void offDepth();

    void onTickers(String market);
    void offTickers();

    void onLooisTickers();
    void offLooisTickers();

    void registerBalanceListener(SocketListener listener);
    void removeBalanceListener(SocketListener listener);

    void registerTransactionListener(SocketListener listener);
    void removeTransactionListener(SocketListener listener);

    void registerMarketCapListener(SocketListener listener);
    void removeMarketCapListener(SocketListener listener);

    void registerDepthListener(SocketListener listener);
    void removeDepthListener(SocketListener listener);

    void registerTickersListener(SocketListener listener);
    void removeTickersListener(SocketListener listener);

    void registerLooisTickersListener(SocketListener listener);
    void removeLooisTickersListener(SocketListener listener);

}
