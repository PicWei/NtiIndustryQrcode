package com.nti.module_loss.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.nti.lib_common.bean.MessageEvent;
import com.nti.lib_common.bean.UpParamer;
import com.nti.lib_common.bean.UpdataStatuesParamer;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.lib_common.constants.BusinessType;
import com.nti.module_loss.R;
import com.nti.module_loss.adapter.IncompleteAdapter;
import com.nti.module_loss.bean.LossOrderInfo;
import com.nti.module_loss.viewmodel.LossViewModel;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.List;

public class IncompleteFragment extends Fragment {

    private RecyclerView recyclerView;
    private LossViewModel viewModel;
    private IncompleteAdapter adapter;

    public static IncompleteFragment newInstance() {
        IncompleteFragment fragment = new IncompleteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incomplete, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        viewModel = new ViewModelProvider(this).get(LossViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<LossOrderInfo> orderInfos = LitePal.where("BB_STATE = ?", "4").find(LossOrderInfo.class);
        adapter = new IncompleteAdapter(getActivity(), orderInfos);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new IncompleteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LossOrderInfo orderInfo) {
                String uuid = orderInfo.getBB_UUID();
                String BB_CONTRACT_NO = orderInfo.getBB_CONTRACT_NO();
                String BB_FLOW_NAME = orderInfo.getBB_FLOW_NAME();
                String BB_STATE = "1";
                String BB_UUID = orderInfo.getBB_UUID();
                String SYSTEM_SERVICE_TYPE= "INDUT_WAREHOUSE_EXCESSIVE";
                UpdataStatuesParamer updataStatuesParamer = new UpdataStatuesParamer(BB_UUID, BB_STATE, SYSTEM_SERVICE_TYPE);
                UpParamer upParamer = new UpParamer(updataStatuesParamer);
                viewModel.updataSellListStatues(upParamer).observe(getActivity(), new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {

                        if (jsonObject == null){
                            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                        }else {
                            String code = jsonObject.get("code").toString().replace("\"", "");
                            if (code.equals("0")){
                                orderInfo.setBB_STATE("1");
                                orderInfo.saveOrUpdate("BB_UUID = ?", BB_UUID);
                                ARouter.getInstance().build(ARouterPath.LOSSDETAIL_PATH)
                                        .withString("contractNo", BB_CONTRACT_NO)
                                        .withString("flowName", BB_FLOW_NAME)
                                        .withString("uuid", uuid)
                                        .navigation();
                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.what = BusinessType.UPDATA_ONGOING;
                                EventBus.getDefault().post(messageEvent);
                            }else {
                                Toast.makeText(getActivity(), "更改状态失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
            }
        });
        return view;
    }
}