package com.fan.soulkiller.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.widget.Toast;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by Chengming Fan on 2023/2/25 9:48 PM
 */
public class Helper {
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static PackageInfo packageInfo;

    public static boolean init(ClassLoader classLoader) {
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.zhihu.android", 0);
            return true;
        } catch (Exception e) {
            XposedBridge.log("[SoulKiller] " + e);
            return false;
        }
    }
    public static void toast(CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, "", duration);
        toast.setText("LuoS：" + text);
        toast.show();
    }
}
