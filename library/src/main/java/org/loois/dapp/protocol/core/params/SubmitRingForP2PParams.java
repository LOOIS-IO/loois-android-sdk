package org.loois.dapp.protocol.core.params;

public class SubmitRingForP2PParams {

    public String takerOrderHash;

    public String makerOrderHash;

    public String rawTx;


    public SubmitRingForP2PParams(String takerOrderHash, String makerOrderHash, String rawTx) {
        this.takerOrderHash = takerOrderHash;
        this.makerOrderHash = makerOrderHash;
        this.rawTx = rawTx;
    }
}