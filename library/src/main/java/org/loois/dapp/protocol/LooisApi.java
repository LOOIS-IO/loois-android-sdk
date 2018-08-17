package org.loois.dapp.protocol;

import org.loois.dapp.Loois;
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
import org.loois.dapp.protocol.core.params.SubmitRingForP2PParams;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.core.params.SupportedTokensParams;
import org.loois.dapp.protocol.core.params.TickersParams;
import org.loois.dapp.protocol.core.params.TransactionParams;
import org.loois.dapp.protocol.core.params.TrendParams;
import org.loois.dapp.protocol.core.params.UnlockWalletParams;
import org.loois.dapp.protocol.core.response.LooisCutoff;
import org.loois.dapp.protocol.core.response.LooisDepth;
import org.loois.dapp.protocol.core.response.LooisEstimateGasPrice;
import org.loois.dapp.protocol.core.response.LooisEstimatedAllocatedAllowance;
import org.loois.dapp.protocol.core.response.LooisFills;
import org.loois.dapp.protocol.core.response.LooisFlexCancelOrder;
import org.loois.dapp.protocol.core.response.LooisFrozenLRCFee;
import org.loois.dapp.protocol.core.response.LooisLRCSuggestCharge;
import org.loois.dapp.protocol.core.response.LooisNotifyTransactionSubmitted;
import org.loois.dapp.protocol.core.response.LooisPriceQuote;
import org.loois.dapp.protocol.core.response.LooisRegisterERC20Token;
import org.loois.dapp.protocol.core.response.LooisRingMined;
import org.loois.dapp.protocol.core.response.LooisOrders;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.response.LooisBalance;
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
import org.loois.dapp.protocol.core.response.Market;
import org.loois.dapp.protocol.core.response.PriceQuoteResult;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.Response;

import java.util.List;

import io.reactivex.Flowable;


/**
 * Define the Loorping JSON-RPC methods
 */
public interface LooisApi {

    Request<?, LooisBalance> looisBalance(BalanceParams ...params);

    Request<?, LooisSubmitOrder> looisSubmitOrder(SubmitOrderParams ...params);

    Request<?, LooisOrders> looisOrders(OrderParams ...params);

    Request<?, LooisDepth> looisDepth(DepthParams ...params);

    Request<?, LooisTicker> looisTicker();

    Request<?, LooisTickers> looisTickers(TickersParams ...params);

    Request<?, LooisFills> looisFills(FillsParams...params);

    Request<?, LooisTrend> looisTrend(TrendParams ...params);

    Request<?, LooisRingMined> looisRingMined(RingMinedParams ...params);

    Request<?, LooisCutoff> looisCutoff(CutoffParams ...params);

    Request<?, LooisPriceQuote> looisPriceQuote(PriceQuoteParams ...params);

    Request<?, LooisEstimatedAllocatedAllowance> looisEstimatedAllocatedAllowance(EstimatedAllocatedAllowanceParams ...params);

    Request<?, LooisFrozenLRCFee> looisFrozenLRCFee(FrozenLRCFeeParams ...params);

    Request<?, LooisSupportedMarket> looisSupportedMarket();

    Request<?, LooisSupportedTokens> looisSupportedTokens(SupportedTokensParams ...params);

    Request<?, LooisTransactions> looisTransactions(TransactionParams ...params);

    Request<?, LooisUnlockWallet> looisUnlockWallet(UnlockWalletParams ...params);

    Request<?, LooisNotifyTransactionSubmitted> looisNotifyTransactionSubmitted(NotifyTransactionSubmittedParams ...params);

    Request<?, LooisEstimateGasPrice> looisEstimateGasPrice();

    Request<?, LooisSubmitRingForP2P> looisSubmitRingForP2P(SubmitRingForP2PParams ...params);

    Request<?, LooisRegisterERC20Token> looisRegisterERC20Token(RegisterERC20TokenParams ...params);

    Request<?, LooisLRCSuggestCharge> looisLRCSuggestCharge(LRCSuggestChargeParams ...params);

    Request<?, LooisSearchLocalERC20Token> looisSearchLocalERC20Token(SearchLocalERC20TokenParams...params);

    Request<?, LooisFlexCancelOrder> looisFlexCancelOrder(LooisFlexCancelOrder ...params);


    //Flowable API
    Flowable<List<Market>> looisTickerFlowable();
    Flowable<PriceQuoteResult> looisPriceQuoteFlowable(PriceQuoteParams... params);

}
