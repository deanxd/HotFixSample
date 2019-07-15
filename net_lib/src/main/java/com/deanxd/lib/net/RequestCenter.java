package com.deanxd.lib.net;


import com.deanxd.lib.net.listener.DisposeDataHandle;
import com.deanxd.lib.net.listener.DisposeDataListener;
import com.deanxd.lib.net.listener.DisposeDownloadListener;
import com.deanxd.lib.net.request.CommonRequest;
import com.deanxd.lib.net.request.RequestParams;

/**
 * 请求发送中心
 */
public class RequestCenter {

    /**
     * 根据参数发送所有post请求
     */
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }


    /**
     * 询问是否有patch可更新
     */
    public static void requestPatchUpdateInfo(DisposeDataListener listener) {
//        RequestCenter.postRequest(HttpConstant.UPDATE_PATCH_URL, null, listener,
//                BasePatch.class);
    }

    /**
     * 文件下载
     */
    public static void downloadFile(String url, String path, DisposeDownloadListener listener) {
        CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(url, null),
                new DisposeDataHandle(listener, path));
    }
}
