package org.loois.dapp.protocol;

import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.response.LoopringBalance;
import org.web3j.protocol.core.Request;

public interface Loopring {

    Request<?, LoopringBalance> loopringBalance(BalanceParams ...params);


}
