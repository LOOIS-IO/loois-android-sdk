package org.loois.dapp.protocol.core.response;

import java.io.Serializable;

public class Transaction {
    /**
     * protocol : 0x0000000000000000000000000000000000000000
     * owner : 0x62dd83a35948d9c4d980550cfa21a69b7a245f57
     * from : 0xf5613e4da78cee6a1bffdf9c235d56bbf6d01d8d
     * to : 0x62dd83a35948d9c4d980550cfa21a69b7a245f57
     * txHash : 0x65d34fe35e4d76eb5e97df758ac2a35ff122f6c620996fc6daef975578ab6e98
     * symbol : ETH
     * content : {"market":"","orderHash":"","fill":""}
     * blockNumber : 5560401
     * value : 1999998000000000000
     * logIndex : 0
     * type : receive       unsupported_contract
     * status : success
     * createTime : 1525522287
     * updateTime : 1525522287
     * gas_price : 28000000000
     * gas_limit : 90000
     * gas_used : 21000
     * nonce : 26290
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

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLogIndex() {
        return logIndex;
    }

    public void setLogIndex(int logIndex) {
        this.logIndex = logIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getGas_price() {
        return gas_price;
    }

    public void setGas_price(String gas_price) {
        this.gas_price = gas_price;
    }

    public String getGas_limit() {
        return gas_limit;
    }

    public void setGas_limit(String gas_limit) {
        this.gas_limit = gas_limit;
    }

    public String getGas_used() {
        return gas_used;
    }

    public void setGas_used(String gas_used) {
        this.gas_used = gas_used;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }


    public static class ContentBean implements Serializable {
        /**
         * market :
         * orderHash :
         * fill :
         */

        private String market;
        private String orderHash;
        private String fill;

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public String getOrderHash() {
            return orderHash;
        }

        public void setOrderHash(String orderHash) {
            this.orderHash = orderHash;
        }

        public String getFill() {
            return fill;
        }

        public void setFill(String fill) {
            this.fill = fill;
        }
    }

}
