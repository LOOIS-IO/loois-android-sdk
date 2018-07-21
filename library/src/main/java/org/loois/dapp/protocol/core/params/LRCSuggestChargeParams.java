package org.loois.dapp.protocol.core.params;

public class LRCSuggestChargeParams {
    public String owner;
    public String currency;

    public LRCSuggestChargeParams(String owner, String currency) {
        this.owner = owner;
        this.currency = currency;
    }
}