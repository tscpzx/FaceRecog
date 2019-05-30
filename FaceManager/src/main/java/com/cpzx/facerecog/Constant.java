package com.cpzx.facerecog;

import com.cpzx.facerecog.model.User;

/**
 * created by xwr on 2019/5/10
 */
public class Constant {
    public static boolean isLogin = false;
    public static final int TAKE_PHOTO = 0x00;
    public static final int CHOOSE_PHOTO = 0x01;
    public static final int CROP_PHOTO = 0x03;
    public static final String IMAGE_FILE_NAME = "image.jpg";
    public static final String IMAGE_FILE_LOCATION = "temp.jpg";
    public static User CURRENT_USER = new User();
}
