package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisTickers extends Response<TickersResult>{

    public TickersResult.MarketTicker getLooisMarketTicker() {
        return getResult().loopr;
    }

    public TickersResult.MarketTicker getBinanceMarketTicker() {
        return getResult().binance;
    }

    public TickersResult.MarketTicker getOkExMarketTicker() {
        return getResult().okEx;
    }

    public TickersResult.MarketTicker getHuobiMarketTicker() {
        return getResult().huobi;
    }
}
