package com.omyrobin.plugin_package;

import android.content.Intent;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/2 10:09 上午
 * @Description:
 */
public class PluginService extends BaseService{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("我正在运行");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
