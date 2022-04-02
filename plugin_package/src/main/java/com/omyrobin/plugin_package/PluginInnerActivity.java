package com.omyrobin.plugin_package;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/1 2:36 下午
 * @Description:
 */
public class PluginInnerActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_inner);
    }
}
