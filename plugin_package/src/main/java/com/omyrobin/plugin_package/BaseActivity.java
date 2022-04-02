package com.omyrobin.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.omyrobin.stander.ActivityInterface;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/1 9:53 上午
 * @Description:
 */
public class BaseActivity extends AppCompatActivity implements ActivityInterface {

    //宿主对象
    public Activity appActivity;

    @Override
    public void insertAppContext(Activity appActivity) {
        this.appActivity = appActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setContentView(int resId) {
        appActivity.setContentView(resId);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onPause() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

    @Override
    public <T extends View> T findViewById(int id) {
        return appActivity.findViewById(id);
    }

    @Override
    public void startActivity(Intent intent) {
        Intent newIntent = new Intent();
        newIntent.putExtra("className", intent.getComponent().getClassName());
        //调用宿主的startActivity
        appActivity.startActivity(newIntent);
    }

    @Nullable
    @Override
    public ComponentName startService(Intent service) {
        Intent newIntent = new Intent();
        newIntent.putExtra("className", service.getComponent().getClassName());
        //调用宿主的startService
        return appActivity.startService(newIntent);
    }
}
