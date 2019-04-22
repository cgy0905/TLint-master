package com.example.retrofit_proxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://android.secoo.com").build();
        ApiService apiService = retrofit.create(ApiService.class);
        final OkHttpCall okHttpCall = apiService.getCartAndBrand("2.0");
        okHttpCall.enqueue(new Callback() {
            @Override
            public void onResponse(String response) {
                Log.e("response data",response);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }
}
