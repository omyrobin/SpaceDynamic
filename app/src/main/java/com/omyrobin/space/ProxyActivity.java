package com.omyrobin.space;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.omyrobin.stander.ActivityInterface;

import java.lang.reflect.Constructor;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/1 10:50 上午
 * @Description:
 */
public class ProxyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchPluginActivity(savedInstanceState);
    }

    private void launchPluginActivity(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String className = intent.getStringExtra("className");
        try {
            Class<?> pluginClass = getClassLoader().loadClass(className);
            Constructor constructor = pluginClass.getConstructor(new Class[]{});
            Object pluginActivity = constructor.newInstance(new Object[]{});

            //注入ProxyActivity Context 让插件拥有宿主的环境
            ActivityInterface activityInterface = (ActivityInterface) pluginActivity;
            activityInterface.insertAppContext(this);

            //调用plugin Activity onCreate() 同步生命周期
            activityInterface.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    /**
     * 很重要 插件里的类加载 需要使用这个classloader
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getClassLoader();
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent proxyIntent = new Intent(this, ProxyActivity.class);
        proxyIntent.putExtra("className", className);
        super.startActivity(proxyIntent);
    }

    @Nullable
    @Override
    public ComponentName startService(Intent service) {
        String className = service.getStringExtra("className");
        Intent proxyIntent = new Intent(this, ProxyService.class);
        proxyIntent.putExtra("className", className);
        return super.startService(proxyIntent);
    }
}
