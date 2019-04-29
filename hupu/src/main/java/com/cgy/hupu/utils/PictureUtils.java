package com.cgy.hupu.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cgy on 2019/4/28 .
 */
public class PictureUtils {

    public static Bitmap returnBitmap(String url) {

        URL fileUrl;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 先保存到本地再广播到图库
     * @param context
     * @param bitmap
     */
    public static void saveImageToGallery(Context context, Bitmap bitmap) {
        //首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory().getPath()+"/stargoto/3E3E");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".jpg";//图片名称
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把文件插入到系统图库

        try {
            savePhotoToMedia(context, file, fileName);
            //mHandler.obtainMessage(WexinShareFragment.SAVE_SUCCESS).sendToTarget();;//保存成功

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //mHandler.obtainMessage(WexinShareFragment.SAVE_FAILURE).sendToTarget(); //保存失败
            return;
        }
    }

    private static void savePhotoToMedia(Context context, File file, String fileName) throws FileNotFoundException {
        String uri = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

        File f = new File(getRealPathFromURI(Uri.parse(uri), context));
        updatePhotoMedia(f, context);
    }



    //更新图库
    private static void updatePhotoMedia(File file, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }

    //获取绝对路径
    private static String getRealPathFromURI(Uri uri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String fileDir = cursor.getString(index);
        cursor.close();
        return fileDir;

    }

}
