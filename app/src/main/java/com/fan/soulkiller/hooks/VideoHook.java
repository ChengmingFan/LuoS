package com.fan.soulkiller.hooks;

import android.util.Log;

import com.google.gson.Gson;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Chengming Fan on 2023/2/24 11:49 PM
 */
public class VideoHook implements IHook {
    Class<?> videoMatchControllerClazz;
    Class<?> videoMatchResultClazz;
    Class<?> callbackClazz;

    @Override
    public String getName() {
        return "视频";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        XposedBridge.log("======soul助手(视频模块)开始工作了======");
        videoMatchControllerClazz = classLoader.loadClass("cn.soulapp.android.component.planet.videomatch.VideoMatchController");
        videoMatchResultClazz = classLoader.loadClass("cn.soulapp.android.component.planet.videomatch.api.bean.VideoMatchResult");
        callbackClazz = classLoader.loadClass("cn.soulapp.android.component.planet.videomatch.VideoMatchController$i");
    }

    @Override
    public void hook() throws Throwable {
        XposedHelpers.findAndHookMethod(videoMatchControllerClazz, "O", videoMatchResultClazz, boolean.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "视频匹配成功，尝试消除马赛克");
                Object videoMatchController = XposedHelpers.callStaticMethod(videoMatchControllerClazz, "I");
                XposedHelpers.callMethod(videoMatchController, "k");
            }
        });

        XposedHelpers.findAndHookMethod(callbackClazz, "a", videoMatchResultClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "正在视频匹配。。。。。。");
            }
        });
    }
}
