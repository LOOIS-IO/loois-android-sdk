package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisSupportedMarket extends Response<List<String>>{

    public List<String> getSupportedMarket() {
        return getResult();
    }
}
