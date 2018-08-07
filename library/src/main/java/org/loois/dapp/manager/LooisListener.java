package org.loois.dapp.manager;

public interface LooisListener<T> {

        void onSuccess(T result);

        void onFailed(Throwable throwable);
    }
