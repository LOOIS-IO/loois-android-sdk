package org.loois.dapp.manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.loois.dapp.LWallet;
import org.loois.dapp.Loois;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.rx.LooisSubscriber;
import org.loois.dapp.rx.ScheduleCompat;
import org.spongycastle.util.encoders.Hex;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import java.io.IOException;
import java.security.SecureRandom;

import io.github.novacrypto.bip32.ExtendedPrivateKey;
import io.github.novacrypto.bip32.networks.Bitcoin;
import io.github.novacrypto.bip39.MnemonicGenerator;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.WordList;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip44.AddressIndex;
import io.reactivex.Flowable;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc : wallet manager
 * </pre>
 */
public class WalletManager {


    /**
     * create mnemonic 创建助记词
     * @param words {@link Words} define mnemonic length 指定助记词长度
     * @param wordList mnemonic type ,use {@link io.github.novacrypto.bip39.wordlists.English} for common.助记词类型,一般用英语单词
     * @return mnemonic 助记词
     */
    public String generateMnemonic(Words words,WordList wordList) {
        StringBuilder sb = new StringBuilder();
        byte[] entropy = new byte[words.byteLength()];
        new SecureRandom().nextBytes(entropy);
        new MnemonicGenerator(wordList).createMnemonic(entropy, sb::append);
        return sb.toString();
    }

    /**
     *
     * @param mnemonic mnemonic 助记词
     * @param addressIndex derive owner 派生路径 eg: m/44'/60'/0'/0/0
     * @return {@link ECKeyPair} public/private key pair 公私钥对象
     */
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

    /**
     * generate HD wallet 创建钱包
     * @param mnemonic mnemonic 助记词
     * @param password the password user input  用户输入的密码
     * @param addressIndex {@link AddressIndex} derive owner 派生地址
     * @return wallet 钱包
     * @throws CipherException cipher exception
     */
    public HDWallet generateWallet(String mnemonic,
                                   String password,
                                   AddressIndex addressIndex) throws CipherException {
        ECKeyPair keyPair = generateKeyPair(mnemonic,addressIndex);
        WalletFile walletFile = Wallet.createLight(password, keyPair);
        return new HDWallet(walletFile.getAddress(),walletFile);
    }

    public HDWallet importMnemonic(String mnemonic,
                                   String password,
                                   AddressIndex addressIndex) throws CipherException {
        return generateWallet(mnemonic,password,addressIndex);
    }

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

    public HDWallet importPrivateKey(String privateKey, String password) throws CipherException {
        if (privateKey.startsWith("0x")) {
            privateKey = privateKey.substring(2);
        }
        byte[] privateBytes = Hex.decode(privateKey);
        ECKeyPair ecKeyPair = ECKeyPair.create(privateBytes);
        WalletFile walletFile = Wallet.createLight(password, ecKeyPair);
        return new HDWallet(walletFile.getAddress(), walletFile);
    }

    public void importPrivateKey(String privateKey, String password, LooisListener<HDWallet> listener) {
        Flowable.just(password)
                .map(s -> importPrivateKey(privateKey, s))
                .compose(ScheduleCompat.apply())
                .subscribe(new LooisSubscriber<HDWallet>() {
                    @Override
                    public void onSuccess(HDWallet wallet) {
                        if (listener != null) {
                            listener.onSuccess(wallet);
                        }
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        if (listener != null) {
                            listener.onFailed(throwable);
                        }
                    }
                });
    }
}
