package com.omyrobin.stander;

import android.app.Service;
import android.content.Intent;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/2 10:01 上午
 * @Description:
 */
public interface ServiceInterface {

    /**
     * 注入宿主的context
     * @param appService
     */
    void insertAppContext(Service appService);

    /**
     * Service生命周期
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    int onStartCommand(Intent intent, int flags, int startId);

}
