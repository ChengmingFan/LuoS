package com.fan.soulkiller.hooks;

import android.util.Log;

import com.fan.soulkiller.utils.Helper;

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
    private Class<?> adProPostProviderClazz;
    private Class<?> adPostViewHolder;
    private Class<?> soulUnifiedAdClazz;
    private Class<?> cSqItemSquareAdPostV2BindingClazz;
    private Class<?> cSqItemSquareAdPostV3BindingClazz;

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
        adProPostProviderClazz= classLoader.loadClass("cn.soulapp.android.component.square.main.ad.u");
        adPostViewHolder = classLoader.loadClass("cn.soulapp.android.component.square.main.ad.AdPostViewHolder");
        soulUnifiedAdClazz = classLoader.loadClass("q3.a");
        cSqItemSquareAdPostV2BindingClazz = classLoader.loadClass("cn.soulapp.android.component.square.databinding.CSqItemSquareAdPostV2Binding");
        cSqItemSquareAdPostV3BindingClazz = classLoader.loadClass("cn.soulapp.android.component.square.databinding.CSqItemSquareAdPostV3Binding");
    }

    @Override
    public void hook() throws Throwable {
        if (!Helper.prefs.getBoolean("switch_ad", false)) {
            return;
        }
        XposedHelpers.findAndHookMethod(HeavenFragmentClazz, "onCreateView", LayoutInflaterClazz, ViewGroupClazz, BundleClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Object obj = XposedHelpers.callStaticMethod(StagePublisherClazz, "b");
                Log.d(TAG, "beforeHookedMethod: onCreateView: " + obj.toString());
                List<Integer> result = new ArrayList<>();
                if (obj instanceof ArrayList<?>) {
                    for (Object o : (List<?>) obj) {
                        result.add((Integer) o);
                        Log.d(TAG, "beforeHookedMethod: onCreateView: " + (Integer) o);
                    }
                }
                Object splashAdManager = XposedHelpers.callStaticMethod(SplashAdManagerClazz, "f");
                Method kMethod = SplashAdManagerClazz.getDeclaredMethod("k");
                kMethod.setAccessible(true);
                Object res = kMethod.invoke(splashAdManager);
                Log.d(TAG, "beforeHookedMethod: onCreateView: " + res);
            }
        });

        XposedHelpers.findAndHookMethod(HotAdManagerClazz, "a", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod(adProPostProviderClazz, "d", LayoutInflaterClazz, ViewGroupClazz, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod(cSqItemSquareAdPostV2BindingClazz, "bind", ViewClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

    }
}
