package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisSubmitOrder extends Response<String> {

    public String getOrderHash() {
        return getResult();
    }
}
