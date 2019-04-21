package com.cgy.hupu.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cgy on 2018/12/17 .
 */
class FileUtil {
    public static boolean hasSDCard() {
        boolean mHasSDCard = false;
        if (Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())) {
            mHasSDCard = true;
        } else {
            mHasSDCard = false;
        }
        return mHasSDCard;
    }

    public static String getSdcardPath() {
        if (hasSDCard()) return Environment.getExternalStorageDirectory().getAbsolutePath();

        return "/sdcard/";
    }

    public static void chmod(String permission, String path) {
        try {
            String command = "chmod" + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String stringFromAssetsFile(Context context, String fileName) {
        AssetManager manager = context.getAssets();
        InputStream file;
        try {
            file = manager.open(fileName);
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制assets文件制定目录
     * @param context
     * @param fileName 文件名
     * @param filePath 目录
     */
    public static void copyAssets(Context context, String fileName, String filePath) {
        InputStream is;
        try {
            is = context.getResources().getAssets().open(fileName);//assets文件夹下的文件
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(filePath + "/" + fileName);//保存到本地的文件夹下的文件
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = is.read(buf)) > 0) {
                fos.write(buf, 0, count);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean copy(File oldFile, File newFile) {
        if (!oldFile.exists()) {
            return false;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(oldFile);
            fos = new FileOutputStream(newFile);
            byte[] buf = new byte[4096];
            while (fis.read(buf) != -1) {
                fos.write(buf);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean exist(String url) {
        File file = new File(url);
        return file.exists();
    }
}
