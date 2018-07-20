package org.loois.dapp.protocol.core.socket;


import org.loois.dapp.protocol.core.response.Token;

import java.util.List;

public class BalanceR {

    public String delegateAddress;

    public String owner;

    public List<Token> tokens;


    @Override
    public String toString() {
        return "BalanceR{" +
                "delegateAddress='" + delegateAddress + '\'' +
                ", owner='" + owner + '\'' +
                ", tokens=" + tokens +
                '}';
    }
}
