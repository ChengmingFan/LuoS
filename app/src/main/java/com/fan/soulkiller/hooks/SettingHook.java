package com.fan.soulkiller.hooks;

/**
 * Created by Chengming Fan on 2023/2/25 10:13 PM
 */
public class SettingHook implements IHook{

    private Class<?> groupSettingItemViewClazz;

    @Override
    public String getName() {
        return "设置";
    }

    @Override
    public void init(ClassLoader classLoader) throws Throwable {
        groupSettingItemViewClazz = classLoader.loadClass("cn.soulapp.android.component.group.view.GroupSettingItemView");
        groupSettingItemViewClazz = classLoader.loadClass("cn.soulapp.android.component.group.view.GroupSettingItemView");
    }

    @Override
    public void hook() throws Throwable {

    }
}
