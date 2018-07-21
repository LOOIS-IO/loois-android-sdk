package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisEstimatedAllocatedAllowance extends Response<String>{

    public String getEstimatedAllocatedAllowance() {
        return getResult();
    }
}
