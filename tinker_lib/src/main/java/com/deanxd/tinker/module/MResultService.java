package com.deanxd.tinker.module;

import android.widget.Toast;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;

/**
 * Tinker Patch安装结果 回调监听
 */
public class MResultService extends DefaultTinkerResultService {

    @Override
    public void onPatchResult(PatchResult result) {
        Toast.makeText(getApplicationContext(), "Patch安装成功", Toast.LENGTH_SHORT).show();
        super.onPatchResult(result);
    }

    @Override
    public boolean checkIfNeedKill(PatchResult result) {
        return true;
    }
}
