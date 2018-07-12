package org.loois.dapp;

import android.text.TextUtils;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class Loois {

    private static String tag = "Loois";

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

    }
}
