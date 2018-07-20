package org.loois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.loois.dapp.protocol.core.LooisSocketImpl;
import org.loois.dapp.protocol.core.SocketListener;
import org.loois.dapp.protocol.core.socket.SocketBalance;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String WALLET_ADDRESS = "0xeaeec75ba0880a44edc5460e1d91c59a9da6bbc7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testSocketBalance();
        testSocketTransactions();
    }

    private void testSocketTransactions() {
        LooisSocketImpl looisSocket = new LooisSocketImpl();
        looisSocket.onTransaction(WALLET_ADDRESS);
        looisSocket.registerTransactionListener(new SocketListener(){
            @Override
            public void onTransactions(String result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void testSocketBalance() {
        LooisSocketImpl looisSocket = new LooisSocketImpl();
        looisSocket.onBalance(WALLET_ADDRESS);
        looisSocket.registerBalanceListener(new SocketListener(){
            @Override
            public void onBalance(SocketBalance result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, result.getTokens().get(0).getSymbol(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
