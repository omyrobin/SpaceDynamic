package com.omyrobin.space;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.omyrobin.stander.ActivityInterface;
import com.omyrobin.stander.ServiceInterface;

import java.lang.reflect.Constructor;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/2 10:15 上午
 * @Description:
 */
public class ProxyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        launchPluginService(intent, flags, startId);
        return super.onStartCommand(intent, flags, startId);
    }

    private void launchPluginService(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("className");
        try {
            Class<?> pluginClass = getClassLoader().loadClass(className);
            Constructor constructor = pluginClass.getConstructor(new Class[]{});
            Object pluginService = constructor.newInstance(new Object[]{});

            //注入ProxyActivity Context 让插件拥有宿主的环境
            ServiceInterface serviceInterface = (ServiceInterface) pluginService;
            serviceInterface.insertAppContext(this);

            //调用plugin Activity onCreate() 同步生命周期
            serviceInterface.onStartCommand(intent, flags, startId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 很重要 插件里的类加载 需要使用这个classloader
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getClassLoader();
    }
}
