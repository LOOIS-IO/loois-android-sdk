package org.loois.dapp.protocol.core.response;

import java.math.BigDecimal;

public class TickersResult {


    /**
     * loopr : {"exchange":"loopr","high":30384.2,"low":19283.2,"last":28002.2,"vol":1038,"amount":1003839.32,"buy":122321,"sell":12388,"change":"-50.12%"}
     * binance : {"exchange":"binance","high":30384.2,"low":19283.2,"last":28002.2,"vol":1038,"amount":1003839.32,"buy":122321,"sell":12388,"change":"-50.12%"}
     * okEx : {"exchange":"okEx","high":30384.2,"low":19283.2,"last":28002.2,"vol":1038,"amount":1003839.32,"buy":122321,"sell":12388,"change":"-50.12%"}
     * huobi : {"exchange":"huobi","high":30384.2,"low":19283.2,"last":28002.2,"vol":1038,"amount":1003839.32,"buy":122321,"sell":12388,"change":"-50.12%"}
     */

    public MarketTicker loopr;
    public MarketTicker binance;
    public MarketTicker okEx;
    public MarketTicker huobi;


    public class MarketTicker {
        /**
         * exchange : loopr
         * high : 30384.2
         * low : 19283.2
         * last : 28002.2
         * vol : 1038
         * amount : 1003839.32
         * buy : 122321
         * sell : 12388
         * change : -50.12%
         */

        public String exchange;
        public BigDecimal high;
        public BigDecimal low;
        public BigDecimal last;
        public BigDecimal vol;
        public BigDecimal amount;
        public BigDecimal buy;
        public BigDecimal sell;
        public String change;

    }

}
