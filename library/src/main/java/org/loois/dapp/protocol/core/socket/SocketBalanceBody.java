package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.Config;

public class SocketBalanceBody {

    public String owner;

    public String delegateAddress = Config.DELEGATE_ADDRESS;

    public SocketBalanceBody(String owner) {
        this.owner = owner;
    }

    public SocketBalanceBody(String owner, String delegateAddress) {
        this.owner = owner;
        this.delegateAddress = delegateAddress;
    }

}
