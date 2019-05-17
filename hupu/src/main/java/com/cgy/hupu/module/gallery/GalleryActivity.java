package com.cgy.hupu.module.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends BaseSwipeBackActivity {

   public static void startActivity(Activity activity, ArrayList<String> selectImages) {
       Intent intent = new Intent(activity, GalleryActivity.class);
       intent.putExtra("selectImages", selectImages);
       activity.startActivityForResult(intent, REQUEST_IMAGE);
   }

    public static final int REQUEST_IMAGE = 101;
    public static final String EXTRA_RESULT = "select_result";

    @Override
    public int initContentView() {
        return R.layout.activity_gallery;
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
