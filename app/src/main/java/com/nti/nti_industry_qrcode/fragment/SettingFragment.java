package com.nti.nti_industry_qrcode.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.nti_industry_qrcode.R;
import com.nti.nti_industry_qrcode.databinding.FragmentSettingBinding;


public class SettingFragment extends SimpleImmersionFragment implements View.OnClickListener{

    private FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.rrDevice.setOnClickListener(this);
        binding.rrVersion.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rr_device:
                ARouter.getInstance().build(ARouterPath.DEVICE_PATH)
                        .withBoolean("flag", true)
                        .navigation();
                break;
            case R.id.rr_version:
                ARouter.getInstance().build(ARouterPath.VERSION_PATH).navigation();
                break;
        }
    }

    public void immersionBarInit(){
        ImmersionBar.with(this)
                .transparentStatusBar()
                .fitsSystemWindows(false)
                .autoDarkModeEnable(true)
                .navigationBarColor(R.color.white)
                .init();
    }

    @Override
    public void initImmersionBar() {
        immersionBarInit();
    }
}