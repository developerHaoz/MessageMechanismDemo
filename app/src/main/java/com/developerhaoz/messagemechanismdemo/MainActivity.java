package com.developerhaoz.messagemechanismdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTvShowInfo;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String result = msg.obj.toString();
                mTvShowInfo.setText(result);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Set<Integer> set = new HashSet<>();

        final String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/48";

        handler.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();


                try {
                    final Response response = okHttpClient.newCall(request).execute();
                    final String result = response.body().string();
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }
}
