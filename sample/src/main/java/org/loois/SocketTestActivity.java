package org.loois;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.loois.dapp.Loois;
import org.loois.dapp.protocol.Options;
import org.loois.dapp.protocol.core.LooisSocketImpl;
import org.loois.dapp.protocol.core.SocketListener;
import org.loois.dapp.protocol.core.socket.SocketBalance;
import org.loois.dapp.protocol.core.socket.SocketDepth;
import org.loois.dapp.protocol.core.socket.SocketLooisTickers;
import org.loois.dapp.protocol.core.socket.SocketMarketCap;
import org.loois.dapp.protocol.core.socket.SocketPendingTx;
import org.loois.dapp.protocol.core.socket.SocketTickers;

public class SocketTestActivity extends AppCompatActivity {

    private static final String TAG = "SocketTestActivity";

    public static final String WALLET_ADDRESS = "0xeaeec75ba0880a44edc5460e1d91c59a9da6bbc7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        Loois.initialize();

//        testSocketBalance();

        testSocketMarketcap();

//        testSocketDepth();

//        testSocketTickers();


//        testSocketLooisTickers();
    }

    private void testSocketLooisTickers() {
        LooisSocketImpl socket = new LooisSocketImpl();
        socket.onLooisTickers();
        socket.registerLooisTickersListener(new SocketListener(){
            @Override
            public void onLooisTickers(SocketLooisTickers socketLoopringTickers) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketTestActivity.this, String.valueOf(socketLoopringTickers.getMarkets().size()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void testSocketTickers() {
        Loois.socket().onTickers("LRC-WETH");
        Loois.socket().registerTickersListener(new SocketListener(){
            @Override
            public void onTickers(SocketTickers socketTickers) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketTestActivity.this, socketTickers.getTickers().binance.change, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void testSocketDepth() {
        LooisSocketImpl socket = new LooisSocketImpl();

        socket.onDepth("LRC-WETH");
        socket.registerDepthListener(new SocketListener(){
            @Override
            public void onDepth(SocketDepth socketDepth) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketTestActivity.this, socketDepth.getDepth().buy.get(0).get(0), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void testSocketMarketcap() {
        Loois.socket().onMarketCap("USD");
        Loois.socket().registerMarketCapListener(new SocketListener(){
            @Override
            public void onMarketCap(SocketMarketCap result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketTestActivity.this, result.getMarketCap().get(0).symbol, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void testSockePendingTx() {
        LooisSocketImpl looisSocket = new LooisSocketImpl();
        looisSocket.onPendingTx(WALLET_ADDRESS);
        looisSocket.registerPendingTxListener(new SocketListener(){

            @Override
            public void onPendingTx(SocketPendingTx result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketTestActivity.this,result.code , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SocketTestActivity.this, result.getTokens().get(0).getSymbol(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
