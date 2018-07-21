package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisFills extends Response<Page<Fill>>{

    public List<Fill> getFills() {
        return getResult().data;
    }
}
