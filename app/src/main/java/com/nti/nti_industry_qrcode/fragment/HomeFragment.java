package com.nti.nti_industry_qrcode.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gyf.immersionbar.ImmersionBar;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.module_returninbound.activity.ReturnInboundActivity;
import com.nti.module_salesexactory.activity.SalesFactoryActivity;
import com.nti.nti_industry_qrcode.R;
import com.nti.nti_industry_qrcode.databinding.FragmentHomeBinding;
import com.nti.nti_industry_qrcode.databinding.FragmentSettingBinding;


public class HomeFragment extends SimpleImmersionFragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.rrXscc.setOnClickListener(this);
        binding.rrThrk.setOnClickListener(this);
        binding.rrYkck.setOnClickListener(this);
        binding.rrYkrk.setOnClickListener(this);
        binding.rrDhrk.setOnClickListener(this);
        binding.rrThck.setOnClickListener(this);
        binding.rrBf.setOnClickListener(this);
        binding.rrSy.setOnClickListener(this);
        binding.rrBfbm.setOnClickListener(this);
        return root;
    }

    public void immersionBarInit(){
        ImmersionBar.with(this)
                .statusBarColor(R.color.white)
                .fitsSystemWindows(false)
                .autoDarkModeEnable(true)
                .navigationBarColor(R.color.white)
                .init();
    }

    @Override
    public void initImmersionBar() {
        immersionBarInit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rr_xscc:
                Intent intent = new Intent(getActivity(), SalesFactoryActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.rr_thrk:
                /*Intent intent2 = new Intent(getActivity(), ReturnInboundActivity.class);
                getActivity().startActivity(intent2);*/

                ARouter.getInstance().build(ARouterPath.RETURNINBOUND_PATH)
                        .navigation();
                break;
            case R.id.rr_ykck:
                ARouter.getInstance().build(ARouterPath.MOVEOUTBOUND_PATH)
                        .navigation();
                break;
            case R.id.rr_ykrk:
                ARouter.getInstance().build(ARouterPath.MOVEINBOUND_PATH)
                        .navigation();
                break;
            case R.id.rr_dhrk:
                ARouter.getInstance().build(ARouterPath.PRODUCEINBOUND_PATH)
                        .navigation();
                break;
            case R.id.rr_thck:
                ARouter.getInstance().build(ARouterPath.PRODUCEOUTBOUND_PATH)
                        .navigation();
                break;
            case R.id.rr_bf:
                ARouter.getInstance().build(ARouterPath.SCRAP_PATH)
                        .navigation();
                break;
            case R.id.rr_bfbm:
                ARouter.getInstance().build(ARouterPath.SCRAP_CODE_PATH)
                        .navigation();
                break;
            case R.id.rr_sy:
                ARouter.getInstance().build(ARouterPath.LOSS_PAT)
                        .navigation();
                break;

        }
    }
}