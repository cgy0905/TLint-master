package com.cgy.hupu.module.report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cgy.hupu.R;

public class ReportActivity extends AppCompatActivity {

    public static void startActivity(Context context, String tid , String pid) {
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra("tid", tid);
        intent.putExtra("pid", pid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}
