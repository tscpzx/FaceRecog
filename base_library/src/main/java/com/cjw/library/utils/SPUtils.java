package com.cjw.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
	private static String spName = "sp_cache";

	public static void putString(Context context, String key, String str) {
		SharedPreferences.Editor sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE).edit();
		sp.putString(key, str);
		sp.apply();
	}

	public static String getString(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}
}
