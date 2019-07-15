package com.deanxd.tinker.module;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;

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
        return super.patchCheck(path, patchMd5);
    }
}
