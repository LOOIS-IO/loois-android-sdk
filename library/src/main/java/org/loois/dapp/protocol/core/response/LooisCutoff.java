package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

public class LooisCutoff extends Response<String>{

    public String getCutoffTimestamp() {
        return getResult();
    }
}
