package cn.com.reformer.ble;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import mutils.TestReceiveutils;

public class MaindddActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainddd);
        View viewById = findViewById(R.id.btn);
        context = this;
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestReceiveutils.send(context, "收到了没");
            }
        });
    }
}
