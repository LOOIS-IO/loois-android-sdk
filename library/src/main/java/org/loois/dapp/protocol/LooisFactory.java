package org.loois.dapp.protocol;

import org.loois.dapp.protocol.core.JsonRpc2_0Loois;
import org.web3j.protocol.Web3jService;

import java.util.concurrent.ScheduledExecutorService;

public class LooisFactory {

    public static Loois build(Web3jService web3jService) {
        return new JsonRpc2_0Loois(web3jService);
    }

    public static Loois build(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Loois(web3jService, pollingInterval, scheduledExecutorService);
    }
}
