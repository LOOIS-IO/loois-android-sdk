package org.loois.dapp;

import android.util.Log;

import org.loois.dapp.manager.TransactionManager;
import org.loois.dapp.protocol.Config;
import org.loois.dapp.protocol.LooisApi;
import org.loois.dapp.protocol.LooisSocketApi;
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

    private static boolean enableDebugLog = true;
    private static byte sChainId = ChainId.MAINNET;



    public static void initialize() {
        initialize(Config.BASE_URL, ChainId.MAINNET);
    }

    public static void initialize(String url, byte chainId) {
        httpService = new HttpService(url);
        sChainId = chainId;
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

    public static TransactionManager transactionManager() {
        if (transactionManager == null) {
            synchronized (TransactionManager.class) {
                if (transactionManager == null) {
                    transactionManager = new TransactionManager();
                }
            }
        }
        return transactionManager;
    }

    public static void setDebugLogEnabled(boolean enabled) {
        enableDebugLog = enabled;
    }

    public static void log(String msg) {
        if (enableDebugLog) {
            Log.d(TAG, msg);
        }
    }

    public static byte getChainId() {
        return sChainId;
    }
}
