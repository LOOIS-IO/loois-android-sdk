package org.loois.dapp;

public class LooisApiTest {

//    public static final String TEST_URL = "http://192.168.1.100:8083/";
//    public static final String TEST_WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";
//    public static final String TEST_DELEGATE_ADDRESS = "0xc28766b782ad2873d7c39a725b88df6c0e753940";
//
//    public static final String LOOPRING_URL = "https://relay1.loopring.io/rpc/v2/";
//    public static final String LOOPRING_DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";
//    public static final String WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";
//
//    public static final String LOOIS_URL = "https://api.loois.io/rpc/v2/";
//    public static final String LOOIS_TEST_URL = "https://loois.tech/rpc/v2/";
//
//    private LooisApiImpl loois;
//
//    @Before
//    public void setup(){
//        loois = new LooisApiImpl(new HttpService(LOOPRING_URL));
//    }
//
//    @Test
//    public void testLooisBalance() {
//        try {
//            BalanceParams params = new BalanceParams(TEST_WALLET_ADDRESS, LOOPRING_DELEGATE_ADDRESS);
//            List<Token> tokens = loois.looisBalance(params).send().getTokens();
//            System.out.println("testLoopringBalance " + tokens.size());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testLooisDepth() {
//
//        DepthParams params = new DepthParams("LRC-WETH", 20, LOOPRING_DELEGATE_ADDRESS);
//        try {
//            List<List<String>> sellList = loois.looisDepth(params).sendAsync().get().getSellList();
//            String market = loois.looisDepth(params).sendAsync().get().getMarket();
//            System.out.println(market);
//            System.out.println(sellList);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testLooisTicker() {
//        try {
//            LooisTicker looisTicker = loois.looisTicker().sendAsync().get();
//            System.out.println(looisTicker.getMarkets().size());
//            System.out.println(looisTicker.getMarkets().get(0));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testLooisTickers() {
//        try {
//            TickersResult.MarketTicker binanceMarketTicker = loois
//                    .looisTickers(new TickersParams("LRC-WETH")).sendAsync().get().getBinanceMarketTicker();
//            log("testLooisTickers ", binanceMarketTicker.change);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void testLooisTrend() throws ExecutionException, InterruptedException {
//        LooisTrend looisTrend = loois.looisTrend(new TrendParams("LRC-WETH", "1Day")).sendAsync().get();
//        log("testLooisTrend", looisTrend.getTrend().get(0).toString());
//    }
//
//    @Test
//    public void testRingMined() throws ExecutionException, InterruptedException {
//        List<Ring> minedRings = loois.looisRingMined(new RingMinedParams(
//                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238",
//                LOOPRING_DELEGATE_ADDRESS,
//                1, 20)).sendAsync().get().getMinedRings();
//        log("testRingMined", minedRings.get(0).toString());
//
//    }
//
//    @Test
//    public void testCutoff() throws ExecutionException, InterruptedException {
//        LooisCutoff latest = loois.looisCutoff(new CutoffParams(WALLET_ADDRESS,
//                LOOPRING_DELEGATE_ADDRESS,
//                "latest")).sendAsync().get();
//        log("testCutoff", latest.getCutoffTimestamp());
//    }
//
//    @Test
//    public void testPriceQuote() throws ExecutionException, InterruptedException {
//        LooisPriceQuote cny = loois.looisPriceQuote(new PriceQuoteParams("CNY")).sendAsync().get();
//        log("testPriceQuote", cny.getCurrency());
//        log("testPriceQuote", cny.getPriceQuoteTokens().get(0).symbol);
//    }
//
//    @Test
//    public void testEstimatedAllocatedAllowance() throws ExecutionException, InterruptedException {
//        LooisEstimatedAllocatedAllowance lrc = loois.looisEstimatedAllocatedAllowance(
//                new EstimatedAllocatedAllowanceParams(WALLET_ADDRESS, "NEO", LOOPRING_DELEGATE_ADDRESS)).sendAsync().get();
//        log("testEstimatedAllocatedAllowance", lrc.getEstimatedAllocatedAllowance());
//    }
//
//    @Test
//    public void testFrozenLRCFee() throws ExecutionException, InterruptedException {
//        LooisFrozenLRCFee looisFrozenLRCFee = loois.looisFrozenLRCFee(new FrozenLRCFeeParams(WALLET_ADDRESS)).sendAsync().get();
//        log("testFrozenLRCFee", looisFrozenLRCFee.getProzenLRCFee());
//    }
//
//    @Test
//    public void testSupportedMarket() throws ExecutionException, InterruptedException {
//        LooisSupportedMarket looisSupportedMarket = loois.looisSupportedMarket().sendAsync().get();
//        log("testSupportedMarket", looisSupportedMarket.getSupportedMarket());
//    }
//
//    @Test
//    public void testSupportedTokens() throws ExecutionException, InterruptedException {
//        LooisSupportedTokens looisSupportedTokens = loois.looisSupportedTokens(new SupportedTokensParams(WALLET_ADDRESS)).sendAsync().get();
//        log("testSupportedTokens", looisSupportedTokens.getSupportedTokens().get(0).symbol);
//    }
//
//
//    @Test
//    public void testTransactions() throws ExecutionException, InterruptedException {
//        LooisTransactions lrc = loois.looisTransactions(new TransactionParams(
//                WALLET_ADDRESS, null, "LRC", null, null, 1, 20))
//                .sendAsync().get();
//        log("testTransactions", lrc.getTotal());
//
//    }
//
//    @Test
//    public void testUnlockWallet() throws ExecutionException, InterruptedException {
//        LooisUnlockWallet wallet = loois.looisUnlockWallet(new UnlockWalletParams(
//                WALLET_ADDRESS)).sendAsync().get();
//        log("testUnlockWallet", wallet.getResult());
//
//    }
//
//    @Test
//    public void testEstimateGasPrice() throws ExecutionException, InterruptedException {
//        LooisEstimateGasPrice looisEstimateGasPrice = loois.looisEstimateGasPrice().sendAsync().get();
//        log("testEstimateGasPrice",looisEstimateGasPrice.getEstimateGasPrice());
//    }
//
//    @Test
//    public void testLRCSuggestCharge() throws ExecutionException, InterruptedException {
//        LooisLRCSuggestCharge cny = loois.looisLRCSuggestCharge(new LRCSuggestChargeParams(WALLET_ADDRESS, "CNY")).sendAsync().get();
//        log("testLRCSuggestCharge", cny.getLRCSuggestCharge());
//    }
//
//
//
//    public static void log(String tag, String msg) {
//        System.out.println(tag + " : " + msg);
//    }
//
//    public static void log(String tag, int msg) {
//        System.out.println(tag + " : " + String.valueOf(msg));
//    }
//
//    public static void log(String tag, List<String> result) {
//        System.out.println(tag + ":");
//        for (int i = 0; i < result.size(); i++) {
//            System.out.println(result.get(i));
//        }
//    }
}
