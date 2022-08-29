package com.nti.nti_industry_qrcode.activity;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.nti_industry_qrcode.R;
import com.nti.nti_industry_qrcode.databinding.ActivityLoginBinding;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.rlLogin.setOnClickListener(this);
        binding.setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_login:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                ARouter.getInstance().build(ARouterPath.DEVICE_PATH)
                        .withBoolean("flag", false)
                        .navigation();
                break;
        }

    }
}