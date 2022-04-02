package com.omyrobin.plugin_package;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @Author: wubo
 * @CreateDate: 2022/4/1 9:55 上午
 * @Description:
 */
public class PluginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);

        Toast.makeText(appActivity,"我是插件Activity", Toast.LENGTH_SHORT).show();

        findViewById(R.id.btn_jump_inner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appActivity, PluginInnerActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_jump_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appActivity, PluginService.class);
                startService(intent);
            }
        });
    }



}