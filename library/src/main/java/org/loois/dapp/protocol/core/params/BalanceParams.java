package org.loois.dapp.protocol.core.params;

public class BalanceParams {

    public BalanceParams(String owner, String delegateAddress) {
        this.owner = owner;
        this.delegateAddress = delegateAddress;
    }

    public String owner;

    public String delegateAddress;
}
