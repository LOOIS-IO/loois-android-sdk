package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisBalance extends Response<BalanceResult> {

    public List<Token> getTokens() {
        return getResult().tokens;
    }

    public String getOwner() {
        return getResult().owner;
    }

    public String getDelegateAddress() {
        return getResult().delegateAddress;
    }
}
