package com.nti.module_produceoutbound.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.module_produceoutbound.R;
import com.nti.module_produceoutbound.adapter.OngoingAdapter;
import com.nti.module_produceoutbound.bean.ProduceoutboundOrderInfo;


import org.litepal.LitePal;

import java.util.List;

public class OngoingFragment extends Fragment {

    private RecyclerView recyclerView;
    private OngoingAdapter adapter;

    public static OngoingFragment newInstance() {
        OngoingFragment fragment = new OngoingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<ProduceoutboundOrderInfo> orderInfos = LitePal.where("BB_STATE = ?", "1").find(ProduceoutboundOrderInfo.class);
        adapter = new OngoingAdapter(getActivity(), orderInfos);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OngoingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProduceoutboundOrderInfo orderInfo) {
                String uuid = orderInfo.getBB_UUID();
                String BB_CONTRACT_NO = orderInfo.getBB_CONTRACT_NO();
                String BB_FLOW_NAME = orderInfo.getBB_FLOW_NAME();
                ARouter.getInstance().build(ARouterPath.PRODUCEOUTBOUNDDETAIL_PATH)
                        .withString("contractNo", BB_CONTRACT_NO)
                        .withString("flowName", BB_FLOW_NAME)
                        .withString("uuid", uuid)
                        .navigation();
            }
        });
        return view;
    }
}