package org.loois.dapp.protocol.core.response;

import java.math.BigDecimal;
import java.util.List;

public class PriceQuoteResult {

    public String currency;
    public List<PriceQuoteToken> tokens;

    public static class PriceQuoteToken {
        public String symbol;
        public BigDecimal price;
    }
}
