package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisSupportedTokens extends Response<List<SupportedToken>> {

    public List<SupportedToken> getSupportedTokens() {
        return getResult();
    }
}
