package org.loois.dapp.protocol;

import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.params.OrderParams;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.core.response.LoopringDepth;
import org.loois.dapp.protocol.core.response.LoopringOrders;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.response.LoopringBalance;
import org.loois.dapp.protocol.core.response.LoopringSubmitOrder;
import org.web3j.protocol.core.Request;

public interface Loopring {

    Request<?, LoopringBalance> loopringBalance(BalanceParams ...params);


    Request<?, LoopringSubmitOrder> loopringSubmitOrder(SubmitOrderParams ...params);


    Request<?, LoopringOrders> loopringOrders(OrderParams ...params);


    Request<?, LoopringDepth> loopringDepth(DepthParams ...params);
}
