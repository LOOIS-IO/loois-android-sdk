package org.loois.dapp.rx;

import org.web3j.protocol.core.Response;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class RxResultHelper {


    public static <T>FlowableTransformer<Response<T>,T> handleResult() {
        return upstream -> upstream.flatMap((Function<Response<T>, Flowable<T>>) tReply -> {
            if (tReply.hasError()) {
                Response.Error error = tReply.getError();
                return Flowable.error(new LooisError(error.getMessage(),error.getCode()));
            } else {
                return createData(tReply.getResult());
            }
        });
    }

    private static <T> Flowable<T> createData(T t){
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.DROP);
    }
}
