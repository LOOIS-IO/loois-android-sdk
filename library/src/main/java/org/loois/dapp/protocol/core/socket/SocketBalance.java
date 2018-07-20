package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.BalanceResult;
import org.loois.dapp.protocol.core.response.Token;

import java.util.List;

public class SocketBalance extends SocketResponse<BalanceResult>{

    public List<Token> getTokens() {
        return data.tokens;
    }

}
