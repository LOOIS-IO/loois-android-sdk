package org.loois.dapp.manager;

public interface LooisListener {

        void onSuccess(String result);

        void onFailed(Throwable throwable);
    }
