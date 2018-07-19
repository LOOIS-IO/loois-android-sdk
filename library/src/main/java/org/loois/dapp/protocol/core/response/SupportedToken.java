package org.loois.dapp.protocol.core.response;

import java.math.BigDecimal;

public class SupportedToken {


    /**
     * protocol : 0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2
     * symbol : WETH
     * source : ethereum
     * time : 0
     * deny : false
     * decimals : 1000,000,000,000,000,000
     * isMarket : true
     * icoPrice : null
     */

    public String protocol;
    public String symbol;
    public String source;
    public long time;
    public boolean deny;
    public BigDecimal decimals;
    public boolean isMarket;
    public Object icoPrice;
    public boolean isRegister;


    @Override
    public String toString() {
        return "Token{" +
                "protocol='" + protocol + '\'' +
                ", symbol='" + symbol + '\'' +
                ", source='" + source + '\'' +
                ", time=" + time +
                ", deny=" + deny +
                ", decimals=" + decimals +
                ", isMarket=" + isMarket +
                ", icoPrice=" + icoPrice +
                '}';
    }
}
