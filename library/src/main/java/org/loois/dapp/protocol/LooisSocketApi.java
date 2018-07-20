package org.loois.dapp.protocol;

public interface LooisSocketApi {

    void onBalance(String owner);
    void offBalance();

    void onPendingTx(String owner);
    void offPendingTx();

    void onMarketCap(String currency);
    void offMarketCap();
//
//    void onDepth();
//    void offDepth();
//
//    void onTickers();
//    void offTickers();
//
//    void onLoopringTickers();
//    void offLoopringTickers();


}
