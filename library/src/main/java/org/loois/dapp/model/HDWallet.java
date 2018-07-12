package org.loois.dapp.model;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class HDWallet {

    public String address;

    public String keystore;

    public HDWallet(String address, String keystore) {
        this.address = address != null && address.startsWith("0x") ? address : "0x"+address;
        this.keystore = keystore;
    }
}
