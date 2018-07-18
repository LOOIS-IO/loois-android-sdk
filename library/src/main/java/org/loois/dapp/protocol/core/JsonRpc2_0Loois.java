package org.loois.dapp.protocol.core;

import org.loois.dapp.protocol.Loois;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.response.LoopringBalance;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.utils.Async;

import java.util.Arrays;
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
    public Request<? , LoopringBalance> loopringBalance(BalanceParams ... params) {
        return new Request<>(
                Method.getBalance,
                Arrays.asList(params),
                web3jService,
                LoopringBalance.class
        );
    }
}