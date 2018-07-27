package org.loois.dapp.manager;

import android.util.SparseArray;

import org.loois.dapp.LWallet;
import org.loois.dapp.Loois;
import org.loois.dapp.common.Params;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.model.OriginalOrder;
import org.loois.dapp.protocol.core.params.NotifyTransactionSubmittedParams;
import org.loois.dapp.rx.LooisError;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.rx.ScheduleCompat;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class TransactionManager {

    private SparseArray<NotifyTransactionSubmittedParams> pendingNotifications = new SparseArray<>();

    private void notifyTransactionSubmitted(String txHash, int nonce) {
        NotifyTransactionSubmittedParams pendingNotificationParams = pendingNotifications.get(nonce);
         if (pendingNotificationParams != null) {
            pendingNotificationParams.hash = txHash;
            Flowable.just(pendingNotificationParams)
                    .map((Function<NotifyTransactionSubmittedParams, Response<String>>) params ->
                            Loois.client().looisNotifyTransactionSubmitted(params).sendAsync().get())
                    .compose(RxResultHelper.handleResult())
                    .compose(ScheduleCompat.apply())
                    .subscribe(new LooisSubscriber<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Loois.log("notifyTransactionSubmitted success:" +  s);
                        }

                        @Override
                        public void onFailed(Throwable throwable) {
                            Loois.log("notifyTransactionSubmitted failed:" + throwable.getLocalizedMessage());
                        }
                    });
        }
    }

    public void sendETHTransaction(String to,
                                   BigInteger gasPrice,
                                   BigInteger gasLimit,
                                   BigDecimal amountEther,
                                   String password,
                                   HDWallet wallet,
                                   LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password))
                .subscribe(new LooisSubscriber<BigInteger>() {
                    @Override
                    public void onSuccess(BigInteger nonce) {
                        sendETHTransaction(to, nonce, gasPrice, gasLimit, amountEther, password, wallet, listener);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }



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
                    SignManager.SignModel signModel = SignManager.shared().signETHTransaction(to, nonce, gasPrice, gasLimit, amountEther, wallet, password);
                    EthSendTransaction ethSendTransaction =
                            Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendTokenTransaction(String contractAddress,
                                     BigDecimal decimal,
                                     String to,
                                     BigInteger gasPrice,
                                     BigInteger gasLimit,
                                     BigDecimal amount,
                                     String password,
                                     HDWallet wallet,
                                     LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendTokenTransaction(contractAddress, decimal, to, nonce, gasPrice, gasLimit, amount, password, wallet, listener);
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (listener != null) {
                    listener.onFailed(throwable);
                }
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
                    SignManager.SignModel signModel = SignManager.shared().signContractTransaction(contractAddress, to, nonce, gasPrice,
                            gasLimit, amount, decimal, wallet, password);
                    EthSendTransaction ethSendTransaction =
                            Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });

    }

    public void sendETHToWETHTransaction(BigInteger gasPrice,
                                         BigInteger gasLimit,
                                         HDWallet wallet,
                                         String password,
                                         BigInteger amount,
                                         LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendETHToWETHTransaction(gasPrice, gasLimit, nonce, wallet, password, amount, listener);
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (listener != null) {
                    listener.onFailed(throwable);
                }
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
                    SignManager.SignModel signModel = SignManager.shared().signDeposit(gasPrice, gasLimit, nonce, wallet, password, amount);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
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
                                         HDWallet wallet,
                                         String password,
                                         BigInteger amount,
                                         LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendWETHToETHTransaction(gasPrice, gasLimit, nonce, wallet, password, amount, listener);
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
                    SignManager.SignModel signModel = SignManager.shared().signWithdraw(gasPrice, gasLimit, nonce, wallet, password, amount);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendBindNeoTransaction(String neoAddress, HDWallet wallet, String password, LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendBindNeoTransaction(neoAddress, nonce, wallet, password, listener);
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (listener != null) {
                    listener.onFailed(throwable);
                }
            }
        });
    }

    public void sendBindNeoTransaction(String neoAddress, BigInteger nonce, HDWallet wallet, String password, LooisListener listener) {
        Flowable.just(neoAddress)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    BigInteger gasPriceWei = Convert.toWei(String.valueOf(Params.DEFAULT_GAS), Convert.Unit.GWEI).toBigInteger();
                    SignManager.SignModel signModel = SignManager.shared().signBindNeo(gasPriceWei, Params.GasLimit.bind, nonce, wallet, password, neoAddress);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendBindQutmTransaction(String qutmAddress, HDWallet wallet, String password, LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendBindQutmTransaction(qutmAddress, nonce, wallet, password, listener);
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
                    SignManager.SignModel signModel = SignManager.shared().signBindQutm(gasPriceWei, Params.GasLimit.bind, nonce, wallet, password, qutmAddress);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
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
                                             HDWallet wallet,
                                             String password,
                                             LooisListener listener){
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendDoubleApproveTransaction(tokenProtocal, gasPrice, gasLimit, nonce, wallet, password, listener);
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
                    SignManager.SignModel signModel = SignManager.shared().signApproveZero(tokenProtocal, wallet, nonce, gasPrice, gasLimit, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
                    return Flowable.just(ethSendTransaction);
                })
                .flatMap((Function<Response<String>, Flowable<Response<String>>>) stringResponse -> {
                    if (stringResponse.hasError()) {
                        return Flowable.error(new LooisError(stringResponse.getError().getMessage(), stringResponse.getError().getCode()));
                    }
                    BigInteger newNonce = nonce.add(new BigInteger("1"));
                    SignManager.SignModel signModel = SignManager.shared().signApproveMax(tokenProtocal, wallet, newNonce, gasPrice, gasLimit, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(newNonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        BigInteger newNonce = nonce.add(new BigInteger("1"));
                        notifyTransactionSubmitted(s, newNonce.intValue());
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
                                             HDWallet wallet,
                                             String password,
                                             LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendSingleApproveTransaction(tokenProtocal, gasPrice, gasLimit, nonce, wallet, password, listener);
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
                    SignManager.SignModel signModel = SignManager.shared().signApproveMax(tokenProtocal, wallet, nonce, gasPrice, gasLimit, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }


    public void sendCancelSingleOrderTransaction(OriginalOrder order, String sellTokenProtocol, String buyTokenProtocol,
                                                 HDWallet wallet, String password,
                                                 LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendCancelSingleOrderTransaction(order, sellTokenProtocol, buyTokenProtocol, nonce, wallet, password, listener);
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
        BigInteger gasLimit = Params.GasLimit.cancelOrder;
        sendCancelSingleOrderTransaction(order, sellTokenProtocol, buyTokenProtocol, nonce, gasPriceWei, gasLimit, wallet, password, listener);
    }


    public void sendCancelSingleOrderTransaction(OriginalOrder order, String sellTokenProtocol, String buyTokenProtocol, BigInteger nonce,
                                                 BigInteger gasPriceWei, BigInteger gasLimit, HDWallet wallet, String password,
                                                 LooisListener listener) {
        Flowable.just(order)
                .flatMap((Function<OriginalOrder, Flowable<Response<String>>>) originalOrder -> {
                    SignManager.SignModel signModel = SignManager.shared()
                            .signedCancelOrder(sellTokenProtocol, buyTokenProtocol, order, nonce, gasPriceWei, gasLimit, wallet, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendCancelAllOrdersTransaction(BigInteger gasPriceWei, BigInteger gasLimit,
                                               HDWallet wallet, String password, LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendCancelAllOrdersTransaction(nonce, gasPriceWei, gasLimit, wallet, password, listener);
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
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    long timestamp = System.currentTimeMillis() / 1000;
                    SignManager.SignModel signModel = SignManager.shared().signCancelAllOrders(timestamp, gasPriceWei, gasLimit, nonce, wallet, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public void sendCancelMarketOrdersTransaction(BigInteger gasPriceWei, BigInteger gasLimit, String protocolB,
                                                  String protocolS, String password, HDWallet wallet, LooisListener listener) {
        PendingTxManager.shared().getNonce(getValidatePasswordFlowable(wallet, password)).subscribe(new LooisSubscriber<BigInteger>() {
            @Override
            public void onSuccess(BigInteger nonce) {
                sendCancelMarketOrdersTransaction(nonce, gasPriceWei, gasLimit, protocolB, protocolS, password, wallet, listener);
            }

            @Override
            public void onFailed(Throwable throwable) {
                listener.onFailed(throwable);
            }
        });

    }


    public void sendCancelMarketOrdersTransaction(BigInteger nonce, BigInteger gasPriceWei, BigInteger gasLimit,
                                                  String protocolB, String protocolS, String password, HDWallet wallet, LooisListener listener) {
        Flowable.just(password)
                .flatMap((Function<String, Flowable<Response<String>>>) s -> {
                    SignManager.SignModel signModel = SignManager.shared().signCancelTokenPairOrders(protocolB, protocolS, gasPriceWei, gasLimit, nonce, wallet, password);
                    EthSendTransaction ethSendTransaction = Loois.web3j().ethSendRawTransaction(signModel.sign).sendAsync().get();
                    pendingNotifications.put(nonce.intValue(), signModel.notifyTransactionSubmittedParams);
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
                        notifyTransactionSubmitted(s, nonce.intValue());
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }

    public boolean validatePassword(HDWallet wallet, String password) throws CipherException {
        if (wallet.getWalletFile() != null) {
            ECKeyPair keyPair = LWallet.decrypt(password, wallet.getWalletFile());
            WalletFile generateWalletFile = Wallet.createLight(password, keyPair);
            return wallet.getWalletFile().getAddress().equalsIgnoreCase(generateWalletFile.getAddress());
        }
        return false;
    }

    public Flowable<String> getValidatePasswordFlowable(HDWallet wallet, String password) {
        return Flowable.just(wallet.address)
                .filter(s -> validatePassword(wallet, password))
                .map(s -> s);
    }
}
