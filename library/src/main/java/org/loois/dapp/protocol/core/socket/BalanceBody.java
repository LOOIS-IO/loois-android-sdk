package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.LooisConfig;

public class BalanceBody {

    public String owner;

    public String delegateAddress = Loois.getOptions().getDelegateAddress();

    public BalanceBody(String owner) {
        this.owner = owner;
    }

    public BalanceBody(String owner, String delegateAddress) {
        this.owner = owner;
        this.delegateAddress = delegateAddress;
    }

}
