package org.loois.dapp;

import android.text.TextUtils;

import org.loois.dapp.protocol.LooisApi;
import org.loois.dapp.protocol.LooisSocketApi;
import org.loois.dapp.protocol.core.LooisApiImpl;
import org.loois.dapp.protocol.core.LooisSocketImpl;
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

    private static String tag = "Loois";

    public static byte chainId = ChainId.MAINNET;

    private static LooisApi looisApi;
    private static LooisSocketApi looisSocket;


    private static ILogger mLogger = (tag, msg) -> {
        // do nothing by default
    };

    public static void log(String msg){
        if (mLogger != null){
            mLogger.log(tag,msg);
        }
    }

    public static class Builder{

        public Builder initLogger(ILogger logger,String tag){
            mLogger = logger;
            Loois.tag = TextUtils.isEmpty(tag)?Loois.tag:tag;
            return this;
        }

        public Builder chainId(byte chainId){
            Loois.chainId = chainId;
            return this;
        }
    }

    public static LooisApi client() {
        if (looisApi == null) {
            synchronized (Loois.class) {
                if (looisApi == null) {
                    looisApi = new LooisApiImpl(new HttpService());
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
}
