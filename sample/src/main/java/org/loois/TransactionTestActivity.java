package org.loois;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.loois.dapp.Loois;

public class TransactionTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

//        eth_address:  http://192.168.1.30:8500
//        socket_io     :  http://192.168.1.30:8087
//        rpc_address :  http://192.168.1.30:8083

        String testUrl = "http://192.168.1.30:8500";
        byte chainId = 10;
        Loois.initialize(testUrl, chainId);


    }
}
