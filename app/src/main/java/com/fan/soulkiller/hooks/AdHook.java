package com.fan.soulkiller.hooks;

import android.util.Log;
import android.view.View;

import com.fan.soulkiller.utils.Helper;
import com.google.gson.Gson;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/2/27 11:16 PM
 */
public class AdHook implements IHook{
    Class<?> mainActivityClazz;
    Class<?> AdViewProviderClazz;
    Class<?> TimeCostCountClazz;
    Class<?> ClodStartupTimingClazz;
    Class<?> AdResponseClazz;
    Class<?> SplashAdImplClazz;
    Class<?> SquareRecycleAutoUtilsClazz;
    Class<?> ViewClazz;
    Class<?> AdCommentProviderClazz;
    Class<?> LayoutInflaterClazz;
    Class<?> ViewGroupClazz;
    Class<?> AdCommentViewHolderClazz;
    Class<?> LightAdapterClazz;
    Class<?> SoulUnifiedAdClazz;
    Class<?> ViewHolderProviderClazz;
    @Override
    public String getName() {
        return "去广告";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        mainActivityClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.MainActivity");
        AdViewProviderClazz = classLoader.loadClass("cn.soulapp.android.component.startup.main.AdViewProvider");
        TimeCostCountClazz = classLoader.loadClass("cn.soulapp.android.component.startup.j");
        ClodStartupTimingClazz = classLoader.loadClass("cn.soulapp.android.component.startup.utils.k");
        AdResponseClazz = classLoader.loadClass("cn.soulapp.android.ad.api.bean.AdResponse");
        SplashAdImplClazz = classLoader.loadClass("s5.b");
        SquareRecycleAutoUtilsClazz = classLoader.loadClass("cn.soulapp.android.component.square.utils.SquareRecycleAutoUtils");
        ViewClazz = classLoader.loadClass("android.view.View");
        AdCommentProviderClazz = classLoader.loadClass("cn.soulapp.android.component.square.main.ad.a");
        LayoutInflaterClazz = classLoader.loadClass("android.view.LayoutInflater");
        ViewGroupClazz = classLoader.loadClass("android.view.ViewGroup");
        AdCommentViewHolderClazz = classLoader.loadClass("cn.soulapp.android.component.square.main.ad.AdCommentViewHolder");
        LightAdapterClazz = classLoader.loadClass("com.lufficc.lightadapter.LightAdapter");
        SoulUnifiedAdClazz = classLoader.loadClass("q3.a");
        ViewHolderProviderClazz = classLoader.loadClass("vy.g");
    }

    @Override
    public void hook() throws Throwable {
        if (!Helper.prefs.getBoolean("switch_ad", false)) {
            return;
        }
        // 开屏广告
        XposedBridge.log("======soul助手(去广告模块)开始工作了======");
        XposedHelpers.findAndHookMethod(AdViewProviderClazz, "R", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: R: " + param.args[0]);
                param.setResult(null);
                XposedHelpers.findAndHookMethod(TimeCostCountClazz, "a", "app_main_show_ad_end");
            }
        });
        XposedHelpers.findAndHookMethod(mainActivityClazz, "doShowAd", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: doShowAd");
                param.setResult(null);
                XposedHelpers.findAndHookMethod(TimeCostCountClazz, "a", "app_main_show_ad_end");
            }
        });

        XposedHelpers.findAndHookMethod(AdViewProviderClazz, "onStart", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: onStart");
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod(ClodStartupTimingClazz, "n", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d(TAG, "beforeHookedMethod: n: " + param.args[0]);
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod(SplashAdImplClazz, "loadAd", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: loadAd: " + param.args[0]);
                param.setResult(null);
                super.beforeHookedMethod(param);
            }
        });


        XposedHelpers.findAndHookMethod(SquareRecycleAutoUtilsClazz, "s", View.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Method getTag = ViewClazz.getMethod("getTag", int.class);
                getTag.setAccessible(true);
                Object tag = getTag.invoke(param.args[0], 2131300162);
                if (tag == null) {
                    param.setResult(null);
                    return;
                }
                Log.d(TAG, "beforeHookedMethod: s:" + new Gson().toJson(tag));
                boolean isInstance = AdResponseClazz.isInstance(tag);
                Log.d(TAG, "beforeHookedMethod: s: " + isInstance);
            }
        });


        XposedHelpers.findAndHookMethod(AdCommentProviderClazz, "d", LayoutInflaterClazz, ViewGroupClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                param.setResult(AdCommentViewHolderClazz.newInstance());
            }
        });

        XposedHelpers.findAndHookMethod(LightAdapterClazz, "y", Class.class, ViewHolderProviderClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (SoulUnifiedAdClazz.equals(param.args[0])) {
                    param.setResult(null);
                }
            }
        });

    }
}
