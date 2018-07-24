package org.loois.dapp.manager;

import org.loois.dapp.Loois;
import org.loois.dapp.common.Params;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.model.OriginalOrder;
import org.loois.dapp.protocol.Config;
import org.loois.dapp.protocol.core.params.NotifyTransactionSubmittedParams;
import org.loois.dapp.protocol.core.response.LooisNotifyTransactionSubmitted;
import org.loois.dapp.rx.LooisError;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.rx.ScheduleCompat;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

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
                                   LooisListener listener) {
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
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        notifyETHTransaction(s, nonce.intValue(), to, amountEther, gasPrice, gasLimit, wallet.address);

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
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
                                     LooisListener listener) {

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
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        notifyTokenTransaction(s, contractAddress, to, nonce.intValue(), gasPrice, gasLimit, wallet.address, amount, decimal);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
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


    public void sendETHToWETHTransaction(BigInteger gasPrice,
                                         BigInteger gasLimit,
                                         BigInteger nonce,
                                         HDWallet wallet,
                                         String password,
                                         BigInteger amount,
                                         LooisListener listener) {
        Flowable.just(password)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String signDeposit = SignManager.shared().signDeposit(gasPrice, gasLimit, nonce, wallet, password, amount);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signDeposit).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        String data = Params.Abi.deposit;
                        notifyTokenExchangeSubmitted(s, nonce.intValue(), Config.PROTOCAL_ADDRESS, gasPrice, gasLimit, data, wallet.address);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendWETHToETHTransaction(BigInteger gasPrice,
                                         BigInteger gasLimit,
                                         BigInteger nonce,
                                         HDWallet wallet,
                                         String password,
                                         BigInteger amount,
                                         LooisListener listener) {
        Flowable.just(password)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String signWithdraw = SignManager.shared().signWithdraw(gasPrice, gasLimit, nonce, wallet, password, amount);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signWithdraw).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        String data = Params.Abi.withdraw + Numeric.toHexStringNoPrefixZeroPadded(amount, 64);
                        notifyTokenExchangeSubmitted(s, nonce.intValue(), Config.PROTOCAL_ADDRESS, gasPrice, gasLimit, data, wallet.address);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    private void notifyTokenExchangeSubmitted(String txHash, int nonce, String protocolAddress, BigInteger gasPrice, BigInteger gasLimit, String data, String address) {
        Flowable.just(txHash)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    NotifyTransactionSubmittedParams params = new NotifyTransactionSubmittedParams(txHash, nonce, protocolAddress, BigInteger.ZERO, gasPrice, gasLimit, data, address);
                    LooisNotifyTransactionSubmitted looisNotifyTransactionSubmitted = Loois.client().looisNotifyTransactionSubmitted(params).sendAsync().get();
                    return Flowable.just(looisNotifyTransactionSubmitted);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {

                    }

                    @Override
                    public void onFailed(Throwable throwable) {

                    }
                });
    }

    public void sendBindNeoTransaction(String neoAddress, BigInteger nonce, HDWallet wallet, String password, LooisListener listener) {
        Flowable.just(neoAddress)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    BigInteger gasPriceWei = Convert.toWei(String.valueOf(Params.DEFAULT_GAS), Convert.Unit.GWEI).toBigInteger();
                    String signBindNeo = SignManager.shared().signBindNeo(gasPriceWei, Params.GasLimit.bind, nonce, wallet, password, neoAddress);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signBindNeo).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                            //TODO: notify submit
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendBindQutmTransaction(String qutmAddress, BigInteger nonce, HDWallet wallet, String password, LooisListener listener) {
        Flowable.just(qutmAddress)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    BigInteger gasPriceWei = Convert.toWei(String.valueOf(Params.DEFAULT_GAS), Convert.Unit.GWEI).toBigInteger();
                    String signBindNeo = SignManager.shared().signBindQutm(gasPriceWei, Params.GasLimit.bind, nonce, wallet, password, qutmAddress);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signBindNeo).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        //TODO: notify submit
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendDoubleApproveTransaction(String tokenProtocal,
                                             BigInteger gasPrice,
                                             BigInteger gasLimit,
                                             BigInteger nonce,
                                             HDWallet wallet,
                                             String password,
                                             LooisListener listener) {
        Flowable.just(password)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String signApproveZero = SignManager.shared().signApproveZero(tokenProtocal, wallet, nonce, gasPrice, gasLimit, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signApproveZero).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .flatMap((Function<Response<String>, Flowable<Response<String>>>) stringResponse -> {
                    if (stringResponse.hasError()) {
                        return Flowable.error(new LooisError(stringResponse.getError().getMessage(), stringResponse.getError().getCode()));
                    }
                    //TODO: notify submit
                    BigInteger newNonce = nonce.add(new BigInteger("1"));
                    String signApproveMax = SignManager.shared().signApproveMax(tokenProtocal, wallet, newNonce, gasPrice, gasLimit, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signApproveMax).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        //TODO: notify submit
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendSingleApproveTransaction(String tokenProtocal,
                                             BigInteger gasPrice,
                                             BigInteger gasLimit,
                                             BigInteger nonce,
                                             HDWallet wallet,
                                             String password,
                                             LooisListener listener) {
        Flowable.just(password)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String signApproveZero = SignManager.shared().signApproveMax(tokenProtocal, wallet, nonce, gasPrice, gasLimit, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signApproveZero).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        //TODO: notify submit
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }


    public void sendCancelSingleOrderTransaction(OriginalOrder order, String sellTokenProtocol, String buyTokenProtocol, BigInteger nonce,
                                                  HDWallet wallet, String password,
                                                 LooisListener listener) {
        BigInteger gasPriceWei = Convert.toWei(String.valueOf(BigInteger.valueOf(Params.DEFAULT_GAS)), Convert.Unit.GWEI).toBigInteger();
        BigInteger gasLimit= Params.GasLimit.cancelOrder;
        sendCancelSingleOrderTransaction(order, sellTokenProtocol, buyTokenProtocol, nonce, gasPriceWei, gasLimit, wallet, password, listener);
    }


    public void sendCancelSingleOrderTransaction(OriginalOrder order, String sellTokenProtocol, String buyTokenProtocol, BigInteger nonce,
                                                 BigInteger gasPriceWei, BigInteger gasLimit, HDWallet wallet, String password,
                                                 LooisListener listener) {
        Flowable.just(order)
                .flatMap(new Function<OriginalOrder, Flowable<Response<String>>>() {
                    @Override
                    public Flowable<Response<String>> apply(OriginalOrder originalOrder) throws Exception {
                        String signedCancelOrder = SignManager.shared()
                                .signedCancelOrder(sellTokenProtocol, buyTokenProtocol, order, nonce, gasPriceWei, gasLimit, wallet, password);
                        EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signedCancelOrder).sendAsync().get();
                        return Flowable.just(ethSendTransaction);
                    }
                })
                .compose(RxResultHelper.handleResult())
                .compose(ScheduleCompat.apply())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        //TODO: notify submit
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendCancelAllOrdersTransaction(BigInteger nonce, BigInteger gasPriceWei, BigInteger gasLimit,
                                               HDWallet wallet, String password, LooisListener listener) {
        Flowable.just(password)
                .flatMap(new Function<String, Flowable<Response<String>>>() {
                    @Override
                    public Flowable<Response<String>> apply(String s) throws Exception {
                        long timestamp = System.currentTimeMillis() / 1000;
                        String signCancelAllOrders = SignManager.shared().signCancelAllOrders(timestamp, gasPriceWei, gasLimit, nonce, wallet, password);
                        EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signCancelAllOrders).sendAsync().get();
                        return Flowable.just(ethSendTransaction);
                    }
                })
                .compose(RxResultHelper.handleResult())
                .compose(ScheduleCompat.apply())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        //TODO: notify submit

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendCancelMarketOrdersTransaction(BigInteger nonce, String protocolB, String protocolS, String password, HDWallet wallet, LooisListener listener) {
        BigInteger gasPriceWei = Convert.toWei(String.valueOf(BigInteger.valueOf(Params.DEFAULT_GAS)), Convert.Unit.GWEI).toBigInteger();
        BigInteger gasLimit = Params.GasLimit.cancelOrderByTokenPair;
        sendCancelMarketOrdersTransaction(nonce, gasPriceWei, gasLimit, protocolB, protocolS, password, wallet, listener);
    }


    public void sendCancelMarketOrdersTransaction(BigInteger nonce, BigInteger gasPriceWei, BigInteger gasLimit,
                                                  String protocolB, String protocolS, String password, HDWallet wallet, LooisListener listener) {
        Flowable.just(password)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    String signed = SignManager.shared().signCancelTokenPairOrders(protocolB, protocolS, gasPriceWei, gasLimit, nonce, wallet, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signed).sendAsync().get();
                    return Flowable.just(ethSendTransaction);
                })
                .compose(RxResultHelper.handleResult())
                .compose(ScheduleCompat.apply())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (listener != null) {
                            listener.onSuccess(s);
                        }
                        //TODO: notify submit
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

}
