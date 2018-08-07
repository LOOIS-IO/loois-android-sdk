package org.loois.dapp.manager;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.core.params.SupportedTokensParams;
import org.loois.dapp.protocol.core.response.SupportedToken;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.rx.ScheduleCompat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class TokenManager {

    public List<SupportedToken> supportedTokens = new ArrayList<>();


    public void fetchSupportedTokens(String address) {
        Flowable.just(address)
                .map(s -> Loois.client().looisSupportedTokens(new SupportedTokensParams(s)).sendAsync().get())
                .compose(RxResultHelper.handleResult())
                .compose(ScheduleCompat.apply())
                .subscribe(new LooisSubscriber<List<SupportedToken>>() {
                    @Override
                    public void onSuccess(List<SupportedToken> supportedTokens) {
                        Loois.log("fetch supported tokens success: " + supportedTokens.size());
                        supportedTokens.clear();
                        supportedTokens.addAll(supportedTokens);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        Loois.log("fetch supported tokens failed: " + throwable.getLocalizedMessage());
                    }
                });
    }

    public SupportedToken getSupportedTokenBySymbol(String symbol) {
        for (SupportedToken token: supportedTokens) {
            if (token.symbol.equalsIgnoreCase(symbol)) {
                return token;
            }
        }
        return null;
    }
}
