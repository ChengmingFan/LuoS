package com.fan.soulkiller.hooks;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/3/5 21:52
 */
public class SquareAdHook implements IHook{
    Class<?> AdViewProvider$bClazz;
    Class<?> AdViewProviderClazz;
    Class<?> MainActivityClazz;
    Class<?> HeavenFragmentClazz;
    Class<?> LayoutInflaterClazz;
    Class<?> ViewGroupClazz;
    Class<?> ViewClazz;
    Class<?> BundleClazz;
    Class<?> StagePublisherClazz;
    Class<?> SplashAdManagerClazz;
    Class<?> HotAdManagerClazz;
    @Override
    public String getName() {
        return "广场广告";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        AdViewProvider$bClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.AdViewProvider$b");
        AdViewProviderClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.AdViewProvider");
        MainActivityClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.MainActivity");
        HeavenFragmentClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.HeavenFragment");
        LayoutInflaterClazz = classLoader.loadClass("android.view.LayoutInflater");
        ViewGroupClazz = classLoader.loadClass("android.view.ViewGroup");
        ViewClazz = classLoader.loadClass("android.view.View");
        BundleClazz = classLoader.loadClass("android.os.Bundle");
        StagePublisherClazz = classLoader.loadClass("cn.soulapp.android.component.startup.utils.j0");
        SplashAdManagerClazz = classLoader.loadClass("u5.e");
        HotAdManagerClazz = classLoader.loadClass("u5.c");
    }

    @Override
    public void hook() throws Throwable {
        XposedHelpers.findAndHookMethod(MainActivityClazz, "doHeavenRunning", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                Object mainActivity = param.thisObject;
//                Field cField = MainActivityClazz.getDeclaredField("c");
//                cField.setAccessible(true);
//                Object adViewProvider = cField.get(mainActivity);
//                Field jField = AdViewProviderClazz.getDeclaredField("j");
//                jField.setAccessible(true);
//                jField.set(adViewProvider, false);
//                param.setResult(null);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field cField = MainActivityClazz.getDeclaredField("c");
                cField.setAccessible(true);
                Object adV = cField.get(param.thisObject);
            }
        });

        XposedHelpers.findAndHookMethod(HeavenFragmentClazz, "onCreateView", LayoutInflaterClazz, ViewGroupClazz, BundleClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Object obj = XposedHelpers.callStaticMethod(StagePublisherClazz, "b");
                List<Integer> result = new ArrayList<>();
                if (obj instanceof ArrayList<?>) {
                    for (Object o : (List<?>) obj) {
                        result.add((Integer) o);
                    }
                }
                Object splashAdManager = XposedHelpers.callStaticMethod(SplashAdManagerClazz, "f");
                Method kMethod = SplashAdManagerClazz.getDeclaredMethod("k");
                kMethod.setAccessible(true);
                Object res = kMethod.invoke(splashAdManager);
            }
        });

        XposedHelpers.findAndHookMethod(AdViewProvider$bClazz, "onAdShow", ViewClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: oonAdShow");
            }
        });

        XposedHelpers.findAndHookMethod(HotAdManagerClazz, "a", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                param.setResult(null);
            }
        });

    }
}
