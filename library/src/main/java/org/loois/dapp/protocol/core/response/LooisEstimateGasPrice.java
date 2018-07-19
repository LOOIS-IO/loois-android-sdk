package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisEstimateGasPrice extends Response<String> {

    public String getEstimateGasPrice() {
        return getResult();
    }
}
