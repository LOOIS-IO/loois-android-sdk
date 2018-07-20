package org.loois.dapp.protocol.core.socket;

import org.loois.dapp.protocol.core.response.PriceQuoteResult;

import java.util.List;

public class SocketMarketCap extends SocketResponse<PriceQuoteResult> {

    public String getCurrency() {
        return data.currency;
    }

    public List<PriceQuoteResult.PriceQuoteToken> getMarketCap() {
        return data.tokens;
    }
}
