package org.loois.dapp.protocol.core;

import org.loois.dapp.protocol.LooisApi;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.params.CutoffParams;
import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.params.EstimatedAllocatedAllowanceParams;
import org.loois.dapp.protocol.core.params.FillsParams;
import org.loois.dapp.protocol.core.params.FrozenLRCFeeParams;
import org.loois.dapp.protocol.core.params.LRCSuggestChargeParams;
import org.loois.dapp.protocol.core.params.NotifyTransactionSubmittedParams;
import org.loois.dapp.protocol.core.params.OrderParams;
import org.loois.dapp.protocol.core.params.PriceQuoteParams;
import org.loois.dapp.protocol.core.params.RegisterERC20TokenParams;
import org.loois.dapp.protocol.core.params.RingMinedParams;
import org.loois.dapp.protocol.core.params.SearchLocalERC20TokenParams;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.core.params.SubmitRingForP2PParams;
import org.loois.dapp.protocol.core.params.SupportedTokensParams;
import org.loois.dapp.protocol.core.params.TickersParams;
import org.loois.dapp.protocol.core.params.TransactionParams;
import org.loois.dapp.protocol.core.params.TrendParams;
import org.loois.dapp.protocol.core.params.UnlockWalletParams;
import org.loois.dapp.protocol.core.response.LooisBalance;
import org.loois.dapp.protocol.core.response.LooisCutoff;
import org.loois.dapp.protocol.core.response.LooisDepth;
import org.loois.dapp.protocol.core.response.LooisEstimateGasPrice;
import org.loois.dapp.protocol.core.response.LooisEstimatedAllocatedAllowance;
import org.loois.dapp.protocol.core.response.LooisFills;
import org.loois.dapp.protocol.core.response.LooisFlexCancelOrder;
import org.loois.dapp.protocol.core.response.LooisFrozenLRCFee;
import org.loois.dapp.protocol.core.response.LooisLRCSuggestCharge;
import org.loois.dapp.protocol.core.response.LooisNotifyTransactionSubmitted;
import org.loois.dapp.protocol.core.response.LooisOrders;
import org.loois.dapp.protocol.core.response.LooisPriceQuote;
import org.loois.dapp.protocol.core.response.LooisRegisterERC20Token;
import org.loois.dapp.protocol.core.response.LooisRingMined;
import org.loois.dapp.protocol.core.response.LooisSearchLocalERC20Token;
import org.loois.dapp.protocol.core.response.LooisSubmitOrder;
import org.loois.dapp.protocol.core.response.LooisSubmitRingForP2P;
import org.loois.dapp.protocol.core.response.LooisSupportedMarket;
import org.loois.dapp.protocol.core.response.LooisSupportedTokens;
import org.loois.dapp.protocol.core.response.LooisTicker;
import org.loois.dapp.protocol.core.response.LooisTickers;
import org.loois.dapp.protocol.core.response.LooisTransactions;
import org.loois.dapp.protocol.core.response.LooisTrend;
import org.loois.dapp.protocol.core.response.LooisUnlockWallet;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;

import java.util.Arrays;
import java.util.Collections;


public final class LooisApiImpl implements LooisApi {

    private Web3jService web3jService;

    public LooisApiImpl(Web3jService web3jService) {
        this.web3jService = web3jService;
    }

    @Override
    public Request<?, LooisBalance> looisBalance(BalanceParams... params) {
        return new Request<>(
                Method.getBalance,
                Arrays.asList(params),
                web3jService,
                LooisBalance.class
        );
    }

    @Override
    public Request<?, LooisSubmitOrder> looisSubmitOrder(SubmitOrderParams... params) {
        return new Request<>(
                Method.submitOrder,
                Arrays.asList(params),
                web3jService,
                LooisSubmitOrder.class
        );
    }

    @Override
    public Request<?, LooisOrders> looisOrders(OrderParams... params) {
        return new Request<>(
                Method.getOrders,
                Arrays.asList(params),
                web3jService,
                LooisOrders.class
        );
    }

    @Override
    public Request<?, LooisDepth> looisDepth(DepthParams... params) {
        return new Request<>(
                Method.getDepth,
                Arrays.asList(params),
                web3jService,
                LooisDepth.class
        );
    }

    @Override
    public Request<?, LooisTicker> looisTicker() {
        return new Request<>(
                Method.getTicker,
                Collections.emptyList(),
                web3jService,
                LooisTicker.class
        );
    }

    @Override
    public Request<?, LooisTickers> looisTickers(TickersParams... params) {
        return new Request<>(
                Method.getTickers,
                Arrays.asList(params),
                web3jService,
                LooisTickers.class
        );
    }

    @Override
    public Request<?, LooisFills> looisFills(FillsParams... params) {
        return new Request<>(
                Method.getFills,
                Arrays.asList(params),
                web3jService,
                LooisFills.class
        );
    }

