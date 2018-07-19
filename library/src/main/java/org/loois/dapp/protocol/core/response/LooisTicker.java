package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisTicker extends Response<List<Market>> {

    public List<Market> getMarkets() {
        return getResult();
    }
}
