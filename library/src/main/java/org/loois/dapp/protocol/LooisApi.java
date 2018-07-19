package org.loois.dapp.protocol;

import org.loois.dapp.protocol.core.params.CutoffParams;
import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.params.FillsParams;
import org.loois.dapp.protocol.core.params.OrderParams;
import org.loois.dapp.protocol.core.params.PriceQuoteParams;
import org.loois.dapp.protocol.core.params.RingMinedParams;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.core.params.TickersParams;
import org.loois.dapp.protocol.core.params.TrendParams;
import org.loois.dapp.protocol.core.response.LooisCutoff;
import org.loois.dapp.protocol.core.response.LooisDepth;
import org.loois.dapp.protocol.core.response.LooisFills;
import org.loois.dapp.protocol.core.response.LooisPriceQuote;
import org.loois.dapp.protocol.core.response.LooisRingMined;
import org.loois.dapp.protocol.core.response.LooisOrders;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.response.LooisBalance;
import org.loois.dapp.protocol.core.response.LooisSubmitOrder;
import org.loois.dapp.protocol.core.response.LooisTicker;
import org.loois.dapp.protocol.core.response.LooisTickers;
import org.loois.dapp.protocol.core.response.LooisTrend;
import org.web3j.protocol.core.Request;


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
}
