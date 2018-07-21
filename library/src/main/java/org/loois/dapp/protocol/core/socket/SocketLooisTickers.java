package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.Market;

import java.util.List;

public class SocketLooisTickers extends SocketResponse<List<Market>>{

    public List<Market> getMarkets() {
        return data;
    }
}
