package com.deanxd.tinker.module;

import com.tencent.tinker.lib.service.DefaultTinkerResultService;
import com.tencent.tinker.lib.service.PatchResult;

/**
 * Tinker Patch安装结果 回调监听
 */
public class MResultService extends DefaultTinkerResultService {

    @Override
    public void onPatchResult(PatchResult result) {
    }

    @Override
    public boolean checkIfNeedKill(PatchResult result) {
        return true;
    }
}
