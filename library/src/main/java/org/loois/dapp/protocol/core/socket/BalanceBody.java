package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.Config;

public class BalanceBody {

    public String owner;

    public String delegateAddress = Config.DELEGATE_ADDRESS;

    public BalanceBody(String owner) {
        this.owner = owner;
    }

    public BalanceBody(String owner, String delegateAddress) {
        this.owner = owner;
        this.delegateAddress = delegateAddress;
    }

}
