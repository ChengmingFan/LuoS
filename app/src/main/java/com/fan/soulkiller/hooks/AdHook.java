package com.fan.soulkiller.hooks;

import android.util.Log;

import com.fan.soulkiller.utils.Helper;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/2/27 11:16 PM
 */
public class AdHook implements IHook{
    Class<?> MainScheduler$onCreate$6Clazz;
    @Override
    public String getName() {
        return "去广告";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        MainScheduler$onCreate$6Clazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.MainScheduler$onCreate$6");
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
                if (mainScheduler != null) {
                    Log.d(TAG, "beforeHookedMethod: 1");
                } else{
                    Log.d(TAG, "beforeHookedMethod: 2");
                }
                param.setResult(null);
                XposedHelpers.callMethod(mainScheduler, "q");
            }
        });

    }
}
