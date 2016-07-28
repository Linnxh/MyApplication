package com.example.myapplication;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class UnInstallActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        remove();
    }

    public void remove() {
        //调用卸载的界面，要去上层源码中看一下卸载界面的意图是什么。通过查看源码，如下：
        /**
         * <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <action android:name="android.intent.action.DELETE" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="package" />
         </intent-filter>
         */

        //在卸载之前首先要取消设激活设备管理器
        //获取设备管理器
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName who = new ComponentName(this, DeviceAdminSample.class);
        dpm.removeActiveAdmin(who);//Remove a current administration component. This can only be called by the application that owns the administration component

        //卸载，启动卸载界面
        Intent remove = new Intent("android.intent.action.DELETE");
        remove.addCategory("android.intent.category.DEFAULT");
        remove.setData(Uri.parse("package:" + getPackageName()));//表示卸载本程序，Return the name of this application's package.

        startActivity(remove);//卸载用户apk界面
        finish();
    }
}
