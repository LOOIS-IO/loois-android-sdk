package org.loois;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.loois.dapp.Loois;
import org.loois.dapp.manager.WalletManager;
import org.loois.dapp.manager.LooisListener;
import org.loois.dapp.model.HDWallet;
import org.loois.dapp.protocol.Options;
import org.loois.dapp.protocol.core.response.SupportedToken;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TransactionTestActivity extends AppCompatActivity {

    private static final String TAG = "TransactionTestActivity";

    private static final String BABY_BASE_URL = "https://loois.tech/rpc/v2";
    private static final String BABY_ETH_URL="https://ropsten.infura.io/1UoO4I/";
    private static final String BABY_SOCKET_URL="https://loois.tech/";
    private static final String BABY_LPR_BIND_CONTRACT_ADDRESS="0xbf78B6E180ba2d1404c92Fc546cbc9233f616C42";
    private static final String BABY_LPR_DELEGATE_ADDRESS="0xF036a52C13E1f749B50BDB6590D8aD42D2eb4e0d";
    private static final String BABY_LPR_PROTOCAL_ADDRESS="0x9636B51d4DF170C623241eD70697aA776BE3Ad43";
    private static final String BABY_ORDER_WALLET_ADDRESS="0x1D3e0DDFdc3D597C4f42b8a0F17d4e8C866B3485";
    private static final byte BABY_CHAIN_ID=3;

    private static final String PRIVATE_KEY = "7FCA79052CAD5462DFCF6F65E32BF64FC7AC48A0D311541DBEEE24A2E69A5FD1";
    private static final String PASSWORD = "a12345678";
    private static final String TO = "0x478c19b242f17d6e8ef678aa71454ef02caaa402";
    private static final String FROM = "0x422873fa86d7913c2a535a4793471528064792ba";

    private HDWallet mHDWallet;

    private TextView mAddressText;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        mAddressText = findViewById(R.id.address);
        Options options = new Options();
        options.setBaseUrl(BABY_BASE_URL);
        options.setChainId(BABY_CHAIN_ID);
        Loois.initialize(options);
        Loois.wallet().importPrivateKey(PRIVATE_KEY, PASSWORD, mHDWalletLooisListener);
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

    public void onSendBindNeoTransaction(View view) {
        if (mHDWallet != null) {
            String neoAddress = "test";
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            Loois.transaction().sendBindNeoTransaction(BABY_LPR_BIND_CONTRACT_ADDRESS, neoAddress, gasPriceGwei, gasLimit, mHDWallet, PASSWORD, mTransactionListener);
        }
    }

    public void onSendBindQutmTransaction(View view) {
        if (mHDWallet != null) {
            String neoAddress = "test";
            BigInteger gasPriceGwei = new BigInteger("4");
            BigInteger gasLimit = new BigInteger("200000");
            Loois.transaction().sendBindQutmTransaction(BABY_LPR_BIND_CONTRACT_ADDRESS, neoAddress, gasPriceGwei, gasLimit, mHDWallet, PASSWORD, mTransactionListener);
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
