package org.loois.dapp.protocol.core;

public interface Method {

    String unlockWallet = "loopring_unlockWallet";

    String getDepth = "loopring_getDepth";

    String getBalance = "loopring_getBalance";

    String getOrders = "loopring_getOrders";

    String getTicker = "loopring_getTicker";

    String getTickers = "loopring_getTickers";

    String getTrend = "loopring_getTrend";

    String getPriceQuote = "loopring_getPriceQuote";
    String submitP2p = "submitP2p";

    String submitOrder = "loopring_submitOrder";

    String getTransactions = "loopring_getTransactions";

    String getSupportedMarket = "loopring_getSupportedMarket";

    String notifyTransactionSubmitted = "loopring_notifyTransactionSubmitted";

    String getSupportedTokens = "loopring_getSupportedTokens";

    String getFills = "loopring_getFills";

    String getRingMined = "loopring_getRingMined";

    String getFrozenLRCFEE = "loopring_getFrozenLRCFee";

    String getEstimatedAllocatedAllowance = "loopring_getEstimatedAllocatedAllowance";

    String getEstimateGasPrice = "loopring_getEstimateGasPrice";

    String loopring_submitRingForP2P = "loopring_submitRingForP2P";
    String getLRCSuggestCharge = "loopring_getLRCSuggestCharge";
    String searchLocalERC20Token = "loopring_searchLocalERC20Token";
    String registerERC20Token = "loopring_registerERC20Token";
}
