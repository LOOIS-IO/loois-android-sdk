package org.loois.dapp.protocol.core.params;

import org.loois.dapp.protocol.Config;

public class FrozenLRCFeeParams {

    public String owner;

    public String contractVersion = Config.CONTRACT_VERSION;

    public String delegateAddress = Config.DELEGATE_ADDRESS;

    public FrozenLRCFeeParams(String owner) {
        this.owner = owner;
    }

    public FrozenLRCFeeParams(String owner, String delegateAddress) {
        this.owner = owner;
        this.delegateAddress = delegateAddress;
    }
}
