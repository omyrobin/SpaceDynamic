# SpaceDynamic
学习使用------占位式插件化

1.AGP 7.0 支持Android 10+
2.少量使用了hidden method 
3.支持Activity + Service

## 核心原理
Dexclassload加载插件apk
Resource AssetManager 反射创建

```
/**
     * 通过DexClassLoader 加载未安装的插件apk
     * @param pluginPath
     * @param context
     * @throws Exception
     */
    private void loadDex(Context context, String pluginPath) throws Exception {
        File cacheDir = context.getDir("pluginDir", Context.MODE_PRIVATE);
        classLoader = new DexClassLoader(pluginPath,cacheDir.getAbsolutePath(), null,context.getClassLoader());
    }

    /**
     * 通过反射获取插件apk中的资源
     * @param context
     * @param pluginPath
     * @throws Exception
     */
    private void loadResource(Context context, String pluginPath) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
        addAssetPathMethod.invoke(assetManager,pluginPath);
        Resources r = context.getResources();
        pluginResources = new Resources(assetManager,r.getDisplayMetrics(),r.getConfiguration());
    }


```

占位式通过一个Proxy类 代理Android组件 分发生命周期，加载插件中的组件


