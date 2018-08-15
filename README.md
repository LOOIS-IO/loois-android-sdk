# Loois Android SDK

此仓库是 [Loois团队](https://github.com/LOOIS-IO) 提供的方便开发者接入钱包的SDK. Loois Android SDK提供了常规的以太坊钱包功能和基于[路印协议](https://loopring.org/) Relay的交易等功能及便捷API.


### 集成步骤
#### 1. 添加依赖

#### 2. 初始SDK
通常在Application的onCreate方法中调用initialize方法完成Loois SDK的初始化：

    Loois.initialize();//使用默认配置完成初始化
    Loois.initialize(options);//使用自定义配置完成初始化

Loois SDK的默认配置为：

    public class LooisConfig {

        public static final String BASE_URL = "http://api.loois.io/";

        public static final String ETH_URL = "https://relay1.loopring.io/";

        /**
         * The loopring TokenTransferDelegate Protocol.
         */
        public static final String DELEGATE_ADDRESS = "0x17233e07c67d086464fD408148c3ABB56245FA64";

        /**
         * Loopring contract owner
         */
        public static final String PROTOCOL_ADDRESS = "0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78";

        public static final String BIND_CONTRACT_ADDRESS = "0xbf78B6E180ba2d1404c92Fc546cbc9233f616C42";

        public static final String ORDER_WALLET_ADDRESS = "0x1D3e0DDFdc3D597C4f42b8a0F17d4e8C866B3485";

        public static final String CONTRACT_VERSION = "v1.51";

        public static final byte CHAIN_ID = ChainId.MAINNET;

    }

另外，可以使用Loois.setDebugLogEnabled(BuildConfig.DEBUG)来设置是否打开SDK日志。
#### 3. 功能组件
Loois SDK初始化后，则可以获取各个功能组件，这些组件包括:
- LooisApi：通过Loois.client()获取，提供API接口
- LooisSocketApi：通过Loois.socket()获取，提供Socket API接口
- Web3j：通过Loois.web3j()获取，提供Web3j的所有接口
- TransactionManager：通过Loois.transaction()获取，提供Transaction相关的所有功能
- WalletManager：通过Loois.wallet()获取，提供钱包相关的功能
- OrderManager：通过Loois.order()获取，提供下单功能
- TokenManager：通过Loois.token()获取，提供代币相关功能


### Web3j
Loois SDK基于[Web3j](https://github.com/web3j/web3j)库，保留了Web3j的所有功能，提供了Web3j的实例，可以使用该实例调用以太坊的所有[JSON_RPC](https://github.com/ethereum/wiki/wiki/JSON-RPC)。
例如：

    //发送RawTransaction
    Loois.web3j().ethSendRawTransaction(sign).sendAsync().get();
    //获取Nonce
    Loois.web3j().ethGetTransactionCount(s, DefaultBlockParameterName.PENDING).sendAsync().get());

### WalletManager
WalletManager主要提供钱包的创建、导入和导出。

#### 创建钱包

 生成助记词

    public String generateMnemonic(Words words,WordList wordList) {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[words.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(wordList).createMnemonic(entropy, sb::append);
        return sb.toString();
    }

 派生地址路径

     private ECKeyPair generateKeyPair(String mnemonic,
                                      AddressIndex addressIndex){

         // 1. calculate seed from mnemonics , then get master/root key ; Note that the BIP39 passphrase we set "" for common
         // 通过助记词计算seed , 然后得到 master/root key ; 注意,我们设置BIP39的密码为"",为了钱包的通用
         ExtendedPrivateKey rootKey = ExtendedPrivateKey.fromSeed(new SeedCalculator().calculateSeed(mnemonic, ""), Bitcoin.MAIN_NET);
         Loois.log("mnemonics:" + mnemonic);
         String extendedBase58 = rootKey.extendedBase58();
         Loois.log("extendedBase58:" + extendedBase58);

         // 3. get child private key deriving from master/root key
         ExtendedPrivateKey childPrivateKey = rootKey.derive(addressIndex, AddressIndex.DERIVATION);
         String childExtendedBase58 = childPrivateKey.extendedBase58();
         Loois.log("childExtendedBase58:" + childExtendedBase58);

         // 4. get key pair
         byte[] privateKeyBytes = childPrivateKey.getKey();
         ECKeyPair keyPair = ECKeyPair.create(privateKeyBytes);

         // we 've gotten what we need
         String privateKey = childPrivateKey.getPrivateKey();
         String publicKey = childPrivateKey.neuter().getPublicKey();
         String address = Keys.getAddress(keyPair);

         Loois.log("privateKey:" + privateKey);
         Loois.log("publicKey:" + publicKey);
         Loois.log("owner:" + "0x" + address);

         return keyPair;
     }

#### 导入钱包
助记词导入

    public HDWallet importMnemonic(String mnemonic,
                                   String password,
                                   AddressIndex addressIndex) throws CipherException {
        return generateWallet(mnemonic,password,addressIndex);
    }


 Keystore导入

     public HDWallet importKeystore(String keystore,
                                    String password) throws IOException, CipherException {
         ObjectMapper objectMapper = new ObjectMapper();
         WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
         ECKeyPair keyPair = LWallet.decrypt(password, walletFile);
         WalletFile generateWalletFile = Wallet.createLight(password, keyPair);
         if (!generateWalletFile.getAddress().equalsIgnoreCase(walletFile.getAddress())) {
             // not the same one
             return null;
         }
         return new HDWallet(walletFile.getAddress(),walletFile);
     }

私钥导入

    public HDWallet importPrivateKey(String privateKey, String password) throws CipherException {
        if (privateKey.startsWith("0x")) {
            privateKey = privateKey.substring(2);
        }
        byte[] privateBytes = Hex.decode(privateKey);
        ECKeyPair ecKeyPair = ECKeyPair.create(privateBytes);
        WalletFile walletFile = Wallet.createLight(password, ecKeyPair);
        return new HDWallet(walletFile.getAddress(), walletFile);
    }

#### 导出钱包

### TransactionManager
TransactionManager管理所有的[Transaction](https://github.com/ethereumbook/ethereumbook/blob/develop/transactions.asciidoc)，包括转账或是智能合约的调用，Loois SDK封装了Nonce的管理，所以在发送任何Transaction时，无需关心Nonce的取值。

#### ETH转账

    public void onSendETHTransaction(View view) {
        if (mHDWallet != null) {
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            BigDecimal amountEther = new BigDecimal("0.001");
            Loois.transaction().sendETHTransaction(TO, gasPriceGwei, gasLimit, amountEther, PASSWORD, mHDWallet, mTransactionListener);
        }
    }


#### ERC-20代币转账
    public void onSendTokenTransaction(View view) {
        SupportedToken lrc = Loois.token().getSupportedTokenBySymbol("LRC");
        if (mHDWallet != null && lrc != null) {
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            BigDecimal amount = new BigDecimal("10");
            Loois.transaction().sendTokenTransaction(lrc.protocol, lrc.decimals, TO, gasPriceGwei,
                    gasLimit, amount, PASSWORD, mHDWallet, mTransactionListener);
        }
    }

#### ETH兑换WETH
    public void onSendETHToWETHTransaction(View view) {
        BigInteger gasPriceGwei = new BigInteger("4");
        BigInteger gasLimit = new BigInteger("200000");
        BigDecimal amountEther = new BigDecimal("0.1");
        SupportedToken weth = Loois.token().getSupportedTokenBySymbol("WETH");
        if (mHDWallet != null && weth != null) {
            Loois.transaction().sendETHToWETHTransaction(weth.protocol,
                    gasPriceGwei, gasLimit, mHDWallet, PASSWORD, amountEther, mTransactionListener);
        }

    }

#### WETH兑换ETH
    public void onSendWETHToETHTransaction(View view) {
        BigInteger gasPriceGwei = new BigInteger("4");
        BigInteger gasLimit = new BigInteger("200000");
        BigDecimal amountEther = new BigDecimal("0.1");
        SupportedToken weth = Loois.token().getSupportedTokenBySymbol("WETH");
        if (mHDWallet != null && weth != null) {
            Loois.transaction().sendWETHToETHTransaction(weth.protocol,
                    gasPriceGwei, gasLimit, mHDWallet, PASSWORD, amountEther, mTransactionListener);
        }
    }

#### 绑定NEO钱包
    public void onSendBindNeoTransaction(View view) {
        if (mHDWallet != null) {
            String neoAddress = "test";
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            Loois.transaction().sendBindNeoTransaction(BABY_LPR_BIND_CONTRACT_ADDRESS, neoAddress, gasPriceGwei, gasLimit, mHDWallet, PASSWORD, mTransactionListener);
        }
    }

#### 绑定量子钱包
    public void onSendBindQutmTransaction(View view) {
        if (mHDWallet != null) {
            String neoAddress = "test";
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            Loois.transaction().sendBindQutmTransaction(BABY_LPR_BIND_CONTRACT_ADDRESS, neoAddress, gasPriceGwei, gasLimit, mHDWallet, PASSWORD, mTransactionListener);
        }
    }

#### 单次授权

#### 二次授权

#### 取消单个订单

#### 取消所有订单

#### 取消某个市场所有订单



### API
Loois SDK实现了所有Loopring的[JSON_RPC](https://github.com/Loopring/relay/blob/wallet_v2/LOOPRING_RELAY_API_SPEC_V2.md#json-rpc-methods)，并且还包括了Loois Relay的扩展API，API的设计延续Web3j的风格。

    public interface LooisApi {

        //获取账户余额
        Request<?, LooisBalance> looisBalance(BalanceParams ...params);

        //提交订单
        Request<?, LooisSubmitOrder> looisSubmitOrder(SubmitOrderParams ...params);

        //获取订单
        Request<?, LooisOrders> looisOrders(OrderParams ...params);

        //获取深度
        Request<?, LooisDepth> looisDepth(DepthParams ...params);

        //获取市场数据
        Request<?, LooisTicker> looisTicker();

        //获取各大市场数据
        Request<?, LooisTickers> looisTickers(TickersParams ...params);

        Request<?, LooisFills> looisFills(FillsParams...params);

        //获取趋势数据，即K线数据
        Request<?, LooisTrend> looisTrend(TrendParams ...params);

        Request<?, LooisRingMined> looisRingMined(RingMinedParams ...params);

        Request<?, LooisCutoff> looisCutoff(CutoffParams ...params);

        //获取人民币或美元价格
        Request<?, LooisPriceQuote> looisPriceQuote(PriceQuoteParams ...params);

        //获取某个钱包地址某个Token已使用的额度
        Request<?, LooisEstimatedAllocatedAllowance> looisEstimatedAllocatedAllowance(EstimatedAllocatedAllowanceParams ...params);

        //获取冻结的LRC费用
        Request<?, LooisFrozenLRCFee> looisFrozenLRCFee(FrozenLRCFeeParams ...params);

        Request<?, LooisSupportedMarket> looisSupportedMarket();

        Request<?, LooisSupportedTokens> looisSupportedTokens(SupportedTokensParams ...params);

        //获取Transactions
        Request<?, LooisTransactions> looisTransactions(TransactionParams ...params);

        //解锁钱包
        Request<?, LooisUnlockWallet> looisUnlockWallet(UnlockWalletParams ...params);

        //通知Relay Transaction已提交
        Request<?, LooisNotifyTransactionSubmitted> looisNotifyTransactionSubmitted(NotifyTransactionSubmittedParams ...params);

        //获取推荐的Gas Price
        Request<?, LooisEstimateGasPrice> looisEstimateGasPrice();

        Request<?, LooisSubmitRingForP2P> looisSubmitRingForP2P(SubmitRingForP2PParams ...params);

        Request<?, LooisRegisterERC20Token> looisRegisterERC20Token(RegisterERC20TokenParams ...params);

        //获取推荐的LRC费用
        Request<?, LooisLRCSuggestCharge> looisLRCSuggestCharge(LRCSuggestChargeParams ...params);

        Request<?, LooisSearchLocalERC20Token> looisSearchLocalERC20Token(SearchLocalERC20TokenParams...params);

        //软取消
        Request<?, LooisFlexCancelOrder> looisFlexCancelOrder(LooisFlexCancelOrder ...params);
    }

#### API使用示例
    @Test
    public void testTransactions() throws ExecutionException, InterruptedException {
        LooisTransactions lrc = Loois.client().looisTransactions(new TransactionParams(
                WALLET_ADDRESS, null, "LRC", null, null, 1, 20))
                .sendAsync().get();
        log("testTransactions", lrc.getTotal());

    }

### SOCKET
Loois SDK实现Loopring的所有[SocketIO-Events](https://github.com/Loopring/relay/blob/wallet_v2/LOOPRING_RELAY_API_SPEC_V2.md#socketio-events)。

    public interface LooisSocketApi {

        //Balance
        void onBalance(String owner);
        void offBalance();

        //Pending中的Transaction
        void onPendingTx(String owner);
        void offPendingTx();

        //市场价格
        void onMarketCap(String currency);
        void offMarketCap();

        //市场深度
        void onDepth(String market);
        void offDepth();

        //各大交易所某个市场数据
        void onTickers(String market);
        void offTickers();

        //所有市场数据
        void onLooisTickers();
        void offLooisTickers();

        //注册监听与取消监听
        void registerBalanceListener(SocketListener listener);
        void removeBalanceListener(SocketListener listener);

        void registerPendingTxListener(SocketListener listener);
        void removePendingTxListener(SocketListener listener);

        void registerMarketCapListener(SocketListener listener);
        void removeMarketCapListener(SocketListener listener);

        void registerDepthListener(SocketListener listener);
        void removeDepthListener(SocketListener listener);

        void registerTickersListener(SocketListener listener);
        void removeTickersListener(SocketListener listener);

        void registerLooisTickersListener(SocketListener listener);
        void removeLooisTickersListener(SocketListener listener);

    }

#### Socket使用示例
    private void testSocketDepth() {
        Loois.socket().onDepth("LRC-WETH");
        Loois.socket().registerDepthListener(new SocketListener(){
            @Override
            public void onDepth(SocketDepth socketDepth) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketTestActivity.this, socketDepth.getDepth().buy.get(0).get(0), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

