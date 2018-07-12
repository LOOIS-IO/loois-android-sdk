package org.loois.dapp;

import junit.framework.Assert;

import org.junit.Test;
import org.loois.dapp.manager.InitWalletManager;
import org.loois.dapp.validation.MnemonicValidation;
import org.loois.dapp.utils.HDValidUtils;
import org.web3j.utils.Numeric;

import io.github.novacrypto.bip39.SeedCalculator;
import io.github.novacrypto.bip39.Words;
import io.github.novacrypto.bip39.wordlists.English;

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




}
