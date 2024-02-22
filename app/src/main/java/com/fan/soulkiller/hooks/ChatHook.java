package com.fan.soulkiller.hooks;

import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

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
    public void hook() throws ClassNotFoundException {

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


//        XposedHelpers.findAndHookMethod("cn.soulapp.android.component.chat.widget.PromptText",
//                classLoader,
//                "i",
//                classLoader.loadClass("cn.soulapp.imlib.msg.ImMessage"),
//                classLoader.loadClass("cn.soulapp.android.component.chat.widget.PromptText$a"),
//                new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//            }
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });


        XposedHelpers.findAndHookMethod("cn.soulapp.imlib.msg.chat.ChatMessage", classLoader, "i", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                if (param.getResult().equals(9)) {
                    param.setResult(1);
                }
            }
        });

//        XposedHelpers.findAndHookMethod("cn.soulapp.android.component.chat.fragment.MsgFragment", classLoader, "D1", boolean.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                param.args[0] = false;
//                XposedBridge.log("111");
//            }
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });



    }
}
