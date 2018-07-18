package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LoopringDepth extends Response<Depth> {

    public List<List<String>> getSellList() {
        return getResult().depth.sell;
    }

    public List<List<String>> getBuyList() {
        return getResult().depth.buy;
    }

    public String getMarket() {
        return getResult().market;
    }
}
