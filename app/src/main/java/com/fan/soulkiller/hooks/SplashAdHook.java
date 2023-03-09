package com.fan.soulkiller.hooks;

import android.util.Log;

import com.fan.soulkiller.utils.Helper;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/2/27 11:16 PM
 */
public class SplashAdHook implements IHook{
    Class<?> MainScheduler$onCreate$6Clazz;
    Class<?> MainActivityClazz;

    @Override
    public String getName() {
        return "去开屏广告";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        MainScheduler$onCreate$6Clazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.MainScheduler$onCreate$6");
        MainActivityClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.MainActivity");
    }

    @Override
    public void hook() throws Throwable {
        if (!Helper.prefs.getBoolean("switch_ad", false)) {
            return;
        }
        XposedHelpers.findAndHookMethod(MainScheduler$onCreate$6Clazz, "invoke", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d(TAG, "beforeHookedMethod: MainScheduler$onCreate$6Clazz");
                Field mainSchedulerField = MainScheduler$onCreate$6Clazz.getDeclaredField("this$0");
                mainSchedulerField.setAccessible(true);
                Object mainScheduler = mainSchedulerField.get(param.thisObject);
                param.setResult(null);
                XposedHelpers.callMethod(mainScheduler, "q");
            }
        });

        // 移除切屏时的广告 暂时未实现
        XposedHelpers.findAndHookMethod(MainActivityClazz, "doShowAd", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

    }
}
