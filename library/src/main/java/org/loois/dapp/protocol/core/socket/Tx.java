package org.loois.dapp.protocol.core.socket;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/5/19
 * Brief Desc :
 * </pre>
 */
public class Tx {

    /**
     * protocol : 0x0000000000000000000000000000000000000000
     * owner : 0xeaeec75ba0880a44edc5460e1d91c59a9da6bbc7
     * from : 0xaf4855d823f61fd5175d15d8807dfe600fd36022
     * to : 0xeaeec75ba0880a44edc5460e1d91c59a9da6bbc7
     * txHash : 0x0ebfa8c2bbb8d9c68d954b0e332229883a02aaf763d26676efcb65b3c3525c45
     * symbol : ETH
     * content : {"market":"","orderHash":"","fill":""}
     * blockNumber : 0
     * value : 0
     * logIndex : 0
     * type : unsupported_contract
     * status : pending
     * createTime : 1526702694
     * updateTime : 1526702694
     * gas_price : 73000000000
     * gas_limit : 200000
     * gas_used : 0
     * nonce : 3
     */

    public String protocol;
    public String owner;
    public String from;
    public String to;
    public String txHash;
    public String symbol;
    public ContentBean content;
    public int blockNumber;
    public String value;
    public int logIndex;
    public String type;
    public String status;
    public int createTime;
    public int updateTime;
    public String gas_price;
    public String gas_limit;
    public String gas_used;
    public String nonce;

    public static class ContentBean {
        /**
         * market :
         * orderHash :
         * fill :
         */

        public String market;
        public String orderHash;
        public String fill;
    }


    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public int hashCode() {
        return txHash.hashCode();
    }

    @Override
    public String toString() {
        return "Tx{" +
                "protocol='" + protocol + '\'' +
                ", owner='" + owner + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", txHash='" + txHash + '\'' +
                ", symbol='" + symbol + '\'' +
                ", content=" + content +
                ", blockNumber=" + blockNumber +
                ", value='" + value + '\'' +
                ", logIndex=" + logIndex +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", gas_price='" + gas_price + '\'' +
                ", gas_limit='" + gas_limit + '\'' +
                ", gas_used='" + gas_used + '\'' +
                ", nonce='" + nonce + '\'' +
                '}';
    }
}
