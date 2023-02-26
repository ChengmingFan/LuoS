package com.fan.soulkiller;

import android.app.Application;
import android.widget.Toast;

import com.fan.soulkiller.hooks.Hooks;
import com.fan.soulkiller.utils.Helper;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Created by Chengming Fan on 2023/2/25 9:30 PM
 */
public class MainHook implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("cn.soulapp.android")) {
            XposedHelpers.findAndHookMethod(android.app.Instrumentation.class, "callApplicationOnCreate", Application.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (param.args[0] instanceof Application) {
                        Helper.context = ((Application) param.args[0]).getApplicationContext();

                        if (!Helper.init(loadPackageParam.classLoader)) {
                            Helper.toast("LuoS初始化失败，可能不支持当前版本Soul: " + Helper.packageInfo.versionName, Toast.LENGTH_SHORT);
                        }else {
                            Hooks.init(loadPackageParam.classLoader);
                            Helper.toast("LuoS初始成功", Toast.LENGTH_SHORT);
                        }
                    }
                }
            });

        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        Helper.prefs = new XSharedPreferences("com.fan.soulkiller");
    }
}
