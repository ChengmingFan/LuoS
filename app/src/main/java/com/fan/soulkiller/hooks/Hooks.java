package com.fan.soulkiller.hooks;

import android.widget.Toast;

import com.fan.soulkiller.utils.Helper;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by Chengming Fan on 2023/2/25 9:46 PM
 */
public class Hooks {
    static final IHook[] defaultHooks = {
            new VoiceHook(),
            new VideoHook(),
            new SplashAdHook(),
            new SquareAdHook(),
            new CommentAdHook()
    };

    public static void init(final ClassLoader classLoader) {
        for (IHook hook : defaultHooks) {
            try {
                hook.init(classLoader);
                hook.hook();
            } catch (Throwable e) {
                Helper.toast(hook.getName() + "功能加载失败，可能不支持当前版本Soul: " + Helper.packageInfo.versionName, Toast.LENGTH_LONG);
                XposedBridge.log("[SoulKiller] " + e.toString());
            }
        }
    }
}
