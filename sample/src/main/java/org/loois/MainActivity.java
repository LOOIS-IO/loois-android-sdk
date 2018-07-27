package org.loois;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartSocketTest(View view) {
        Intent intent = new Intent(this, SocketTestActivity.class);
        startActivity(intent);
    }

    public void onStartTransactionTest(View view) {
        Intent intent = new Intent(this, TransactionTestActivity.class);
        startActivity(intent);
    }
}
