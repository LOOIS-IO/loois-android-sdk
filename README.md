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

##### ETH转账

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

##### ETH兑换WETH
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

##### WETH兑换ETH
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

##### 绑定NEO钱包
    public void onSendBindNeoTransaction(View view) {
        if (mHDWallet != null) {
            String neoAddress = "test";
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            Loois.transaction().sendBindNeoTransaction(BABY_LPR_BIND_CONTRACT_ADDRESS, neoAddress, gasPriceGwei, gasLimit, mHDWallet, PASSWORD, mTransactionListener);
        }
    }

##### 绑定量子钱包
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


### SOCKET
