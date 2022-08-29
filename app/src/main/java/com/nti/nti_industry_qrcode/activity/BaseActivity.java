package com.nti.nti_industry_qrcode.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.nti.nti_industry_qrcode.R;

/**
 * @author: weiqiyuan
 * @date: 2022/7/18
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
