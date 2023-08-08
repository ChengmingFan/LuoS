package com.fan.soulkiller.hooks;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/8/8 14:45
 */
public class ChatHook implements IHook {
    private ClassLoader classLoader;
    @Override
    public String getName() {
        return "聊天";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        this.classLoader = classLoader;
    }

    @Override
    public void hook() {

        XposedHelpers.findAndHookMethod("cn.soulapp.android.lib.common.bean.ChatLimitModel", classLoader, "isLimit", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XposedBridge.log("送礼物才能私聊? 去你的!");
                param.setResult(false);
            }
        });
    }
}
