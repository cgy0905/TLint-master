package com.example.dynamic_proxy;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int GET_SUCCESS = 1;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)  {
                case GET_SUCCESS:
                    String result = (String) msg.obj;
                    Toast.makeText(MainActivity.this, "result :" + result, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRequest = findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Map map = new HashMap();
//                    map.put("type", "android");
                    TestClass testClass = MyRetrofit.getInstance()
                            .init("http://www.baidu.com", getClient())
                            .create(TestClass.class);
                    Call call = testClass.getMessage();
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        String result = response.body().string();

                        Message message = Message.obtain();
                        message.what = GET_SUCCESS;
                        message.obj = result;
                        handler.sendMessage(message);

                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }
}
