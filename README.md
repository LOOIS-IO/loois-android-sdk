# Loois Android SDK

此仓库是 [Loois团队](https://github.com/LOOIS-IO) 提供的方便开发者接入钱包的SDK. Loois Android SDK提供了常规的以太坊钱包功能和基于[路印协议](https://loopring.org/) Relay的交易等功能及便捷API.


### 集成步骤
#### 1. 添加依赖

#### 2. 初始SDK

#### 3. 功能组件



### Web3j
Loois SDK基于[Web3j](https://github.com/web3j/web3j)库，保留了Web3j的所有功能，提供了Web3j的实例，可以使用该实例调用以太坊的所有[JSON_RPC](https://github.com/ethereum/wiki/wiki/JSON-RPC)。
例如：

    //发送RawTransaction
    Loois.web3j().ethSendRawTransaction(sign).sendAsync().get();
    //获取Nonce
    Loois.web3j().ethGetTransactionCount(s, DefaultBlockParameterName.PENDING).sendAsync().get());

### 钱包

##### 创建钱包

* 生成助记词
* 派生地址路径

##### 导入钱包

* 助记词导入
* Keystore导入
* 私钥导入

##### 导出钱包

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

### 高级功能
