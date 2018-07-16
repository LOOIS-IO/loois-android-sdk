package org.loois.dapp.common;

public class Constants {

    public static String PREFIX_16 = "0x";

    interface Token {
        String ETH = "ETH";
        String LRC = "LRC";
        String WETH = "WETH";
    }

    interface TransferType {
        String CANCEL_ORDER = "cancel_order";
        String CUTOFF = "cutoff";
        String CUTOFF_TRADING_PAIR = "cutoff_trading_pair";
        String UNSUPPORTED_CONTRACT = "unsupported_contract";
        String RECEIVE = "receive";
        String SEND = "send";
        String APPROVE = "approve";
        String SELL = "sell";
        String BUY = "buy";
        String CONVERT_INCOME = "convert_income";
        String CONVERT_OUTCOME = "convert_outcome";
        String LRC_FEE = "lrc_fee";
    }


}
