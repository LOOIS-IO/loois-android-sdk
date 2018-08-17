package org.loois.dapp;

import android.util.Log;

import org.loois.dapp.manager.OrderManager;
import org.loois.dapp.manager.TokenManager;
import org.loois.dapp.manager.TransactionManager;
import org.loois.dapp.manager.WalletManager;
import org.loois.dapp.protocol.LooisConfig;
import org.loois.dapp.protocol.LooisApi;
import org.loois.dapp.protocol.LooisSocketApi;
import org.loois.dapp.protocol.Options;
import org.loois.dapp.protocol.core.LooisApiImpl;
import org.loois.dapp.protocol.core.LooisSocketImpl;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class Loois {

    private static final String TAG = "Loois";

    private static LooisApi looisApi;
    private static LooisSocketApi looisSocket;
    private static Web3j web3j;
    private static HttpService httpService;
    private static TransactionManager transactionManager;
    private static TokenManager tokenManager;
    private static OrderManager orderManager;
    private static WalletManager walletManager;
    private static Options options;

    private static boolean enableDebugLog = true;

    private static final String RPC_V2 = "rpc/v2/";


    public static void initialize() {
        initialize(new Options());
    }

    public static void initialize(Options opt) {
        options = opt;
        httpService = new HttpService(options.getBaseUrl() + RPC_V2);
    }


    public static LooisApi client() {
        if (looisApi == null) {
            synchronized (Loois.class) {
                if (looisApi == null) {
                    looisApi = new LooisApiImpl(httpService);
                }
            }
        }
        return looisApi;
    }

    public static LooisSocketApi socket() {
        if (looisSocket == null) {
            synchronized (Loois.class) {
                if (looisSocket == null) {
                    looisSocket = new LooisSocketImpl();
                }
            }
        }
        return looisSocket;
    }

    public static Web3j web3j() {
        if (web3j == null) {
            synchronized (Loois.class) {
                if (web3j == null) {
                    web3j = Web3jFactory.build(httpService);
                }
            }
        }
        return web3j;
    }

    public static TransactionManager transaction() {
        if (transactionManager == null) {
            synchronized (Loois.class) {
                if (transactionManager == null) {
                    transactionManager = new TransactionManager();
                }
            }
        }
        return transactionManager;
    }

    public static TokenManager token() {
        if (tokenManager == null) {
            synchronized (Loois.class) {
                if (tokenManager == null) {
                    tokenManager = new TokenManager();
                }
            }
        }
        return tokenManager;
    }

    public static OrderManager order() {
        if (orderManager == null) {
            synchronized (Loois.class) {
                if (orderManager == null) {
                    orderManager = new OrderManager();
                }
            }
        }
        return orderManager;
    }

    public static WalletManager wallet() {
        if (walletManager == null) {
            synchronized (Loois.class) {
                if (walletManager == null) {
                    walletManager = new WalletManager();
                }
            }
        }
        return walletManager;
    }

    public static void setDebugLogEnabled(boolean enabled) {
        enableDebugLog = enabled;
    }

    public static void log(String msg) {
        if (enableDebugLog) {
            Log.d(TAG, msg);
        }
    }

    public static Options getOptions() {
        return options;
    }
}
