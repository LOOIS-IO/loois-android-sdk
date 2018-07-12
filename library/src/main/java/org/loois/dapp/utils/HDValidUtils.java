package org.loois.dapp.utils;

import org.loois.dapp.validation.MnemonicValidation;
import org.web3j.crypto.WalletUtils;

import io.github.novacrypto.bip39.MnemonicValidator;
import io.github.novacrypto.bip39.Validation.InvalidChecksumException;
import io.github.novacrypto.bip39.Validation.InvalidWordCountException;
import io.github.novacrypto.bip39.Validation.UnexpectedWhiteSpaceException;
import io.github.novacrypto.bip39.Validation.WordNotFoundException;
import io.github.novacrypto.bip39.WordList;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2018/7/12
 * Brief Desc :
 * </pre>
 */
public class HDValidUtils {

    public static MnemonicValidation isValidMnemonic(String mnemonic,
                                                     WordList wordList) {
        MnemonicValidation validation = MnemonicValidation.QUALIFIED;
        try {
            MnemonicValidator
                    .ofWordList(wordList)
                    .validate(mnemonic);
        } catch (UnexpectedWhiteSpaceException e) {
            validation = MnemonicValidation.UNEXPECTED_WHITESPACE_EXCEPTION;
            e.printStackTrace();
        } catch (InvalidWordCountException e) {
            validation = MnemonicValidation.INVALID_WORD_COUNT_EXCEPTION;
            e.printStackTrace();
        } catch (InvalidChecksumException e) {
            validation = MnemonicValidation.INVALID_CHECKSUM_EXCEPTION;
            e.printStackTrace();
        } catch (WordNotFoundException e) {
            validation = MnemonicValidation.WORD_NOT_FOUND_EXCEPTION;
            e.printStackTrace();
        }
        return validation;
    }

    public static boolean isValidPrivateKey(String privateKey){
        return WalletUtils.isValidPrivateKey(privateKey);
    }

    public static boolean isValidAddress(String address){
        return WalletUtils.isValidAddress(address);
    }
}
