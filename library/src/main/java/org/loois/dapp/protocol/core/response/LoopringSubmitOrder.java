package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LoopringSubmitOrder extends Response<String> {

    public String getOrderHash() {
        return getResult();
    }
}
