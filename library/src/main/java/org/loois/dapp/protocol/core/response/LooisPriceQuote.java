package org.loois.dapp.protocol.core.response;

import org.web3j.protocol.core.Response;

import java.util.List;

public class LooisPriceQuote extends Response<PriceQuoteResult>{

    public String getCurrency() {
        return getResult().currency;
    }

    public List<PriceQuoteResult.PriceQuoteToken> getPriceQuoteTokens() {
        return getResult().tokens;
    }

}
