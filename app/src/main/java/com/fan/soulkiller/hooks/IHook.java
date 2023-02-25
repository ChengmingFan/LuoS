package com.fan.soulkiller.hooks;

public interface IHook {
    String TAG = "LuoS_TAG";
    String getName();
    void init(final ClassLoader classLoader) throws Throwable;
    void hook() throws Throwable;
}