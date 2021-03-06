package com.cpzx.facerecog.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 * created by xwr on 2019/5/16
 */
public class ImageUtil {

    public static String bytesToBase64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 压缩本地图片
     *
     * @param bytes
     * @return
     */
    public static byte[] compressImageByBytes(byte[] bytes) {


        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//解码时只返回bitmap尺寸
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, newOpts);
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 800f;
        float ww = 480f;
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率
        newOpts.inJustDecodeBounds = false;
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//色彩模式
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, newOpts);
        byte[] b = BitmapToBytes(bitmap, 100);
        return b;
    }


    /**
     * bitmap 转为byte[]
     *
     * @param bitmap
     * @return
     */
    public static byte[] BitmapToBytes(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }


    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            // 先通过getContentResolver方法获得一个ContentResolver实例，
            // 调用openInputStream(Uri)方法获得uri关联的数据流stream
            // 把上一步获得的数据流解析成为bitmap
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}