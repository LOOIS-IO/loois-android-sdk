package org.loois.dapp.protocol.core.response;

import java.math.BigDecimal;


public class Market {

    /**
     * market : 1ST-BAR
     * exchange :
     * interval :
     * amount : 0
     * vol : 0
     * open : 0
     * close : 0
     * high : 0
     * low : 0
     * last : 0
     * buy : 0
     * sell : 0
     * change :
     */

    public String market;
    public String exchange;
    public String interval;
    public BigDecimal amount = BigDecimal.valueOf(0);
    public BigDecimal vol = BigDecimal.valueOf(0);
    public BigDecimal open = BigDecimal.valueOf(0);
    public BigDecimal close = BigDecimal.valueOf(0);
    public BigDecimal high = BigDecimal.valueOf(0);
    public BigDecimal low = BigDecimal.valueOf(0);
    public BigDecimal last = BigDecimal.valueOf(0);
    public String buy;
    public String sell;
    public String change;
    public int areaType;




    @Override
    public String toString() {
        return "Market{" +
                "market='" + market + '\'' +
                ", exchange='" + exchange + '\'' +
                ", interval='" + interval + '\'' +
                ", amount=" + amount +
                ", vol=" + vol +
                ", open=" + open +
                ", close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", last=" + last +
                ", buy='" + buy + '\'' +
                ", sell='" + sell + '\'' +
                ", change='" + change + '\'' +
                ", areaType='" + areaType + '\'' +
                '}';
    }
}
