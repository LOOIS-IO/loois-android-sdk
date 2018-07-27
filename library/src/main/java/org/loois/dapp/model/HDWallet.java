package org.loois.dapp.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.web3j.crypto.WalletFile;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class HDWallet {

    public String address;

    private WalletFile walletFile;


    public HDWallet(String address, WalletFile walletFile) {
        this.address = address != null && address.startsWith("0x") ? address : "0x"+address;
        this.walletFile = walletFile;
    }

    public WalletFile getWalletFile() {
        return walletFile;
    }

    public String getWalletFileString() {
        if (walletFile != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(walletFile);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
