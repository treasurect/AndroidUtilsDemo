package com.treasure.androidutils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * ========================================
 * <p>
 * Created by treasure on 2018/5/2.
 * <p>
 * Android Bitmap 相关
 * <p>
 * ========================================
 */

public class BitmapUtil {

    /**
     * Bitmap 转 base64
     */
    public String bmpToBase64(Bitmap bitmap) {

        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * base64 转 Bitmap
     */
    public Bitmap base64ToBmp(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * Drawable 转 Bitmap
     */
    public static Bitmap drawableToBmp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Bitmap 转 Drawable
     */
    public static Drawable bmpToDrawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * Bitmap 转换成 byte[]
     */
    public static byte[] bmpToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();

    }

    /**
     * byte[] 转化成 Bitmap
     */
    public static Bitmap bytesToBmp(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 保存 Bitmap To File
     */
    public static String saveBmpToFile(Bitmap bmp, String path, String dir) {
        File imageFileName;
        FileOutputStream out;
        File imageFileFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dir);
        if (!imageFileFolder.exists()) {
            imageFileFolder.mkdir();
        }
        Calendar c = Calendar.getInstance();
        String date = String.valueOf(c.get(Calendar.MONTH))
                + String.valueOf(c.get(Calendar.DAY_OF_MONTH))
                + String.valueOf(c.get(Calendar.YEAR))
                + String.valueOf(c.get(Calendar.HOUR_OF_DAY))
                + String.valueOf(c.get(Calendar.MINUTE))
                + String.valueOf(c.get(Calendar.SECOND));
        FileOutputStream out2;


        imageFileName = new File(imageFileFolder, path + ".png");

        try {
            out2 = new FileOutputStream(imageFileName);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out2);
            out = out2;
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }
        return imageFileName.getPath();
    }

}
