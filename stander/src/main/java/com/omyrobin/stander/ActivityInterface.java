package com.omyrobin.stander;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/1 9:52 上午
 * @Description:
 */
public interface ActivityInterface {
    /**
     * 注入宿主的context
     * @param appActivity
     */
    void insertAppContext(Activity appActivity);

    /**
     * Activity 生命周期
     * @param savedInstanceState
     */
    void onCreate(@Nullable Bundle savedInstanceState);

    /**
     * Activity 生命周期
     */
    void onResume();

    /**
     * Activity 生命周期
     */
    void onPause();

    /**
     * Activity 生命周期
     */
    void onStop();

    /**
     * Activity 生命周期
     */
    void onDestroy();
}
