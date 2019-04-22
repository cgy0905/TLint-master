package com.cgy.hupu.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by cgy on 2019/4/21 21:05 .
 */
public class ShareUtil {

    public static void shrareImage(Context context, Uri uri, String title) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(intent, title));
    }

    public static void share(Context context, String extraText) {
        Intent intent = new Intent();
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        Intent sendIntent = Intent.createChooser(intent, "分享");
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(sendIntent);
    }
}
