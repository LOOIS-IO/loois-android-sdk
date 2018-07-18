package org.loois.dapp.protocol.core;

import org.loois.dapp.protocol.Loois;
import org.web3j.protocol.rx.JsonRpc2_0Rx;

import java.util.concurrent.ScheduledExecutorService;

import rx.Scheduler;
import rx.schedulers.Schedulers;

class JsonRpc2_0LooisRx {

    private final Loois loois;

    private final ScheduledExecutorService scheduledExecutorService;

    private final Scheduler scheduler;


    public JsonRpc2_0LooisRx(Loois loois, ScheduledExecutorService scheduledExecutorService) {
        this.loois = loois;
        this.scheduledExecutorService = scheduledExecutorService;
        this.scheduler = Schedulers.from(scheduledExecutorService);
    }
}
