package org.loois.dapp.protocol.core.params;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class NotifyTransactionSubmittedParams {

    public String hash;
    public String nonce;
    public String to;
    public String value;
    public String gasPrice;
    public String gas;
    public String input;
    public String from;

    public NotifyTransactionSubmittedParams(String txHash, Integer nonce, String to, BigInteger gasPrice, BigInteger gas, String input, String from) {
        this(txHash, nonce, to, BigInteger.ZERO, gasPrice, gas, input, from);
    }


    public NotifyTransactionSubmittedParams(String txHash, Integer nonce, String to, BigInteger value, BigInteger gasPrice, BigInteger gas, String input, String from) {
        this.hash = txHash;
        this.nonce = Numeric.encodeQuantity(BigInteger.valueOf(nonce));
        this.to = to;
        this.value = Numeric.encodeQuantity(value);
        this.gasPrice = Numeric.encodeQuantity(gasPrice);
        this.gas = Numeric.encodeQuantity(gas);
        this.input = input;
        this.from = from;
    }
}