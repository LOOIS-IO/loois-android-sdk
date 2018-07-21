package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.TickersResult;

public class SocketTickers extends SocketResponse<TickersResult>{

    public TickersResult getTickers() {
        return data;
    }
}
