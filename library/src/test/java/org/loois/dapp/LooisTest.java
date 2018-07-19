package org.loois.dapp;

import org.junit.Test;
import org.loois.dapp.protocol.Loois;
import org.loois.dapp.protocol.LooisFactory;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.params.TickersParams;
import org.loois.dapp.protocol.core.params.TrendParams;
import org.loois.dapp.protocol.core.response.LooisTicker;
import org.loois.dapp.protocol.core.response.LooisTrend;
import org.loois.dapp.protocol.core.response.TickersResult;
import org.loois.dapp.protocol.core.response.Token;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LooisTest {

    public static final String TEST_URL = "http://192.168.1.100:8083/";
    public static final String TEST_WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";
    public static final String TEST_DELEGATE_ADDRESS = "0xc28766b782ad2873d7c39a725b88df6c0e753940";

    public static final String LOOPRING_URL = "https://relay1.loopring.io/rpc/v2/";
    public static final String LOOPRING_DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";

    public static final String LOOIS_URL = "https://api.loois.io/rpc/v2/";
    public static final String LOOIS_TEST_URL = "https://loois.tech/rpc/v2/";


    @Test
    public void testLooisBalance() {
        Loois loois = LooisFactory.build(new HttpService(TEST_URL));
        try {
            BalanceParams params = new BalanceParams(TEST_WALLET_ADDRESS, TEST_DELEGATE_ADDRESS);
            List<Token> tokens = loois.looisBalance(params).send().getTokens();
            System.out.println("testLoopringBalance " + tokens.size());

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLooisDepth() {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        DepthParams params = new DepthParams("LRC-WETH", 20, LOOPRING_DELEGATE_ADDRESS);
        try {
            List<List<String>> sellList = loois.looisDepth(params).sendAsync().get().getSellList();
            String market = loois.looisDepth(params).sendAsync().get().getMarket();
            System.out.println(market);
            System.out.println(sellList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLooisTicker() {
        Loois loois = LooisFactory.build(new HttpService(LOOIS_TEST_URL));
        try {
            LooisTicker looisTicker = loois.looisTicker().sendAsync().get();
            System.out.println(looisTicker.getMarkets().size());
            System.out.println(looisTicker.getMarkets().get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLooisTickers() {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        try {
            TickersResult.MarketTicker binanceMarketTicker = loois
                    .looisTickers(new TickersParams("LRC-WETH")).sendAsync().get().getBinanceMarketTicker();
            log("testLooisTickers ", binanceMarketTicker.change);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testLooisTrend() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisTrend looisTrend = loois.looisTrend(new TrendParams("LRC-WETH", "1Day")).sendAsync().get();
        log("testLooisTrend", looisTrend.getTrend().get(0).toString());
    }


    public static void log(String tag, String msg) {
        System.out.println(tag + ":" + msg);
    }
}
