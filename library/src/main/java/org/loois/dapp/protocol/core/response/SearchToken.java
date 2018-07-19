package org.loois.dapp.protocol.core.response;

import java.util.List;

public class SearchToken {

    /**
     * isOk : true
     * tokens : [{"protocol":"0x6435636331393538613665643131353363386561","symbol":"CABC","name":"Cashback coin ","source":"","time":0,"deny":false,"decimals":8,"isMarket":false,"icoPrice":null,"areaType":0},{"protocol":"0x6533663238336465326135616266383031396138","symbol":"THER","name":"TherapistCoin ","source":"","time":0,"deny":false,"decimals":18,"isMarket":false,"icoPrice":null,"areaType":0}]
     */

    public boolean isOk;
    public List<TokensBean> tokens;


    public static class TokensBean {
        /**
         * protocol : 0x6435636331393538613665643131353363386561
         * symbol : CABC
         * name : Cashback coin
         * source :
         * time : 0
         * deny : false
         * decimals : 8
         * isMarket : false
         * icoPrice : null
         * areaType : 0
         */
        public String protocol;
        public String symbol;
        public String name;
        public String source;
        public int time;
        public boolean deny;
        public int decimals;
        public boolean isMarket;
        public Object icoPrice;
        public int areaType;

    }


}
