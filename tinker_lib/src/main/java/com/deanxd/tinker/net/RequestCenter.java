package com.deanxd.tinker.net;

import com.deanxd.lib.net.CommonOkHttpClient;
import com.deanxd.lib.net.listener.DisposeDataHandle;
import com.deanxd.lib.net.listener.DisposeDataListener;
import com.deanxd.lib.net.listener.DisposeDownloadListener;
import com.deanxd.lib.net.request.CommonRequest;
import com.deanxd.tinker.bean.BasePatch;

public class RequestCenter {

    /**
     * 询问是否有patch可更新
     */
    public static void requestPatchUpdateInfo(DisposeDataListener listener) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(HttpConstant.UPDATE_PATCH_URL, null),
                new DisposeDataHandle(listener, String.class));
    }

    /**
     * 文件下载
     */
    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }
}
