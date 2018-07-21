package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisTrend extends Response<List<Trend>>{

    public List<Trend> getTrend() {
        return getResult();
    }
}
