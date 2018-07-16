package org.loois.dapp;

import android.text.TextUtils;

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

    public static final String DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";

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
}
