package org.loois.dapp.manager;

import org.loois.dapp.Loois;
import org.loois.dapp.common.Constants;
import org.loois.dapp.protocol.core.SocketListener;
import org.loois.dapp.protocol.core.response.Transaction;
import org.loois.dapp.protocol.core.socket.SocketPendingTx;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.utils.StringUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class PendingTxManager {

    private boolean isListening = false;
    private List<Transaction> pendingTxs = new ArrayList<>();

    private List<String> txTypes = Arrays.asList(
            "send",
            "cancel_order",
            "cutoff_trading_pair",
            "cutoff",
            "approve",
            "convert_outcome"
    );

    public void startGetPending(String address) {
        if (!isListening) {
            Loois.socket().onPendingTx(address);
            Loois.socket().registerPendingTxListener(socketListener);
            isListening = true;
        }
    }

    private SocketListener socketListener = new SocketListener() {
        @Override
        public void onPendingTx(SocketPendingTx result) {
            List<Transaction> pendingTx = result.getPendingTxs();
            if (pendingTx != null && !pendingTx.isEmpty()) {
                pendingTx.clear();
                Collections.sort(pendingTx, (o1, o2) -> o1.createTime - o2.createTime);
                pendingTxs.addAll(pendingTx);
            }

        }
    };

    public void stopGetPending() {
        if (isListening) {
            Loois.socket().offPendingTx();
            Loois.socket().removePendingTxListener(socketListener);
            isListening = false;
        }
    }

    public void restartPending(String address) {
        stopGetPending();
        startGetPending(address);
    }

    public List<Transaction> getPendingTxs() {
        return this.pendingTxs;
    }

    public Transaction getLatestPendingTx() {
        for (Transaction tx : pendingTxs) {
            if (txTypes.contains(tx.type)) {
                return tx;
            }
        }
        return null;
    }

    public List<Transaction> getPositivePendingTx() {
        List<Transaction> list = new ArrayList<>();
        for (Transaction pendingTx : pendingTxs) {
            if (txTypes.contains(pendingTx.type)) {
                list.add(pendingTx);
            }
        }
        return list;
    }

    public void registerPendingTxListener(SocketListener socketListener) {
        Loois.socket().registerPendingTxListener(socketListener);
    }

    public void removePendingTxListener(SocketListener socketListener) {
        Loois.socket().removePendingTxListener(socketListener);
    }


    public Flowable<BigInteger> getNonce(Flowable<String> flowable) throws NumberFormatException {
        return flowable.map((Function<String, Response<String>>) s ->
                Loois.web3j().ethGetTransactionCount(s, DefaultBlockParameterName.PENDING).sendAsync().get())
                .compose(RxResultHelper.handleResult())
                .flatMap((Function<String, Flowable<BigInteger>>) nonceString -> {
                    Integer nonceValue;
                    if (StringUtils.isHex(nonceString)) {
                        nonceValue = Integer.parseInt(nonceString.substring(Constants.PREFIX_16.length()), Constants.PRIVATE_KEY_RADIX);
                    } else {
                        nonceValue = Integer.parseInt(nonceString);
                    }
                    int actualNonce = nonceValue;
                    if (pendingTxs.size() > 0) {
                        int smallestNonceInPending = Integer.parseInt(getPositivePendingTx().get(0).nonce);
                        actualNonce = smallestNonceInPending > nonceValue ? nonceValue : nonceValue + pendingTxs.size();
                    }
                    return Flowable.just(BigInteger.valueOf(actualNonce));
                });
    }

    public static PendingTxManager shared() {
        return PendingTxManager.Holder.singleton;
    }

    private PendingTxManager() {
    }

    private static class Holder {

        private static final PendingTxManager singleton = new PendingTxManager();

    }
}
