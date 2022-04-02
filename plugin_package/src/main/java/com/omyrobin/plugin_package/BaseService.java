package com.omyrobin.plugin_package;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.omyrobin.stander.ServiceInterface;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/2 10:02 上午
 * @Description:
 */
public class BaseService extends Service implements ServiceInterface {

    private Service appService;

    @Override
    public void insertAppContext(Service appService) {
        this.appService = appService;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {

    }


}
