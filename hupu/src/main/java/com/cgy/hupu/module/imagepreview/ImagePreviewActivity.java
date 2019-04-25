package com.cgy.hupu.module.imagepreview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import java.util.ArrayList;

public class ImagePreviewActivity extends BaseSwipeBackActivity {

    public static void startActivity(Context context, String extraPic, ArrayList<String> extraPics) {
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra("extraPic", extraPic);
        intent.putExtra("extraPics", extraPics);
        context.startActivity(intent);
    }


    @Override
    public int initContentView() {
        return R.layout.activity_image_preview;
    }

    @Override
    public void initInjector() {

    }

    @Override
    public void initUiAndListener() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isApplyStatusBarColor() {
        return false;
    }
}
