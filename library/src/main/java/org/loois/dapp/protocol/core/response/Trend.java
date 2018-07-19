package org.loois.dapp.protocol.core.response;

import java.math.BigDecimal;

public class Trend {


    /**
     * market : LRC-WETH
     * high : 30384.2
     * low : 19283.2
     * vol : 1038
     * amount : 1003839.32
     * open : 122321.01
     * close : 12388.3
     * start : 1512646617
     * end : 1512726001
     */

    public String market;
    public BigDecimal high;
    public BigDecimal low;
    public BigDecimal vol;
    public BigDecimal amount;
    public BigDecimal open;
    public BigDecimal close;
    public long start;
    public long end;


    @Override
    public String toString() {
        return "Trend{" +
                "market='" + market + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", vol=" + vol +
                ", amount=" + amount +
                ", open=" + open +
                ", close=" + close +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
