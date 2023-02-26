package com.fan.soulkiller.hooks;

import static de.robv.android.xposed.callbacks.XC_LoadPackage.*;

import com.fan.soulkiller.utils.Helper;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Created by Chengming Fan on 2023/2/24 3:41 PM
 */

public class VoiceHook implements IHook {
    Class<?> managerClazz;
    Class<?> matchResultClazz;
    Class<?> identityOpenControllerClazz;
    @Override
    public String getName() {
        return "语音";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        managerClazz = classLoader.loadClass("cn.soulapp.android.component.planet.voicematch.utils.CallMatchManager");
        matchResultClazz = classLoader.loadClass("com.soulapp.android.planet.bean.MatchResult");
        identityOpenControllerClazz = classLoader.loadClass("cn.soulapp.android.component.planet.voicematch.model.IdentityOpenController");
    }

    @Override
    public void hook() throws Throwable {
        if (Helper.prefs.getBoolean("switch_voice", true)) {
            XposedBridge.log("======soul助手(语音模块)开始工作了======");
            XposedHelpers.findAndHookMethod(managerClazz, "y", matchResultClazz, int.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Object identityOpenController = XposedHelpers.callStaticMethod(identityOpenControllerClazz, "c");
                    XposedHelpers.callMethod(identityOpenController, "k", false);
                }
            });
        }
    }
}
