package com.example.myapplication;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    private DevicePolicyManager dmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取设备管理员对象
        dmp = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        lockScreen(null);
    }

    public void lockScreen(View view) {
        // 如果没有激活设备管理员，提醒用户做事
        ComponentName who = new ComponentName(this, DeviceAdminSample.class);
        if (dmp.isAdminActive(who)) {// true表示已经激活，测试管理员是否已经激活。这里的who其实就是我们自定义的空的广播接收者
            dmp.lockNow();// 一键锁屏
            finish();
        } else {
            ////帮助用户打开激活设备管理器的界面，让用户手动激活
            /**根据官方文档，写出启动设备管理器的活动
             * // Launch the activity to have the user enable our admin. Intent
             * intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
             * intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
             * mDeviceAdminSample);
             * intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
             * mActivity.getString(R.string.add_admin_extra_app_text));
             * startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
             */

            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    who);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "设备管理器。。。");
            startActivityForResult(intent, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            lockScreen(null);
        }
    }
}
