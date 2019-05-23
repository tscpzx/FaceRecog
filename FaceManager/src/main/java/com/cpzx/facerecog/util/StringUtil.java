package com.cpzx.facerecog.util;

import java.util.List;

/**
 * created by xwr on 2019/5/21
 */
public class StringUtil {
    //数组拼接为字符串
    public static String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

}
