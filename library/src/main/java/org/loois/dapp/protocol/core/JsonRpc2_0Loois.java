package org.loois.dapp.protocol.core;

import org.loois.dapp.protocol.core.params.CutoffParams;
import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.params.EstimatedAllocatedAllowanceParams;
import org.loois.dapp.protocol.core.params.FillsParams;
import org.loois.dapp.protocol.core.params.FrozenLRCFeeParams;
import org.loois.dapp.protocol.core.params.OrderParams;
import org.loois.dapp.protocol.core.params.PriceQuoteParams;
import org.loois.dapp.protocol.core.params.RingMinedParams;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.Loois;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.params.SupportedTokensParams;
import org.loois.dapp.protocol.core.params.TickersParams;
import org.loois.dapp.protocol.core.params.TrendParams;
import org.loois.dapp.protocol.core.response.LooisBalance;
import org.loois.dapp.protocol.core.response.LooisCutoff;
import org.loois.dapp.protocol.core.response.LooisDepth;
import org.loois.dapp.protocol.core.response.LooisEstimatedAllocatedAllowance;
import org.loois.dapp.protocol.core.response.LooisFills;
import org.loois.dapp.protocol.core.response.LooisFrozenLRCFee;
import org.loois.dapp.protocol.core.response.LooisOrders;
import org.loois.dapp.protocol.core.response.LooisPriceQuote;
import org.loois.dapp.protocol.core.response.LooisRingMined;
import org.loois.dapp.protocol.core.response.LooisSubmitOrder;
import org.loois.dapp.protocol.core.response.LooisSupportedMarket;
import org.loois.dapp.protocol.core.response.LooisSupportedTokens;
import org.loois.dapp.protocol.core.response.LooisTicker;
import org.loois.dapp.protocol.core.response.LooisTickers;
import org.loois.dapp.protocol.core.response.LooisTrend;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.utils.Async;

 import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;

public class JsonRpc2_0Loois implements Loois {

    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

    protected final Web3jService web3jService;
    private final JsonRpc2_0LooisRx looisRx;
    private final long blockTime;

    public JsonRpc2_0Loois(Web3jService web3jService) {
        this(web3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Loois(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.web3jService = web3jService;
        this.looisRx = new JsonRpc2_0LooisRx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
    }

    @Override
    public Request<? , LooisBalance> looisBalance(BalanceParams ... params) {
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
    public Request<?, LooisTickers> looisTickers(TickersParams ...params) {
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
    public Request<?, LooisSupportedTokens> looisSupportedTokens(SupportedTokensParams params) {
        return new Request<>(
                Method.getSupportedTokens,
                Arrays.asList(params),
                web3jService,
                LooisSupportedTokens.class
        );
    }
}