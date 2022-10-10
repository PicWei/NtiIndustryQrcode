package com.nti.module_moveoutbound.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.nti.lib_common.activity.BaseActivity;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.MessageEvent;
import com.nti.lib_common.bean.Paramer;
import com.nti.lib_common.bean.Params;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.lib_common.constants.BusinessType;
import com.nti.lib_common.utils.DeviceUtils;
import com.nti.module_moveoutbound.R;
import com.nti.module_moveoutbound.bean.MoveoutboundOrderInfo;
import com.nti.module_moveoutbound.databinding.ActivityMoveoutboundBinding;
import com.nti.module_moveoutbound.fragment.CompletedFragment;
import com.nti.module_moveoutbound.fragment.IncompleteFragment;
import com.nti.module_moveoutbound.fragment.OngoingFragment;
import com.nti.module_moveoutbound.viewmodel.MoveoutboundViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.List;

//工业移库出库 INDUT_MOVE_OUTBOUND
@Route(path = ARouterPath.MOVEOUTBOUND_PATH)
public class MoveoutboundActivity extends BaseActivity implements View.OnClickListener{

    private ActivityMoveoutboundBinding binding;
    private MoveoutboundViewModel viewModel;
    private LoadingPopupView loadingPopup;
    private int current = 1;

    public static final String SYSTEM_SERVICE_TYPE = "INDUT_MOVE_OUTBOUND";

    String deviceId;

    //未完成
    private int unFinishCount;

    //进行中
    private int doingCount;

    //已完成
    private int finishCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moveoutbound);
        EventBus.getDefault().register(this);

        initListener();

        viewModel = new ViewModelProvider(this).get(MoveoutboundViewModel.class);

        deviceId = DeviceUtils.getDevUUID(this);

        loadData();

    }

    private void initListener() {
        binding.incompleteCl.setOnClickListener(this);
        binding.ongoingCl.setOnClickListener(this);
        binding.completedCl.setOnClickListener(this);
        binding.titleBar.findViewById(R.id.right_cl).setOnClickListener(this);
    }

    /**
     * 请求数据
     */
    private void loadData() {
        Params params = new Params(deviceId, SYSTEM_SERVICE_TYPE);
        Paramer paramer = new Paramer(params);
        if (loadingPopup == null) {
            loadingPopup = (LoadingPopupView)new XPopup.Builder(this)
                    .dismissOnBackPressed(true)
                    .isLightNavigationBar(true)
                    .asLoading("加载中...")
                    .show();
        }else {
            loadingPopup.show();
        }
        viewModel.PDA_H(paramer).observe(this, new Observer<DataResult<List<MoveoutboundOrderInfo>>>() {
            @Override
            public void onChanged(DataResult<List<MoveoutboundOrderInfo>> dataResult) {
                loadingPopup.dismiss();
                int errcode = dataResult.getErrcode();
                if (errcode == -1){
                    Toast.makeText(MoveoutboundActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }else {
                    List<MoveoutboundOrderInfo> MoveoutboundOrderInfos = dataResult.getT();
                    if (MoveoutboundOrderInfos == null || MoveoutboundOrderInfos.isEmpty()){
                        Toast.makeText(MoveoutboundActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
                    }else {
                        binding.incompleteCl.performClick();
                        loadViewCount();
                    }
                }
            }
        });
    }



    /**
     * 顶部tab数量
     */
    private void loadViewCount() {
        unFinishCount = LitePal.where("BB_STATE = ?", "4").count(MoveoutboundOrderInfo.class);
        doingCount = LitePal.where("BB_STATE = ?", "1").count(MoveoutboundOrderInfo.class);
        finishCount = LitePal.where("BB_STATE = ? and PDA_SCANNER_IS_END = ?", "3", "0").count(MoveoutboundOrderInfo.class);

        binding.incompleteNum.setText(unFinishCount + "");
        binding.ongoingNum.setText(doingCount + "");
        binding.completedNum.setText(finishCount + "");
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadViewCount();

        if (current == 1){
            binding.incompleteCl.performClick();
        }
        if (current == 2){
            binding.ongoingCl.performClick();
        }
        if (current == 3){
            binding.completedCl.performClick();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.incomplete_cl){
            current = 1;
            swithTab(R.id.incomplete_cl);
            Fragment incompleteFragment = IncompleteFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, incompleteFragment).commit();
        }else if (view.getId() == R.id.ongoing_cl){
            current = 2;
            swithTab(R.id.ongoing_cl);
            Fragment ongoingFragment = OngoingFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, ongoingFragment).commit();
        }else if (view.getId() == R.id.completed_cl){
            current = 3;
            swithTab(R.id.completed_cl);
            Fragment completedFragment = CompletedFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, completedFragment).commit();
        }else if ((view.getId()) == R.id.right_cl){
            loadData();
        }
    }

    private void swithTab(int tabId){
        if (tabId == R.id.incomplete_cl){
            binding.incompleteTv.setTextColor(getColor(R.color.text_press_bg));
            binding.incompleteNum.setTextColor(getColor(R.color.num_press));
            binding.rrNum01.setBackground(getDrawable(R.drawable.num_press_bg));
            binding.view01.setVisibility(View.VISIBLE);

            binding.ongoingTv.setTextColor(getColor(R.color.text_normal));
            binding.ongoingNum.setTextColor(getColor(R.color.num_normal));
            binding.rrNum02.setBackground(getDrawable(R.drawable.num_normal));
            binding.view02.setVisibility(View.GONE);

            binding.completedTv.setTextColor(getColor(R.color.text_normal));
            binding.completedNum.setTextColor(getColor(R.color.num_normal));
            binding.rrNum03.setBackground(getDrawable(R.drawable.num_normal));
            binding.view03.setVisibility(View.GONE);
        }else if (tabId == R.id.ongoing_cl){
            binding.incompleteTv.setTextColor(getColor(R.color.text_normal));
            binding.incompleteNum.setTextColor(getColor(R.color.num_normal));
            binding.rrNum01.setBackground(getDrawable(R.drawable.num_normal));
            binding.view01.setVisibility(View.GONE);

            binding.ongoingTv.setTextColor(getColor(R.color.text_press_bg));
            binding.ongoingNum.setTextColor(getColor(R.color.num_press));
            binding.rrNum02.setBackground(getDrawable(R.drawable.num_press_bg));
            binding.view02.setVisibility(View.VISIBLE);

            binding.completedTv.setTextColor(getColor(R.color.text_normal));
            binding.completedNum.setTextColor(getColor(R.color.num_normal));
            binding.rrNum03.setBackground(getDrawable(R.drawable.num_normal));
            binding.view03.setVisibility(View.GONE);
        }else if (tabId == R.id.completed_cl){
            binding.incompleteTv.setTextColor(getColor(R.color.text_normal));
            binding.incompleteNum.setTextColor(getColor(R.color.num_normal));
            binding.rrNum01.setBackground(getDrawable(R.drawable.num_normal));
            binding.view01.setVisibility(View.GONE);

            binding.ongoingTv.setTextColor(getColor(R.color.text_normal));
            binding.ongoingNum.setTextColor(getColor(R.color.num_normal));
            binding.rrNum02.setBackground(getDrawable(R.drawable.num_normal));
            binding.view02.setVisibility(View.GONE);

            binding.completedTv.setTextColor(getColor(R.color.text_press_bg));
            binding.completedNum.setTextColor(getColor(R.color.num_press));
            binding.rrNum03.setBackground(getDrawable(R.drawable.num_press_bg));
            binding.view03.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        switch (event.what){
            case BusinessType.UPDATA_ONGOING:
                loadViewCount();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}