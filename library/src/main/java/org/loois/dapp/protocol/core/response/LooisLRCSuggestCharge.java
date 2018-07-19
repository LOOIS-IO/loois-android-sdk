package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisLRCSuggestCharge extends Response<String>{

    public String getLRCSuggestCharge() {
        return getResult();
    }

}
