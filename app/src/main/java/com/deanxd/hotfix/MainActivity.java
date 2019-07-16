package com.deanxd.hotfix;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.deanxd.tinker.TinkerCheckPatchService;
import com.deanxd.tinker.TinkerManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main1);

//        TinkerCheckPatchService.runCheckPatchService(this);
    }

    public void loadPatch(View view) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "patch_signed_7zip.apk";
        TinkerManager.loadPatch(filePath, "");
    }
}