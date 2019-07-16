package com.deanxd.andfix.lib;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;
import com.deanxd.andfix.lib.util.Utils;

import java.io.File;
import java.io.IOException;

public class AndFixManager {

    private final static String TAG = "and fix >>>";

    private PatchManager mPatchManager;
    private static AndFixManager mFixManager;

    private AndFixManager() {

    }

    public static AndFixManager getInstance() {
        if (mFixManager == null) {
            synchronized (AndFixManager.class) {
                if (mFixManager == null) {
                    mFixManager = new AndFixManager();
                }
            }
        }
        return mFixManager;
    }

    public void init(Context context) {
        if (mPatchManager != null) {
            return;
        }
        mPatchManager = new PatchManager(context);
        mPatchManager.init(Utils.getVersionName(context));
        mPatchManager.loadPatch();
    }

    public void loadPatch(String filePath) {
        if (mPatchManager == null || TextUtils.isEmpty(filePath)) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try {
            mPatchManager.addPatch(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "addPatch error");
        }
    }

}
