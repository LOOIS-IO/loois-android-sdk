package org.loois.dapp.protocol;

public interface LooisSocketApi {

    void onBalance(String owner);
    void offBalance();

    void onTransaction(String owner);
    void offTransaction();
//
//    void onMarketCap();
//    void offMarketCap();
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
