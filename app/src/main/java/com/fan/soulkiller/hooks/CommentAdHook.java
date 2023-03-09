package com.fan.soulkiller.hooks;

import android.util.Log;

import com.fan.soulkiller.utils.Helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/3/5 22:09
 */
public class CommentAdHook implements IHook{

    private static final String TAG = "LuoS_Comment";
    private Class<?> lightAdapterClazz;
    private Class<?> viewHolderProviderClazz;
    private Class<?> soulUnifiedAdClazz;
    private Class<?> postDetailHeaderProviderClazz;
    private Class<?> postDetailHeaderProvider$g;
    private Class<?> cSqItemDetailAdCommentBindingClazz;
    private Class<?> viewClass;
    private Class<?> soulUnifiedAdLoaderImplClazz;
    private Class<?> adSlotClazz;
    private Class<?> unifiedAdCacheApiClazz;

    @Override
    public String getName() {
        return "评论区广告";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        lightAdapterClazz = classLoader.loadClass("com.lufficc.lightadapter.LightAdapter");
        viewHolderProviderClazz = classLoader.loadClass("vy.g");
        soulUnifiedAdClazz = classLoader.loadClass("q3.a");
        postDetailHeaderProviderClazz = classLoader.loadClass("cn.soulapp.android.component.square.post.base.detail.PostDetailHeaderProvider");
        postDetailHeaderProvider$g = classLoader.loadClass("cn.soulapp.android.component.square.post.base.detail.PostDetailHeaderProvider$g");
        cSqItemDetailAdCommentBindingClazz = classLoader.loadClass("cn.soulapp.android.component.square.databinding.CSqItemDetailAdCommentBinding");
        viewClass = classLoader.loadClass("android.view.View");
        soulUnifiedAdLoaderImplClazz = classLoader.loadClass("l4.d");
        adSlotClazz = classLoader.loadClass("l3.a");
        unifiedAdCacheApiClazz = classLoader.loadClass("r5.g");
    }

    @Override
    public void hook() throws Throwable {
        if (!Helper.prefs.getBoolean("switch_ad", false)) {
            return;
        }
        XposedHelpers.findAndHookMethod(lightAdapterClazz, "y", Class.class, viewHolderProviderClazz, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (soulUnifiedAdClazz.isInstance(param.args[0])) {
                    param.setResult(null);
                }
            }
        });

        XposedHelpers.findAndHookMethod(postDetailHeaderProviderClazz, "a0", soulUnifiedAdClazz, postDetailHeaderProvider$g, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.args[0] = null;
                super.beforeHookedMethod(param);
            }
        });

        XposedHelpers.findAndHookMethod(cSqItemDetailAdCommentBindingClazz, "bind", viewClass, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(null);
            }
        });

        XposedHelpers.findAndHookMethod(soulUnifiedAdLoaderImplClazz, "loadAds", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Object soulUnifiedAdLoader = param.thisObject;
                Field adSlotField = soulUnifiedAdLoaderImplClazz.getDeclaredField("a");
                adSlotField.setAccessible(true);
                Object adSlot = adSlotField.get(soulUnifiedAdLoader);
                Field aField = adSlotClazz.getDeclaredField("a");
                aField.setAccessible(true);
                aField.set(adSlot, "18");
            }
        });

        XposedHelpers.findAndHookMethod(soulUnifiedAdLoaderImplClazz, "h", List.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: onLoadSuccess");
                param.args[0] = new ArrayList<>();
            }
        });

        XposedHelpers.findAndHookMethod(soulUnifiedAdLoaderImplClazz, "onAdFailed", int.class, String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.d(TAG, "beforeHookedMethod: onAdFailed");
            }
        });
    }
}
