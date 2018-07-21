package org.loois.dapp.protocol;

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


}
