package org.loois.dapp;

import org.junit.Test;
import org.loois.dapp.protocol.Loois;
import org.loois.dapp.protocol.LooisFactory;
import org.loois.dapp.protocol.core.params.BalanceParams;
import org.loois.dapp.protocol.core.params.CutoffParams;
import org.loois.dapp.protocol.core.params.DepthParams;
import org.loois.dapp.protocol.core.params.EstimatedAllocatedAllowanceParams;
import org.loois.dapp.protocol.core.params.FrozenLRCFeeParams;
import org.loois.dapp.protocol.core.params.NotifyTransactionSubmittedParams;
import org.loois.dapp.protocol.core.params.PriceQuoteParams;
import org.loois.dapp.protocol.core.params.RingMinedParams;
import org.loois.dapp.protocol.core.params.SupportedTokensParams;
import org.loois.dapp.protocol.core.params.TickersParams;
import org.loois.dapp.protocol.core.params.TransactionParams;
import org.loois.dapp.protocol.core.params.TrendParams;
import org.loois.dapp.protocol.core.params.UnlockWalletParams;
import org.loois.dapp.protocol.core.response.LooisCutoff;
import org.loois.dapp.protocol.core.response.LooisEstimatedAllocatedAllowance;
import org.loois.dapp.protocol.core.response.LooisFrozenLRCFee;
import org.loois.dapp.protocol.core.response.LooisPriceQuote;
import org.loois.dapp.protocol.core.response.LooisSupportedMarket;
import org.loois.dapp.protocol.core.response.LooisSupportedTokens;
import org.loois.dapp.protocol.core.response.LooisTicker;
import org.loois.dapp.protocol.core.response.LooisTransactions;
import org.loois.dapp.protocol.core.response.LooisTrend;
import org.loois.dapp.protocol.core.response.LooisUnlockWallet;
import org.loois.dapp.protocol.core.response.Ring;
import org.loois.dapp.protocol.core.response.TickersResult;
import org.loois.dapp.protocol.core.response.Token;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class LooisTest {

    public static final String TEST_URL = "http://192.168.1.100:8083/";
    public static final String TEST_WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";
    public static final String TEST_DELEGATE_ADDRESS = "0xc28766b782ad2873d7c39a725b88df6c0e753940";

    public static final String LOOPRING_URL = "https://relay1.loopring.io/rpc/v2/";
    public static final String LOOPRING_DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";
    public static final String WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";

    public static final String LOOIS_URL = "https://api.loois.io/rpc/v2/";
    public static final String LOOIS_TEST_URL = "https://loois.tech/rpc/v2/";


    @Test
    public void testLooisBalance() {
        Loois loois = LooisFactory.build(new HttpService(TEST_URL));
        try {
            BalanceParams params = new BalanceParams(TEST_WALLET_ADDRESS, TEST_DELEGATE_ADDRESS);
            List<Token> tokens = loois.looisBalance(params).send().getTokens();
            System.out.println("testLoopringBalance " + tokens.size());

        } catch (IOException e) {
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

    @Test
    public void testRingMined() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        List<Ring> minedRings = loois.looisRingMined(new RingMinedParams(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238",
                LOOPRING_DELEGATE_ADDRESS,
                1, 20)).sendAsync().get().getMinedRings();
        log("testRingMined", minedRings.get(0).toString());

    }

    @Test
    public void testCutoff() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisCutoff latest = loois.looisCutoff(new CutoffParams(WALLET_ADDRESS,
                LOOPRING_DELEGATE_ADDRESS,
                "latest")).sendAsync().get();
        log("testCutoff", latest.getCutoffTimestamp());
    }

    @Test
    public void testPriceQuote() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOIS_URL));
        LooisPriceQuote cny = loois.looisPriceQuote(new PriceQuoteParams("CNY")).sendAsync().get();
        log("testPriceQuote", cny.getCurrency());
        log("testPriceQuote", cny.getPriceQuoteTokens().get(0).symbol);
    }

    @Test
    public void testEstimatedAllocatedAllowance() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOIS_URL));
        LooisEstimatedAllocatedAllowance lrc = loois.looisEstimatedAllocatedAllowance(
                new EstimatedAllocatedAllowanceParams(WALLET_ADDRESS, "NEO", LOOPRING_DELEGATE_ADDRESS)).sendAsync().get();
        log("testEstimatedAllocatedAllowance", lrc.getEstimatedAllocatedAllowance());
    }

    @Test
    public void testFrozenLRCFee() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisFrozenLRCFee looisFrozenLRCFee = loois.looisFrozenLRCFee(new FrozenLRCFeeParams(WALLET_ADDRESS)).sendAsync().get();
        log("testFrozenLRCFee", looisFrozenLRCFee.getProzenLRCFee());
    }

    @Test
    public void testSupportedMarket() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisSupportedMarket looisSupportedMarket = loois.looisSupportedMarket().sendAsync().get();
        log("testSupportedMarket", looisSupportedMarket.getSupportedMarket());
    }

    @Test
    public void testSupportedTokens() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisSupportedTokens looisSupportedTokens = loois.looisSupportedTokens(new SupportedTokensParams(WALLET_ADDRESS)).sendAsync().get();
        log("testSupportedTokens", looisSupportedTokens.getSupportedTokens().get(0).symbol);
    }


    @Test
    public void testTransactions() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisTransactions lrc = loois.looisTransactions(new TransactionParams(
                WALLET_ADDRESS, null, "LRC", null, null, 1, 20))
                .sendAsync().get();
        log("testTransactions", lrc.getTotal());

    }

    @Test
    public void testUnlockWallet() throws ExecutionException, InterruptedException {
        Loois loois = LooisFactory.build(new HttpService(LOOPRING_URL));
        LooisUnlockWallet wallet = loois.looisUnlockWallet(new UnlockWalletParams(
                WALLET_ADDRESS)).sendAsync().get();
        log("testUnlockWallet", wallet.getResult());

    }



    public static void log(String tag, String msg) {
        System.out.println(tag + " : " + msg);
    }

    public static void log(String tag, int msg) {
        System.out.println(tag + " : " + String.valueOf(msg));
    }

    public static void log(String tag, List<String> result) {
        System.out.println(tag + ":");
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
