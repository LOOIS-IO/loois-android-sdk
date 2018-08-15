package org.loois.dapp.protocol.core.params;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.LooisConfig;

public class FlexCancelOrderParams {

    public String contractVersion = Loois.getOptions().getContractVersion();
    public String delegateAddress = Loois.getOptions().getDelegateAddress();

    public String orderHash;
    public int cutoffTime;
    public String tokenS;
    public String tokenB;
    public int type;
    public SignBean sign;

    @Override
    public String toString() {
        return "FlexCancelOrderParams{" +
                "contractVersion='" + contractVersion + '\'' +
                ", delegateAddress='" + delegateAddress + '\'' +
                ", orderHash='" + orderHash + '\'' +
                ", cutoffTime=" + cutoffTime +
                ", tokenS='" + tokenS + '\'' +
                ", tokenB='" + tokenB + '\'' +
                ", type=" + type +
                ", sign=" + sign +
                '}';
    }

    public FlexCancelOrderParams(String orderHash,
                                 int cutoffTime,
                                 String tokenS,
                                 String tokenB,
                                 int type,
                                 SignBean sign, String contractVersion, String delegateAddress) {
        this(orderHash, cutoffTime, tokenS, tokenB, type, sign);
        this.contractVersion = contractVersion;
        this.delegateAddress = delegateAddress;
    }

    public FlexCancelOrderParams(String orderHash,
                                 int cutoffTime,
                                 String tokenS,
                                 String tokenB,
                                 int type,
                                 SignBean sign) {
        this.orderHash = orderHash;
        this.cutoffTime = cutoffTime;
        this.tokenS = tokenS;
        this.tokenB = tokenB;
        this.type = type;
        this.sign = sign;
    }

    public static class SignBean {
        /**
         * owner : 0x71c079107b5af8619d54537a93dbf16e5aab4900
         * v : 27
         * r : 0xfc476be69f175c18f16cf72738cec0b810716a8e564914e8d6eb2f61e33ad454
         * s : 0x3570a561cb85cc65c969411dabfd470a436d3af2d04694a410f500f2a6238127
         * timestamp : 1444423423
         */
        public String owner;
        public int v;
        public String r;
        public String s;
        public String timestamp;

        @Override
        public String toString() {
            return "SignBean{" +
                    "owner='" + owner + '\'' +
                    ", v=" + v +
                    ", r='" + r + '\'' +
                    ", s='" + s + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }

        public SignBean(String owner,
                        int v,
                        String r,
                        String s,
                        String timestamp) {
            this.owner = owner;
            this.v = v;
            this.r = r;
            this.s = s;
            this.timestamp = timestamp;


        }
    }
}