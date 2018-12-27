package com.example.dynamic_proxy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    private static final String url = "www.baidu.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ApiService apiService = new BaiduServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(apiService);
        ApiService proxy = (ApiService) Proxy.newProxyInstance(apiService.getClass().getClassLoader(), apiService.getClass().getInterfaces(), handler);
        final String result = proxy.getBaiduPage(url);
        Toast.makeText(this, "result:" + result, Toast.LENGTH_SHORT).show();
    }
}
