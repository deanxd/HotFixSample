package com.deanxd.javafix.app;

import android.support.multidex.MultiDexApplication;

import com.deanxd.javafix.core.FixDexUtils;


public class FixApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        boolean isFixSuccess = FixDexUtils.loadFixFile(this);
    }
}
