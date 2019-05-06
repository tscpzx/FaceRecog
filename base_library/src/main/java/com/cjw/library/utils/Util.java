package com.cjw.library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.widget.Toast;


public class Util {
	private static Handler mHandler = new Handler(Looper.getMainLooper());

	public static void runOnUIThread(Runnable r) {
		if (isRunOnUIThread()) {
			// 已经是主线程, 直接运行
			r.run();
		} else {
			// 如果是子线程, 借助handler让其运行在主线程
			mHandler.post(r);
		}
	}

	// 获取图片
	public static Drawable getDrawable(Context context, int id) {
		return ContextCompat.getDrawable(context, id);
	}

	// 获取颜色
	public static int getColor(Context context, int id) {
		return ContextCompat.getColor(context, id);
	}

	// /////////////////dip和px转换//////////////////////////
	public static int dip2px(Context context, float dip) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dip * density + 0.5f);
	}

	public static float px2dip(Context context, int px) {
		float density = context.getResources().getDisplayMetrics().density;
		return px / density;
	}

	// /////////////////获取屏幕高度和宽度//////////////////////////
	public static int getHeightPixels(Context context, double d) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int heightPixels = metrics.heightPixels;
		return (int) (heightPixels * d);
	}

	public static int getWidthPixels(Context context, double i) {
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int widthPixels = metrics.widthPixels;
		return (int) (widthPixels * i);
	}

	// /////////////////判断是否运行在主线程//////////////////////////
	public static boolean isRunOnUIThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}

	public static void showToast(final Context mContext, final String s) {
		runOnUIThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
