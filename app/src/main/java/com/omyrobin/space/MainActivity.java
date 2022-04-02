package com.omyrobin.space;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button permissionsBtn = findViewById(R.id.btn_permissions);
        permissionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }
            }
        });

        Button loadPluginBtn = findViewById(R.id.btn_load);
        loadPluginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlugin();
            }
        });

        Button jumpBtn = findViewById(R.id.btn_jump);
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProxyActivity.class);
                intent.putExtra("className",getPluginManifestActivityClassName());
                startActivity(intent);
            }
        });
    }

    private void loadPlugin() {
        File pluginFile = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin_package-debug.apk");
        if(!pluginFile.exists()){
            Log.i("TAG","插件包不存在");
            return;
        }
        PluginManager.getInstance().loadPlugin(this, pluginFile.getAbsolutePath());
    }

    private String getPluginManifestActivityClassName() {
        File pluginFile = new File(Environment.getExternalStorageDirectory() + File.separator + "plugin_package-debug.apk");

        PackageManager packageManager = getPackageManager();
        PackageInfo info = packageManager.getPackageArchiveInfo(pluginFile.getAbsolutePath(),PackageManager.GET_ACTIVITIES);
        ActivityInfo activityInfo = info.activities[0];
        return activityInfo.name;
    }
}