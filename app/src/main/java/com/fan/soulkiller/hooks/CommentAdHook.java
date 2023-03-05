package com.fan.soulkiller.hooks;

import android.util.Log;

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
    }

    @Override
    public void hook() throws Throwable {
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
                Log.d(TAG, "beforeHookedMethod: a0000");
                param.args[0] = null;
                super.beforeHookedMethod(param);
            }
        });
    }
}
