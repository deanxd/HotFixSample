package com.deanxd.tinker.module;

import android.content.Context;
import android.widget.Toast;

import com.deanxd.tinker.TinkerManager;
import com.tencent.tinker.lib.listener.DefaultPatchListener;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerLoadResult;
import com.tencent.tinker.lib.util.TinkerServiceInternals;
import com.tencent.tinker.lib.util.UpgradePatchRetry;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;

public class MPatchListener extends DefaultPatchListener {

    private String currentMD5;

    public void setCurrentMD5(String currentMD5) {
        this.currentMD5 = currentMD5;
    }

    public MPatchListener(Context context) {
        super(context);
    }

    @Override
    protected int patchCheck(String path, String patchMd5) {
        //校验md5
        Tinker manager = Tinker.with(context);
        //check SharePreferences also
        if (!manager.isTinkerEnabled() || !ShareTinkerInternals.isTinkerEnableWithSharedPreferences(context)) {
            showToast("ShareConstants.ERROR_PATCH_DISABLE");
            return ShareConstants.ERROR_PATCH_DISABLE;
        }
        File file = new File(path);

        if (!SharePatchFileUtil.isLegalFile(file)) {
            showToast("ShareConstants.ERROR_PATCH_NOTEXIST");

            return ShareConstants.ERROR_PATCH_NOTEXIST;
        }

        //patch service can not send request
        if (manager.isPatchProcess()) {
            showToast("ShareConstants.ERROR_PATCH_INSERVICE");

            return ShareConstants.ERROR_PATCH_INSERVICE;
        }

        //if the patch service is running, pending
        if (TinkerServiceInternals.isTinkerPatchServiceRunning(context)) {
            showToast("ShareConstants.ERROR_PATCH_RUNNING");
            return ShareConstants.ERROR_PATCH_RUNNING;
        }

        if (ShareTinkerInternals.isVmJit()) {
            showToast("ShareConstants.ERROR_PATCH_JIT");
            return ShareConstants.ERROR_PATCH_JIT;
        }

        Tinker tinker = Tinker.with(context);

        if (tinker.isTinkerLoaded()) {
            TinkerLoadResult tinkerLoadResult = tinker.getTinkerLoadResultIfPresent();
            if (tinkerLoadResult != null && !tinkerLoadResult.useInterpretMode) {
                String currentVersion = tinkerLoadResult.currentVersion;
                if (patchMd5.equals(currentVersion)) {
                    return ShareConstants.ERROR_PATCH_ALREADY_APPLY;
                }
            }
        }

        if (!UpgradePatchRetry.getInstance(context).onPatchListenerCheck(patchMd5)) {
            return ShareConstants.ERROR_PATCH_RETRY_COUNT_LIMIT;
        }

        return ShareConstants.ERROR_PATCH_OK;
    }


    private void showToast(String msg) {
        Toast.makeText(TinkerManager.getApplicationContext(), "patch check Result: " + msg, Toast.LENGTH_SHORT).show();
    }
}
