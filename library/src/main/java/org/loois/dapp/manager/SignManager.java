package org.loois.dapp.manager;

import org.loois.dapp.Loois;
import org.loois.dapp.model.HDWallet;
import org.spongycastle.crypto.io.CipherIOException;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
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


    public String signContractTransaction(String contractAddress,
                                          String to,
                                          BigInteger nonce,
                                          BigInteger gasPrice,
                                          BigInteger gasLimit,
                                          BigDecimal amount,
                                          BigDecimal decimal,
                                          HDWallet wallet,
                                          String password) throws IOException, CipherException {
        BigDecimal realValue = amount.multiply(decimal);
        Function function = new Function("transfer",
                Arrays.asList(new Address(to), new Uint256(realValue.toBigInteger())),
                Collections.emptyList());
        String data = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                contractAddress,
                data);
        return signData(rawTransaction, wallet, password);
    }

    public String signETHTransaction(String to,
                                     BigInteger nonce,
                                     BigInteger gasPrice,
                                     BigInteger gasLimit,
                                     BigDecimal amount,
                                     HDWallet wallet,
                                     String password) throws IOException, CipherException {
        BigDecimal realValue = Convert.toWei(amount.toString(), Convert.Unit.ETHER);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, realValue.toBigInteger());
        return signData(rawTransaction, wallet, password);
    }


    /**
     * If token's allowance is 0, it means token never authenticated before, we need to sign a big value.
     *
     * @param tokenProtocol Sell token's protocol address
     * @param wallet User wallet
     * @param nonce Transaction nonce
     * @param gasPrice Gas price
     * @param gasLimit Gas limit
     * @param password Wallet password
     * @return Signed data
     * @throws IOException
     * @throws CipherException
     */
    public String signApproveOnce(String tokenProtocol, HDWallet wallet, BigInteger nonce, BigInteger gasPrice,
                                  BigInteger gasLimit, String password) throws IOException, CipherException {
        BigDecimal b1 = new BigDecimal(String.valueOf(Long.MAX_VALUE));
        BigDecimal b2 = new BigDecimal("1000000000000000000");
        BigInteger value = b1.multiply(b2).toBigIntegerExact();
        return signApproveData(tokenProtocol, wallet, nonce, gasPrice, gasLimit, value, password);
    }

    /**
     * If token's allowance is not 0, but still not cover order amount, we need to authenticate twice:
     * 1. Call signApproveTwice to sign 0 and send the signed data.
     * 2. Call signApproveOnce to sign a big value and send the signed data.
     *
     * @param tokenProtocol Sell token's protocol address
     * @param wallet User wallet
     * @param nonce Transaction nonce
     * @param gasPrice Gas price
     * @param gasLimit Gas limit
     * @param password Wallet password
     * @return Signed data
     * @throws IOException
     * @throws CipherException
     */
    public String signApproveTwice(String tokenProtocol, HDWallet wallet, BigInteger nonce, BigInteger gasPrice,
                                   BigInteger gasLimit, String password) throws IOException, CipherException {
        BigInteger value = new BigInteger("0");
        return signApproveData(tokenProtocol, wallet, nonce, gasPrice, gasLimit, value, password);
    }


    public String signApproveData(String tokenProtocol, HDWallet wallet, BigInteger nonce, BigInteger gasPrice,
                                  BigInteger gasLimit, BigInteger value, String password) throws IOException, CipherException {
        Function function = new Function("approve",
                Arrays.asList(new Address(Loois.DELEGATE_ADDRESS), new Uint(value)),
                Collections.emptyList());
        String data = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, tokenProtocol, data);
        return signData(rawTransaction, wallet, password);
    }


    private String signData(RawTransaction rawTransaction,
                            HDWallet wallet,
                            String password) throws IOException, CipherException {

        Credentials credentials = Credentials.create(Wallet.decrypt(password, wallet.getWalletFile()));
        byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, Loois.chainId, credentials);
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
