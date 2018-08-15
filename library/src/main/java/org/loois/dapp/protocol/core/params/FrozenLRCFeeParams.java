package org.loois.dapp.protocol.core.params;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.LooisConfig;

public class FrozenLRCFeeParams {

    public String owner;

    public String contractVersion = Loois.getOptions().getContractVersion();

    public String delegateAddress = Loois.getOptions().getDelegateAddress();

    public FrozenLRCFeeParams(String owner) {
        this.owner = owner;
    }

    public FrozenLRCFeeParams(String owner, String delegateAddress) {
        this.owner = owner;
        this.delegateAddress = delegateAddress;
    }
}
