package org.loois.dapp.manager;

import org.loois.dapp.Loois;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.protocol.core.params.NotifyTransactionSubmittedParams;
import org.loois.dapp.protocol.core.response.LooisNotifyTransactionSubmitted;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.rx.ScheduleCompat;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class TransactionManager {


    public void sendETHTransaction(String to,
                                   BigInteger nonce,
                                   BigInteger gasPrice,
                                   BigInteger gasLimit,
                                   BigDecimal amountEther,
                                   String password,
                                   HDWallet wallet,
                                   OnTransactionListener listener) {
        Flowable.just(to)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String data = SignManager.shared().signETHTransaction(to, nonce, gasPrice, gasLimit, amountEther, wallet, password);
                    EthSendTransaction ethSendTransaction =
                            Loois.web3j().ethSendRawTransaction(data).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        listener.onSuccess(s);
                        notifyETHTransaction(s, nonce.intValue(), to, amountEther, gasPrice, gasLimit, wallet.address);

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        listener.onFailed(throwable);
                    }
                });
    }

    private void notifyETHTransaction(String hashTx, int nonce, String to, BigDecimal amountEther,
                                      BigInteger gasPrice, BigInteger gasLimit, String address) {
        Flowable.just(hashTx)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    BigDecimal amountWei = Convert.toWei(amountEther.toString(), Convert.Unit.ETHER);
                    NotifyTransactionSubmittedParams params =
                            new NotifyTransactionSubmittedParams(hashTx, nonce, to, amountWei.toBigInteger(),
                                    gasPrice, gasLimit, "0x", address);
                    LooisNotifyTransactionSubmitted looisNotifyTransactionSubmitted = Loois.client().looisNotifyTransactionSubmitted(params).sendAsync().get();
                    return Flowable.just(looisNotifyTransactionSubmitted);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String o) {
                    }

                    @Override
                    public void onFailed(Throwable throwable) {

                    }
                });
    }


    public void sendTokenTransaction(String contractAddress,
                                     BigDecimal decimal,
                                     String to,
                                     BigInteger nonce,
                                     BigInteger gasPrice,
                                     BigInteger gasLimit,
                                     BigDecimal amount,
                                     String password,
                                     HDWallet wallet,
                                     OnTransactionListener listener) {

        Flowable.just(to)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String data = SignManager.shared().signContractTransaction(contractAddress, to, nonce, gasPrice,
                            gasLimit, amount, decimal, wallet, password);
                    EthSendTransaction ethSendTransaction =
                            Loois.web3j().ethSendRawTransaction(data).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        listener.onSuccess(s);
                        notifyTokenTransaction(s, contractAddress, to, nonce.intValue(), gasPrice, gasLimit, wallet.address, amount, decimal);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        listener.onFailed(throwable);
                    }
                });

    }

    private void notifyTokenTransaction(String hashTx, String contractAddress, String to, int nonce, BigInteger gasPrice, BigInteger gasLimit, String address, BigDecimal amount, BigDecimal decimal) {
        Flowable.just(hashTx)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    BigInteger realValue = amount.multiply(decimal).toBigInteger();
                    String data = SignManager.encodeTransferFunction(to, realValue);
                    NotifyTransactionSubmittedParams params =
                            new NotifyTransactionSubmittedParams(hashTx, nonce, contractAddress, realValue,
                                    gasPrice, gasLimit, data, address);
                    LooisNotifyTransactionSubmitted looisNotifyTransactionSubmitted = Loois.client().looisNotifyTransactionSubmitted(params).sendAsync().get();
                    return Flowable.just(looisNotifyTransactionSubmitted);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String result) {
                    }

                    @Override
                    public void onFailed(Throwable throwable) {

                    }
                });
    }


    public interface OnTransactionListener {

        void onSuccess(String result);

        void onFailed(Throwable throwable);
    }


}
