package org.loois.dapp.rx;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

public abstract class LooisSubscriber<T> implements FlowableSubscriber<T>{

    public abstract void onSuccess(T t);

    public abstract void onFailed(Throwable throwable);


    @Override
    public void onSubscribe(Subscription s) {
        s.request(Integer.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        onFailed(t);
    }

    @Override
    public void onComplete() {

    }
}
