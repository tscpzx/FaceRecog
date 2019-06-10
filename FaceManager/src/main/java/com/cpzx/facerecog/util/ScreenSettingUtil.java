package com.cpzx.facerecog.util;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

/**
 * created by xwr on 2019/6/10
 * 手机系统屏幕亮度设置
 */
public class ScreenSettingUtil {
    /**
     * 获取当前屏幕亮度的模式
      * @param context
     * @return
     */
    public static int getScreenMode(Context context){
        int screenMode= 0;
        try {
            screenMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return  screenMode;
    }

    /**
     * 获取当前屏幕亮度值0~255
     * @param context
     * @return
     */
    public static int  getScreenBrightnes(Context context){
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }
    /**
     * 设置当前屏幕亮度值 0--255
     */
    public static  void saveScreenBrightness(Context context,int paramInt) {
        try {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, paramInt);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     */
    public static void setScreenBrightness(Activity activity, int paramInt) {
        Window localWindow =activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

}