    @Override
    public Request<?, LooisTrend> looisTrend(TrendParams... params) {
        return new Request<>(
                Method.getTrend,
                Arrays.asList(params),
                web3jService,
                LooisTrend.class
        );
    }

    @Override
    public Request<?, LooisRingMined> looisRingMined(RingMinedParams... params) {
        return new Request<>(
                Method.getRingMined,
                Arrays.asList(params),
                web3jService,
                LooisRingMined.class
        );
    }

    @Override
    public Request<?, LooisCutoff> looisCutoff(CutoffParams... params) {
        return new Request<>(
                Method.getCutoff,
                Arrays.asList(params),
                web3jService,
                LooisCutoff.class
        );
    }

    @Override
    public Request<?, LooisPriceQuote> looisPriceQuote(PriceQuoteParams... params) {
        return new Request<>(
                Method.getPriceQuote,
                Arrays.asList(params),
                web3jService,
                LooisPriceQuote.class
        );
    }

    @Override
    public Request<?, LooisEstimatedAllocatedAllowance> looisEstimatedAllocatedAllowance(EstimatedAllocatedAllowanceParams... params) {
        return new Request<>(
                Method.getEstimatedAllocatedAllowance,
                Arrays.asList(params),
                web3jService,
                LooisEstimatedAllocatedAllowance.class
        );
    }


    @Override
    public Request<?, LooisFrozenLRCFee> looisFrozenLRCFee(FrozenLRCFeeParams... params) {
        return new Request<>(
                Method.getFrozenLRCFEE,
                Arrays.asList(params),
                web3jService,
                LooisFrozenLRCFee.class
        );
    }

    @Override
    public Request<?, LooisSupportedMarket> looisSupportedMarket() {
        return new Request<>(
                Method.getSupportedMarket,
                Collections.emptyList(),
                web3jService,
                LooisSupportedMarket.class
        );
    }

    @Override
    public Request<?, LooisSupportedTokens> looisSupportedTokens(SupportedTokensParams... params) {
        return new Request<>(
                Method.getSupportedTokens,
                Arrays.asList(params),
                web3jService,
                LooisSupportedTokens.class
        );
    }

    @Override
    public Request<?, LooisTransactions> looisTransactions(TransactionParams... params) {
        return new Request<>(
                Method.getTransactions,
                Arrays.asList(params),
                web3jService,
                LooisTransactions.class
        );
    }

    @Override
    public Request<?, LooisUnlockWallet> looisUnlockWallet(UnlockWalletParams... params) {
        return new Request<>(
                Method.unlockWallet,
                Arrays.asList(params),
                web3jService,
                LooisUnlockWallet.class
        );
    }

    @Override
    public Request<?, LooisNotifyTransactionSubmitted> looisNotifyTransactionSubmitted(NotifyTransactionSubmittedParams... params) {
        return new Request<>(
                Method.notifyTransactionSubmitted,
                Arrays.asList(params),
                web3jService,
                LooisNotifyTransactionSubmitted.class
        );
    }

    @Override
    public Request<?, LooisEstimateGasPrice> looisEstimateGasPrice() {
        return new Request<>(
                Method.getEstimateGasPrice,
                Collections.emptyList(),
                web3jService,
                LooisEstimateGasPrice.class
        );
    }

    @Override
    public Request<?, LooisSubmitRingForP2P> looisSubmitRingForP2P(SubmitRingForP2PParams... params) {
        return new Request<>(
                Method.loopring_submitRingForP2P,
                Arrays.asList(params),
                web3jService,
                LooisSubmitRingForP2P.class
        );
    }

    @Override
    public Request<?, LooisRegisterERC20Token> looisRegisterERC20Token(RegisterERC20TokenParams... params) {
        return new Request<>(
                Method.registerERC20Token,
                Arrays.asList(params),
                web3jService,
                LooisRegisterERC20Token.class
        );
    }

    @Override
    public Request<?, LooisLRCSuggestCharge> looisLRCSuggestCharge(LRCSuggestChargeParams... params) {
        return new Request<>(
                Method.getLRCSuggestCharge,
                Arrays.asList(params),
                web3jService,
                LooisLRCSuggestCharge.class
        );
    }

    @Override
    public Request<?, LooisSearchLocalERC20Token> looisSearchLocalERC20Token(SearchLocalERC20TokenParams... params) {
        return new Request<>(
                Method.searchLocalERC20Token,
                Arrays.asList(params),
                web3jService,
                LooisSearchLocalERC20Token.class
        );
    }

    @Override
    public Request<?, LooisFlexCancelOrder> looisFlexCancelOrder(LooisFlexCancelOrder... params) {
        return new Request<>(
                Method.flexCancelOrder,
                Arrays.asList(params),
                web3jService,
                LooisFlexCancelOrder.class
        );
    }
}