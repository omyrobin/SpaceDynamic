package com.omyrobin.space;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/1 9:56 上午
 * @Description:
 */
public class PluginManager {

    private DexClassLoader classLoader;

    private Resources pluginResources;

    private static PluginManager instance;

    private PluginManager() { }

    public static PluginManager getInstance() {
        if(instance == null) {
            instance = new PluginManager();
        }
        return instance;
    }

    public void loadPlugin(Context context, String pluginPath) {
        preformHiddenMethod();

        try{
            loadDex(context,pluginPath);
            loadResource(context, pluginPath);
        }catch (Exception e) {
            Log.i("TAG",e.getMessage());
        }
    }

    /**
     * 解决Android 10以上版本反射调用hidden method 问题
     */
    private void preformHiddenMethod() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
            Object sVmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
        } catch (Throwable e) {
            Log.e("[error]", "reflect bootstrap failed:", e);
        }
    }

    /**
     * 通过DexClassLoader 加载未安装的插件apk
     * @param pluginPath
     * @param context
     * @throws Exception
     */
    private void loadDex(Context context, String pluginPath) throws Exception {
        File cacheDir = context.getDir("pluginDir", Context.MODE_PRIVATE);
        classLoader = new DexClassLoader(pluginPath,cacheDir.getAbsolutePath(), null,context.getClassLoader());
    }

    /**
     * 通过反射获取插件apk中的资源
     * @param context
     * @param pluginPath
     * @throws Exception
     */
    private void loadResource(Context context, String pluginPath) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPathMethod.invoke(assetManager,pluginPath);
        Resources r = context.getResources();
        pluginResources = new Resources(assetManager,r.getDisplayMetrics(),r.getConfiguration());
    }

    public DexClassLoader getClassLoader() {
        return classLoader;
    }

    public Resources getResources() {
        return pluginResources;
    }
}
