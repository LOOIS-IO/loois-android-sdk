package org.loois.dapp;

import org.junit.Before;
import org.junit.Test;
import org.loois.dapp.manager.InitWalletManager;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.model.OrderInfo;
import org.loois.dapp.protocol.Options;
import org.loois.dapp.protocol.core.params.SubmitOrderParams;
import org.loois.dapp.protocol.core.params.SupportedTokensParams;
import org.loois.dapp.protocol.core.response.SupportedToken;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.RxResultHelper;
import org.loois.dapp.rx.ScheduleCompat;
import org.web3j.crypto.CipherException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


public class OrderTest {


    private static final String BABY_BASE_URL = "https://loois.tech/rpc/v2";
    private static final String BABY_ETH_URL = "https://ropsten.infura.io/1UoO4I/";
    private static final String BABY_SOCKET_URL = "https://loois.tech/";
    private static final String BABY_LPR_DELEGATE_ADDRESS = "0xF036a52C13E1f749B50BDB6590D8aD42D2eb4e0d";
    private static final String BABY_LPR_PROTOCAL_ADDRESS = "0x9636B51d4DF170C623241eD70697aA776BE3Ad43";
    private static final String BABY_ORDER_WALLET_ADDRESS = "0x1D3e0DDFdc3D597C4f42b8a0F17d4e8C866B3485";
    private static final byte BABY_CHAIN_ID = 3;
    private String PRIVATE_KEY = "7FCA79052CAD5462DFCF6F65E32BF64FC7AC48A0D311541DBEEE24A2E69A5FD1";
    private String PASSWORD = "a12345678";
    private HDWallet mHDWallet;
    private List<SupportedToken> mSupportedTokens = new ArrayList<>();

    @Before
    public void on() {
        asyncToSync();
        Options options = new Options();
        options.setBaseUrl(BABY_BASE_URL);
        options.setChainId(BABY_CHAIN_ID);
        Loois.initialize(options);
        try {
            mHDWallet = InitWalletManager.shared().importPrivateKey(PRIVATE_KEY, PASSWORD);
            getSupportToken();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSupportToken() {
        Flowable.just(mHDWallet.address)
                .map(s -> Loois.client().looisSupportedTokens(new SupportedTokensParams(s)).send())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<List<SupportedToken>>() {
                    @Override
                    public void onSuccess(List<SupportedToken> tokens) {
                        System.out.println("fetch supported tokens success: " + tokens.size());
                        mSupportedTokens.clear();
                        mSupportedTokens.addAll(tokens);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        System.out.println("fetch supported tokens failed: " + throwable.getLocalizedMessage());
                    }
                });
    }


    private static void asyncToSync() {
        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void testOrder() {
        OrderInfo orderInfo = createOrderInfo();
        Flowable.just(PRIVATE_KEY)
                .map(s -> {
                    SubmitOrderParams submitOrderParams = Loois.order().createSubmitOrderParams(
                            BABY_LPR_PROTOCAL_ADDRESS, BABY_LPR_DELEGATE_ADDRESS, s.toLowerCase(), orderInfo,
                            true, 50, BABY_ORDER_WALLET_ADDRESS);
                    return Loois.client().looisSubmitOrder(submitOrderParams).sendAsync().get();
                })
                .compose(ScheduleCompat.apply())
                .compose(RxResultHelper.handleResult())
                .subscribe(new LooisSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        System.out.println("testOrder success " + "submit order success " + s);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        System.out.println("testOrder failed: " + throwable.getLocalizedMessage());
                    }
                });
    }

    private OrderInfo createOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.tokenB = "VITE";
        orderInfo.tokenS = "WETH";
        SupportedToken supportedTokenB = getSupportedTokenBySymbol(orderInfo.tokenB);
        SupportedToken supportedTokenS = getSupportedTokenBySymbol(orderInfo.tokenS);

        orderInfo.owner = mHDWallet.address;
        orderInfo.amountB = "100";
        orderInfo.amountS = String.valueOf(100 *  0.0002);
        orderInfo.validateDuration = 24 * 60 * 60;
        orderInfo.decimalB = supportedTokenB.decimals.toPlainString();
        orderInfo.decimalS = supportedTokenS.decimals.toPlainString();
        orderInfo.lrcDecimals = new BigDecimal("1000000000000000000");
        orderInfo.lrcFee = "0.83";
        orderInfo.protocolB = supportedTokenB.protocol;
        orderInfo.protocolS = supportedTokenS.protocol;
        return orderInfo;
    }

    public SupportedToken getSupportedTokenBySymbol(String symbol) {
        for (SupportedToken token: mSupportedTokens) {
            if (token.symbol.equalsIgnoreCase(symbol)) {
                return token;
            }
        }
        return null;
    }

}
