package com.deanxd.tinker;

import android.annotation.SuppressLint;
import android.content.Context;

import com.deanxd.tinker.module.MPatchListener;
import com.deanxd.tinker.module.MResultService;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * 对Tinker的所有api做一层封装
 */
public class TinkerManager {

    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;
    @SuppressLint("StaticFieldLeak")
    private static MPatchListener mPatchListener;

    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;

        if (!isInstalled) {
            //完成tinker初始化

            Context context = mAppLike.getApplication().getApplicationContext();

            DefaultLoadReporter loadReporter = new DefaultLoadReporter(context);
            DefaultPatchReporter patchReporter = new DefaultPatchReporter(context);
            mPatchListener = new MPatchListener(context);
            UpgradePatch upgradePatch = new UpgradePatch();

            TinkerInstaller.install(mAppLike, loadReporter, patchReporter, mPatchListener, MResultService.class, upgradePatch);
            isInstalled = true;
        }
    }

    /**
     * 完成Patch文件的加载
     */
    public static void loadPatch(String path, String md5Value) {
        if (!Tinker.isTinkerInstalled()) {
            throw new RuntimeException("please call TinkerManager.installTinker(ApplicationLike) first");
        }
        if (mPatchListener != null) {
            mPatchListener.setCurrentMD5(md5Value);
        }
        Context context = getApplicationContext();
        TinkerInstaller.onReceiveUpgradePatch(context, path);
    }

    /**
     * 通过ApplicationLike获取Context
     */
    private static Context getApplicationContext() {
        if (mAppLike == null) {
            throw new RuntimeException("please call TinkerManager.installTinker(ApplicationLike) first");
        }
        return mAppLike.getApplication().getApplicationContext();
    }
}
