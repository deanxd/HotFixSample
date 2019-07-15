package com.deanxd.tinker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.deanxd.lib.net.listener.DisposeDataListener;
import com.deanxd.lib.net.listener.DisposeDownloadListener;
import com.deanxd.tinker.net.HttpConstant;
import com.deanxd.tinker.net.RequestCenter;

import java.io.File;

/**
 * 应用程序Tinker更新服务：
 * <p>
 * 1.从服务器下载patch文件
 * 2.使用TinkerManager完成patch文件加载
 * 3.patch文件会在下次进程启动时生效
 */
public class TinkerCheckPatchService extends Service {

    private final static String TAG = "TAG_tinker>>>";

    private static final String FILE_END = ".apk"; //文件后缀名
    private static final String CACHE_FILE_NAME = "tpatch";

    private static final int DOWNLOAD_PATCH = 0x01; //下载patch文件信息
    private static final int UPDATE_PATCH = 0x02; //检查是否有patch更新

    private String mPatchFileDir; //patch要保存的文件夹
    private String mFilePatch; //patch文件保存路径

    public static void runCheckPatchService(Context context) {
        Intent intent = new Intent(context, TinkerCheckPatchService.class);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PATCH:
                    checkPatchInfo();
                    break;
                case DOWNLOAD_PATCH:
                    downloadPatch();
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //检查是否有patch更新
        mHandler.sendEmptyMessage(DOWNLOAD_PATCH);
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 初始化变量
     */
    private void init() {
        File cacheDir = getExternalCacheDir();
        String absolutePath;
        if (cacheDir != null) {
            absolutePath = cacheDir.getAbsolutePath();
        } else {
            absolutePath = getCacheDir().getAbsolutePath();
        }
        mPatchFileDir = absolutePath + File.separator + CACHE_FILE_NAME + File.separator;

        try {
            File patchFileDir = new File(mPatchFileDir);
            if (!patchFileDir.exists()) {
                boolean isSuccess = patchFileDir.mkdirs();
                if (!isSuccess) {
                    throw new RuntimeException("create cache file error");
                }
            }
        } catch (Exception e) {
            //无法正常创建文件，则终止服务
            e.printStackTrace();
            stopSelf();
        }
    }

    private void checkPatchInfo() {
        RequestCenter.requestPatchUpdateInfo(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.d(TAG, " patch check success");
                mHandler.sendEmptyMessage(DOWNLOAD_PATCH);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d(TAG, " patch check failed");
                stopSelf();
            }
        });
    }

    private void downloadPatch() {
        Log.d(TAG, " start download path");

        mFilePatch = mPatchFileDir.concat(String.valueOf(System.currentTimeMillis()))
                .concat(FILE_END);
        RequestCenter.downloadFile(HttpConstant.DOWNLOAD_PATCH_URL, mFilePatch,
                new DisposeDownloadListener() {
                    @Override
                    public void onProgress(int progress) {
                        Log.d(TAG, " patch download progress:" + progress);
                    }

                    @Override
                    public void onSuccess(Object responseObj) {
                        Log.d(TAG, " patch download success");
                        TinkerManager.loadPatch(mFilePatch, "");
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Log.d(TAG, " patch download failed");
                        stopSelf();
                    }
                });
    }
}
