package com.bob.android.clgl.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * @package com.bob.android.clgl.base
 * @fileName BaseActivity
 * @Author Bob on 2018/5/15 14:07.
 * @Describe TODO
 */

public  class BaseActivity extends AppCompatActivity {

    public Context mContext;
    public long exitTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
    }

    /**
     * 启动 activity
     * @param clazz
     * */
    protected  void startActivity(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (onBackQuit()) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }

    protected boolean onBackQuit() {
        return false;
    }
}
