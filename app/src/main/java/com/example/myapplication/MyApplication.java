package com.example.myapplication;

import androidx.multidex.MultiDexApplication;

import com.example.myapplication.network.NetWorkManager;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
    }
}
