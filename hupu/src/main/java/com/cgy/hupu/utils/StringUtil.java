package com.cgy.hupu.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/17 17:37
 */
public class StringUtil {

    public static void copy(Context context, String stripped) {
        android.content.ClipboardManager clipboard =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("content", stripped);
        clipboard.setPrimaryClip(clip);
        ToastUtil.showToast("复制成功");
    }
}
