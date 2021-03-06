package org.loois.dapp.manager;

import org.loois.dapp.LWallet;
import org.loois.dapp.Loois;
import org.loois.dapp.common.Params;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.model.OriginalOrder;
import org.loois.dapp.protocol.LooisConfig;
import org.loois.dapp.protocol.core.params.NotifyTransactionSubmittedParams;
import org.loois.dapp.utils.IBan;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class SignManager {

    public static class SignModel {

        public NotifyTransactionSubmittedParams notifyTransactionSubmittedParams;

        public String sign;

        public Function function;

        public SignModel(NotifyTransactionSubmittedParams params, String sign) {
            this.notifyTransactionSubmittedParams = params;
            this.sign = sign;
        }

        public SignModel(String sign, Function function) {
            this.sign = sign;
            this.function = function;
        }

        public SignModel(String sign) {
            this.sign = sign;
        }
    }


    public SignModel signContractTransaction(String contractAddress,
                                             String to,
                                             BigInteger nonce,
                                             BigInteger gasPriceGwei,
                                             BigInteger gasLimit,
                                             BigDecimal amount,
                                             BigDecimal decimal,
                                             HDWallet wallet,
                                             String password) throws IOException, CipherException {
        Loois.log("signContractTransaction");
        BigInteger realValue = amount.multiply(decimal).toBigInteger();
        String data = encodeTransferFunction(to, realValue);
        BigInteger gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPriceWei,
                gasLimit,
                contractAddress,
                data);
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params = new NotifyTransactionSubmittedParams(null, nonce.intValue(), to,
                realValue, gasPriceWei, gasLimit, data, wallet.address);
        return new SignModel(params, signData);
    }

    public static String encodeTransferFunction(String to, BigInteger amountToken) {
        Function function = new Function("transfer",
                Arrays.asList(new Address(to), new Uint256(amountToken)),
                Collections.emptyList());
        return FunctionEncoder.encode(function);
    }

    public SignModel signETHTransaction(String to,
                                        BigInteger nonce,
                                        BigInteger gasPriceGwei,
                                        BigInteger gasLimit,
                                        BigDecimal amountEther,
                                        HDWallet wallet,
                                        String password) throws IOException, CipherException {
        BigDecimal amountWei = Convert.toWei(amountEther.toString(), Convert.Unit.ETHER);
        BigDecimal gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPriceWei.toBigInteger(), gasLimit, to, amountWei.toBigInteger());
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(), to, amountEther.toBigInteger(), gasPriceWei.toBigInteger(), gasLimit, "0x", wallet.address);
        return new SignModel(params, signData);
    }


    /*
     * If token's allowance is 0, it means token never authenticated before, we need to sign a big value.
     *
     * @param tokenProtocol Sell token's protocol owner
     * @param wallet        User wallet
     * @param nonce         Transaction nonce
     * @param gasPrice      Gas price
     * @param gasLimit      Gas limit
     * @param password      Wallet password
     * @return Signed data
     * @throws IOException
     * @throws CipherException
     */
    public SignModel signApproveMax(String tokenProtocol, HDWallet wallet, BigInteger nonce, BigInteger gasPrice,
                                    BigInteger gasLimit, String password) throws IOException, CipherException {
        BigDecimal b1 = new BigDecimal(String.valueOf(Long.MAX_VALUE));
        BigDecimal b2 = new BigDecimal("1000000000000000000");
        BigInteger value = b1.multiply(b2).toBigIntegerExact();
        return signApproveData(tokenProtocol, wallet, nonce, gasPrice, gasLimit, value, password);
    }

    /*
     * If token's allowance is not 0, but still not cover order amount, we need to authenticate twice:
     * 1. Call signApproveZero to sign 0 and send the signed data.
     * 2. Call signApproveMax to sign a big value and send the signed data.
     */
    public SignModel signApproveZero(String tokenProtocol, HDWallet wallet, BigInteger nonce, BigInteger gasPriceGwei,
                                     BigInteger gasLimit, String password) throws IOException, CipherException {
        BigInteger value = new BigInteger("0");
        return signApproveData(tokenProtocol, wallet, nonce, gasPriceGwei, gasLimit, value, password);
    }

    private SignModel signApproveData(String tokenProtocol, HDWallet wallet, BigInteger nonce, BigInteger gasPriceGwei,
                                      BigInteger gasLimit, BigInteger value, String password) throws IOException, CipherException {
        Function function = new Function("approve",
                Arrays.asList(new Address(Loois.getOptions().getDelegateAddress()), new Uint(value)),
                Collections.emptyList());
        String data = FunctionEncoder.encode(function);
        BigInteger gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPriceWei, gasLimit, tokenProtocol, data);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(), tokenProtocol, gasPriceWei, gasLimit, data, wallet.address);
        String signData = signData(rawTransaction, wallet, password);
        return new SignModel(params, signData);
    }


    public SignModel signedCancelOrder(String sellTokenProtocol,
                                       String buyTokenProtocol,
                                       OriginalOrder order,
                                       BigInteger nonce,
                                       BigInteger gasPrice,
                                       BigInteger gasLimit,
                                       HDWallet wallet,
                                       String password) throws Exception {
        String data = getCancelOrderMessage(sellTokenProtocol, buyTokenProtocol, order);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                Loois.getOptions().getProtocolAddress(), data);
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(),
                        Loois.getOptions().getProtocolAddress(), gasPrice, gasLimit, data, wallet.address);
        return new SignModel(params, signData);

    }


    public SignModel signCancelTokenPairOrders(String protocolB,
                                               String protocolS,
                                               BigInteger gasPrice,
                                               BigInteger gasLimit,
                                               BigInteger nonce,
                                               HDWallet wallet,
                                               String password) throws Exception {
        String data = abiCancelTokenPairOrder(protocolB, protocolS);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, Loois.getOptions().getProtocolAddress(), data);
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(), Loois.getOptions().getProtocolAddress(), gasPrice, gasLimit, data, wallet.address);
        return new SignModel(params, signData);
    }


    public String abiCancelTokenPairOrder(String tokenA,
                                          String tokenB) {
        int length = 64;
        char zero = '0';
        return Params.Abi.cancelAllOrdersByTradingPair +
                IBan.padLeft(Numeric.cleanHexPrefix(tokenA), length, zero) +
                IBan.padLeft(Numeric.cleanHexPrefix(tokenB), length, zero)
                + Numeric.toHexStringNoPrefixZeroPadded(BigInteger.valueOf(System.currentTimeMillis() / 1000), 64);
    }


    public String abiCancelAllOrders(long timestamp) {
        return Params.Abi.cancelAllOrders +
                Numeric.toHexStringNoPrefixZeroPadded(BigInteger.valueOf(timestamp), 64);
    }

    public SignModel signCancelAllOrders(long timestamp,
                                         BigInteger gasPrice,
                                         BigInteger gasLimit,
                                         BigInteger nonce,
                                         HDWallet wallet,
                                         String password) throws Exception {
        String data = abiCancelAllOrders(timestamp);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                Loois.getOptions().getProtocolAddress(),
                data);
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(), Loois.getOptions().getProtocolAddress(), gasPrice, gasLimit, data, wallet.address);
        return new SignModel(params, signData);
    }


    private String getCancelOrderMessage(
            String sellTokenProtocol,
            String buyTokenProtocol,
            OriginalOrder order) {
        StringBuilder builder = new StringBuilder();

        char zero = '0';
        int length = 64;
        builder.append(IBan.padLeft(Numeric.cleanHexPrefix(order.address), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(sellTokenProtocol), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(buyTokenProtocol), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.walletAddress), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.authAddr), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.amountS), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.amountB), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.validSince), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.validUntil), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.lrcFee), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.buyNoMoreThanAmountB ? order.amountB : order.amountS), length, zero))
                .append(Numeric.toHexStringNoPrefixZeroPadded(BigInteger.valueOf((order.buyNoMoreThanAmountB ? 1 : 0)), length))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.marginSplitPercentage), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.v), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.r), length, zero))
                .append(IBan.padLeft(Numeric.cleanHexPrefix(order.s), length, zero))
        ;

        return Params.Abi.cancelOrder + builder.toString();
    }

    public SignModel signDeposit(String protocolAddress,
                                 BigInteger gasPriceGwei,
                                 BigInteger gasLimit,
                                 BigInteger nonce,
                                 HDWallet wallet,
                                 String password,
                                 BigDecimal amountEther) throws Exception {
        Loois.log("signDeposit");
        String data = Params.Abi.deposit;
        BigInteger gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();
        BigInteger amountWei = Convert.toWei(amountEther, Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPriceWei, gasLimit, protocolAddress, amountWei, data);
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(), protocolAddress, BigInteger.ZERO, gasPriceWei, gasLimit, data, wallet.address);
        return new SignModel(params, signData);
    }


    public SignModel signWithdraw(String wethProtocol,
                                  BigInteger gasPriceGwei,
                                  BigInteger gasLimit,
                                  BigInteger nonce,
                                  HDWallet wallet,
                                  String password,
                                  BigDecimal amountEther) throws Exception {
        BigInteger gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();
        BigInteger amountWei = Convert.toWei(amountEther, Convert.Unit.ETHER).toBigInteger();
        String data = Params.Abi.withdraw + Numeric.toHexStringNoPrefixZeroPadded(amountWei, 64);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPriceWei, gasLimit, wethProtocol, data);
        String signData = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(),
                        wethProtocol, BigInteger.ZERO, gasPriceWei, gasLimit, data, wallet.address);
        return new SignModel(params, signData);
    }


    private SignModel signBind(
            String bindContractAddress,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger nonce,
            HDWallet wallet,
            String password,
            int param,
            String address) throws Exception {
        Function function = new Function("bind",
                Arrays.asList(new Uint8(param), new Utf8String(address)),
                Collections.emptyList());
        String data = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                Loois.getOptions().getBindContractAddress(), data);
        String sign = signData(rawTransaction, wallet, password);
        NotifyTransactionSubmittedParams params =
                new NotifyTransactionSubmittedParams(null, nonce.intValue(), bindContractAddress, gasPrice, gasLimit, data, wallet.address);
        return new SignModel(params, sign);
    }

    public SignModel signBindNeo(
            String bindContractAddress,
            BigInteger gasPriceGwei,
            BigInteger gasLimit,
            BigInteger nonce,
            HDWallet wallet,
            String password,
            String address) throws Exception {
        BigInteger gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();
        return signBind(
                bindContractAddress,
                gasPriceWei,
                gasLimit,
                nonce,
                wallet,
                password,
                1,
                address);

    }

    public SignModel signBindQutm(
            String bindContractAddress,
            BigInteger gasPriceGwei,
            BigInteger gasLimit,
            BigInteger nonce,
            HDWallet wallet,
            String password,
            String address) throws Exception {
        BigInteger gasPriceWei = Convert.toWei(gasPriceGwei.toString(), Convert.Unit.GWEI).toBigInteger();
        return signBind(
                bindContractAddress,
                gasPriceWei,
                gasLimit,
                nonce,
                wallet,
                password,
                2,
                address);
    }

    private String signData(RawTransaction rawTransaction,
                            HDWallet wallet,
                            String password) throws IOException, CipherException {

        Credentials credentials = Credentials.create(LWallet.decrypt(password, wallet.getWalletFile()));
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, Loois.getOptions().getChainId(), credentials);
        return Numeric.toHexString(signMessage);
    }


    private String signData(byte chainId,
                            RawTransaction rawTransaction,
                            HDWallet wallet,
                            String password) throws Exception {
        Credentials credentials;
        credentials = Credentials.create(Wallet.decrypt(password, wallet.getWalletFile()));
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        return Numeric.toHexString(signMessage);
    }


    // ---------------- singleton stuff --------------------------
    public static SignManager shared() {
        return SignManager.Holder.singleton;
    }

    private SignManager() {

    }

    private static class Holder {

        private static final SignManager singleton = new SignManager();

    }
}
