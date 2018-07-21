package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisFrozenLRCFee extends Response<String>{
    public String getProzenLRCFee() {
        return getResult();
    }
}
