package com.cpzx.facerecog.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author xuwanru
 * @date 2018/10/4.
 */
public class SharedPreferenceUtil {
    private static SharedPreferenceUtil sharedPreferenceUtil;
    private static SharedPreferences sharedPreferences = null;

    public static SharedPreferenceUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil();
            sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        }
        return sharedPreferenceUtil;
    }

    public void putValues(ContentValue... contentValues) {
        //获取编辑器，才能进行存储
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (ContentValue contentValue : contentValues) {
            if (contentValue.value instanceof String) {
                editor.putString(contentValue.key, contentValue.value.toString()).commit();
            } else if (contentValue.value instanceof Integer) {
                editor.putInt(contentValue.key, Integer.parseInt(contentValue.value.toString())).commit();
            } else if (contentValue.value instanceof Boolean) {
                editor.putBoolean(contentValue.key, Boolean.parseBoolean(contentValue.value.toString())).commit();
            } else if (contentValue.value instanceof Long) {
                editor.putLong(contentValue.key, Long.parseLong(contentValue.value.toString())).commit();
            }
        }
    }

    //获取数据
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1);
    }

    //清除当前文件所有数据
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }

    public static class ContentValue {
        String key;
        Object value;

        public ContentValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

}
