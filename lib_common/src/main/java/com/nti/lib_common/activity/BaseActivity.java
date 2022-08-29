package com.nti.lib_common.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.nti.lib_common.R;

/**
 * @author: weiqiyuan
 * @date: 2022/7/20
 * @describe
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式状态栏初始化
        immersionBarInit();
    }

    public void immersionBarInit(){
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .autoDarkModeEnable(true)
                .navigationBarColor(R.color.white)
                .init();
    }
}