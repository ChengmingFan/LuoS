package com.fan.soulkiller.voice;

import static de.robv.android.xposed.callbacks.XC_LoadPackage.*;

import com.google.gson.Gson;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Chengming Fan on 2023/2/24 3:41 PM
 */

public class VoiceHook implements IXposedHookLoadPackage {
    private static final String TAG = "VOICE_TAG";
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("cn.soulapp.android")) {
            XposedBridge.log("======soul助手开始工作了======");
            Class<?> managerClazz = loadPackageParam.classLoader.loadClass("cn.soulapp.android.component.planet.voicematch.utils.CallMatchManager");
            Class<?> matchResultClazz = loadPackageParam.classLoader.loadClass("com.soulapp.android.planet.bean.MatchResult");
            Class<?> identityOpenControllerClazz = loadPackageParam.classLoader.loadClass("cn.soulapp.android.component.planet.voicematch.model.IdentityOpenController");
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
