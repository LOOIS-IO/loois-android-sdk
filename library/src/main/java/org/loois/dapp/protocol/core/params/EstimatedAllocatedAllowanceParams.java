package org.loois.dapp.protocol.core.params;

public class EstimatedAllocatedAllowanceParams {

    public String owner;

    public String token;

    public String delegateAddress;

    public EstimatedAllocatedAllowanceParams(String owner, String token, String delegateAddress) {
        this.owner = owner;
        this.token = token;
        this.delegateAddress = delegateAddress;
    }
}
