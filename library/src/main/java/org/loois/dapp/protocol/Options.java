package org.loois.dapp.protocol;


public class Options {

    private byte chainId = LooisConfig.CHAIN_ID;

    private String baseUrl = LooisConfig.BASE_URL;

    private String ethUrl = LooisConfig.ETH_URL;

    private String delegateAddress = LooisConfig.DELEGATE_ADDRESS;

    private String protocolAddress = LooisConfig.PROTOCOL_ADDRESS;

    private String bindContractAddress = LooisConfig.BIND_CONTRACT_ADDRESS;

    private String orderWalletAddress = LooisConfig.ORDER_WALLET_ADDRESS;

    private String contractVersion = LooisConfig.CONTRACT_VERSION;

    public byte getChainId() {
        return chainId;
    }

    public void setChainId(byte chainId) {
        this.chainId = chainId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEthUrl() {
        return ethUrl;
    }

    public void setEthUrl(String ethUrl) {
        this.ethUrl = ethUrl;
    }

    public String getDelegateAddress() {
        return delegateAddress;
    }

    public void setDelegateAddress(String delegateAddress) {
        this.delegateAddress = delegateAddress;
    }

    public String getProtocolAddress() {
        return protocolAddress;
    }

    public void setProtocolAddress(String protocolAddress) {
        this.protocolAddress = protocolAddress;
    }

    public String getBindContractAddress() {
        return bindContractAddress;
    }

    public void setBindContractAddress(String bindContractAddress) {
        this.bindContractAddress = bindContractAddress;
    }

    public String getOrderWalletAddress() {
        return orderWalletAddress;
    }

    public void setOrderWalletAddress(String orderWalletAddress) {
        this.orderWalletAddress = orderWalletAddress;
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
    }
}
