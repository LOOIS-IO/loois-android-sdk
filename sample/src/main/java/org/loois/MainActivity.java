package org.loois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.loois.dapp.protocol.core.LooisSocketImpl;
import org.loois.dapp.protocol.core.SocketListener;
import org.loois.dapp.protocol.core.socket.SocketBalance;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final String WALLET_ADDRESS = "0xd91a7cb8efc59f485e999f02019bf2947b15ee1d";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
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
