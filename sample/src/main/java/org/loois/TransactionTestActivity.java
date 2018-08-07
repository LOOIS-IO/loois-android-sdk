package org.loois;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.loois.dapp.Loois;
import org.loois.dapp.manager.InitWalletManager;
import org.loois.dapp.manager.LooisListener;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.protocol.core.response.SupportedToken;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransactionTestActivity extends AppCompatActivity {

    private static final String TAG = "TransactionTestActivity";

    private static final String BABY_BASE_URL = "http://192.168.1.30:8083/";
    private static final String BABY_ETH_URL="https://ropsten.infura.io/1UoO4I/";
    private static final String BABY_SOCKET_URL="http://192.168.1.30:8087/";
    private static final String BABY_LPR_BIND_CONTRACT_ADDRESS="0xbf78B6E180ba2d1404c92Fc546cbc9233f616C42";
    private static final String BABY_LPR_DELEGATE_ADDRESS="0xc28766b782ad2873d7c39a725b88df6c0e753940";
    private static final String BABY_LPR_PROTOCAL_ADDRESS="0x3b8be7868ad035df06f3fa4843b8837937c13446";
    private static final String BABY_ORDER_WALLET_ADDRESS="0x1D3e0DDFdc3D597C4f42b8a0F17d4e8C866B3485";
    private static final byte BABY_CHAIN_ID=3;

    private static final String PRIVATE_KEY = "7FCA79052CAD5462DFCF6F65E32BF64FC7AC48A0D311541DBEEE24A2E69A5FD1";
    private static final String PASSWORD = "a12345678";
    private static final String TO = "0x478c19b242f17d6e8ef678aa71454ef02caaa402";

    private HDWallet mHDWallet;

    private TextView mAddressText;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        mAddressText = findViewById(R.id.address);
        Loois.initialize(BABY_BASE_URL, BABY_CHAIN_ID);
        InitWalletManager.shared().importPrivateKey(PRIVATE_KEY, PASSWORD, mHDWalletLooisListener);
    }

    private LooisListener<HDWallet> mHDWalletLooisListener = new LooisListener<HDWallet>() {
        @Override
        public void onSuccess(HDWallet result) {
            mHDWallet = result;
            mAddressText.setText(result.address);
            Loois.token().fetchSupportedTokens(result.address);
        }

        @Override
        public void onFailed(Throwable throwable) {
            Toast.makeText(TransactionTestActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    public void onSendETHTransaction(View view) {
        if (mHDWallet != null) {
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            BigDecimal amountEther = new BigDecimal("0.001");
            Loois.transaction().sendETHTransaction(TO, gasPriceGwei, gasLimit, amountEther, PASSWORD, mHDWallet, mTransactionListener);
        }
    }

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


    private LooisListener<String> mTransactionListener = new LooisListener<String>() {
        @Override
        public void onSuccess(String result) {
            Toast.makeText(TransactionTestActivity.this, result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed(Throwable throwable) {
            Toast.makeText(TransactionTestActivity.this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };


}
