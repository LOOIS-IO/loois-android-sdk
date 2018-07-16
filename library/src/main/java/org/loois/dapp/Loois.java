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

    /**
     * The loopring TokenTransferDelegate Protocol.
     */
    public static final String DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";

    /**
     * Loopring contract address
     */
    public static final String PROTOCAL_ADDRESS = "0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78";

    public static final String WETH_ADDRESS = "0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2";

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
