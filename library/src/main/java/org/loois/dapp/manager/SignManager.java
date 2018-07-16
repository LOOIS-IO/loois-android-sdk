package org.loois.dapp.manager;

import org.loois.dapp.LWallet;
import org.loois.dapp.Loois;
import org.loois.dapp.model.HDWallet;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
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


    private String signData(RawTransaction rawTransaction,
                            HDWallet wallet,
                            String password) throws IOException, CipherException {

        Credentials credentials = Credentials.create(LWallet.decrypt(password, wallet.getWalletFile()));
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
