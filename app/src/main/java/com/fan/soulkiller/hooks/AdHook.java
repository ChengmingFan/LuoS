package com.fan.soulkiller.hooks;

import java.lang.reflect.Field;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/2/27 11:16 PM
 */
public class AdHook implements IHook{
    Class<?> mainActivityClazz;
    Class<?> AdViewProviderClazz;
    @Override
    public String getName() {
        return "去广告";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        mainActivityClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.MainActivity");
        AdViewProviderClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.AdViewProvider");
    }

    @Override
    public void hook() throws Throwable {
        Field c = mainActivityClazz.getDeclaredField("c");
        XposedHelpers.findAndHookConstructor(mainActivityClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Field c = mainActivityClazz.getDeclaredField("c");
                c.setAccessible(true);
                Object mainActivity = param.getResult();
                c.set(mainActivity, null);
            }
        });
    }
}
