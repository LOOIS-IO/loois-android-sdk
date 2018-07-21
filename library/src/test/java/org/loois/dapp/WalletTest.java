package org.loois.dapp;

import junit.framework.Assert;

import org.junit.Test;
import org.loois.dapp.manager.InitWalletManager;
import org.loois.dapp.utils.HDValidUtils;
import org.loois.dapp.validation.MnemonicValidation;
import org.web3j.utils.Numeric;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.github.novacrypto.base58.Base58;
import io.github.novacrypto.bip32.ExtendedPrivateKey;
import io.github.novacrypto.bip32.networks.Bitcoin;
import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;
import io.github.novacrypto.hashing.Sha256;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class WalletTest {

    @Test
    public void testCreateMnemonic(){
        String mnemonic = InitWalletManager.shared().generateMnemonic(Words.TWELVE, English.INSTANCE);
        System.out.println(mnemonic);
        Assert.assertEquals(MnemonicValidation.QUALIFIED,HDValidUtils.isValidMnemonic(mnemonic,English.INSTANCE));
    }

    @Test
    public void testSeed(){
        String mnemonic = "paper goose sleep travel aspect custom avocado permit language protect boy hazard";
        byte[] seed = new SeedCalculator().calculateSeed(mnemonic, "");
        String actual = Numeric.toHexString(seed);
        String expect = "0xd27aee1067bac4fb53ad1eebb803163ea4702476f83ebc07e8f67e29764c6ae78fcba8e20caca732da925880ec04bfc99b0cd1bb337c774d8e33f69b18cb2af0";
        Assert.assertEquals(actual,expect);
    }

    @Test
    public void testHmacSha512() throws Exception{
        String mnemonic = "traffic buddy void three pink bronze radar rule science grab orbit surge";

        final String HMAC_SHA512 = "HmacSHA512";
        byte[] byteKey = "Bitcoin seed".getBytes();
        byte[] seed = new SeedCalculator().calculateSeed(mnemonic,"");
        System.out.println(Numeric.toHexString(seed));
        Mac hmacSha512 = Mac.getInstance(HMAC_SHA512);
        final SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
        hmacSha512.init(keySpec);
        byte[] result = hmacSha512.doFinal(seed);
        for (byte b : result) {
            System.out.print((b&0xff)+",");
        }
        String hex = Numeric.toHexString(result);
        System.out.println("\n"+hex+"\nlength:"+hex.length());
    }

    @Test
    public void testBase58(){
        String mnemonic = "516b6fcd0f";
        byte[] seed = mnemonic.getBytes();
        String base58 = Base58.base58Encode(seed);
        System.out.println(base58);
    }

    @Test
    public void testCreateWallet(){
        String mnemonic = "traffic buddy void three pink bronze radar rule science grab orbit surge";
        System.out.println("mnemonics:" + mnemonic);
        byte[] seed = new SeedCalculator().calculateSeed(mnemonic, "");
        System.out.println("seed:"+Numeric.toHexString(seed));

        ExtendedPrivateKey rootKey = ExtendedPrivateKey.fromSeed(seed, Bitcoin.MAIN_NET);

        String extendedKeyByteArray = Numeric.toHexString(rootKey.extendedKeyByteArray());
        System.out.println("extendedKeyByteArray:"+extendedKeyByteArray);
        System.out.println("length:"+extendedKeyByteArray.length());
        String extendedBase58 = rootKey.extendedBase58();
        System.out.println("extendedBase58:" + extendedBase58);
    }

    @Test
    public void testSha256Twice(){
        String mnemonic = "traffic buddy void three pink bronze radar rule science grab orbit surge";
        ExtendedPrivateKey rootKey = ExtendedPrivateKey.fromSeed(new SeedCalculator().calculateSeed(mnemonic, ""), Bitcoin.MAIN_NET);
        System.out.println("mnemonics:" + mnemonic);
        byte[] bytes = rootKey.extendedKeyByteArray();
        String hexByte = Numeric.toHexString(bytes);
        System.out.println("hex:"+hexByte);
//        System.out.println("extendedBase58:" + extendedBase58);
        byte[] twice = Sha256.sha256Twice(bytes, 0, 78);
        System.out.println(Numeric.toHexString(twice));

    }

    @Test
    public void test2(){
        String hex = "0x0488ade4000000000000000000dc1419de8e87e95e2bd811ee55536d24c612b38267be3669ce428fcd33468173006ebaa1547129f9fca5eaff736bff6053695cc9460eb762b74cfde8fc962696307feaf160";
        byte[] bytes = Numeric.hexStringToByteArray(hex);
        byte[] twice = Sha256.sha256Twice(bytes, 0, 78);
        System.out.println(Numeric.toHexString(twice));
    }

    @Test
    public void testSha256Once(){
        String mnemonic = "traffic buddy void three pink bronze radar rule science grab orbit surge";
        byte[] seed = new SeedCalculator().calculateSeed(mnemonic, "");
        System.out.println(Numeric.toHexString(seed));
//        ExtendedPrivateKey rootKey = ExtendedPrivateKey.fromSeed(), Bitcoin.MAIN_NET);
        System.out.println("mnemonics:" + mnemonic);
//        byte[] bytes = rootKey.extendedKeyByteArray();
        byte[] bytes = Sha256.sha256(seed);
        System.out.println(Numeric.toHexString(bytes));

    }


}
