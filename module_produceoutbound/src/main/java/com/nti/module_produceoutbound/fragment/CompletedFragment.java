package com.nti.module_produceoutbound.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.SalesBarcodeParamer;
import com.nti.lib_common.bean.SalesOrderParamer;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;
import com.nti.lib_common.bean.SellParamer;
import com.nti.lib_common.bean.UploadSellParamer;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.lib_common.viewmodel.SellBarcodeReciveViewModel;
import com.nti.module_produceoutbound.R;
import com.nti.module_produceoutbound.adapter.CompletedAdapter;
import com.nti.module_produceoutbound.bean.ProduceoutboundBarcode;
import com.nti.module_produceoutbound.bean.ProduceoutboundDetail;
import com.nti.module_produceoutbound.bean.ProduceoutboundOrderInfo;
import com.ruffian.library.widget.RTextView;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompletedFragment extends Fragment {

    private CompletedAdapter adapter;
    private boolean ispressed;
    private List<String> uuids = new ArrayList<>();
    private List<ProduceoutboundOrderInfo> orderInfos = new ArrayList<>();
    private SellBarcodeReciveViewModel viewModel;
    private RelativeLayout selectAllRl;
    private RTextView submitBtn;
    private RecyclerView recyclerView;
    private TextView totalTv;
    private ImageView radioBtn;

    public static CompletedFragment newInstance() {
        CompletedFragment fragment = new CompletedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);
        selectAllRl = view.findViewById(R.id.select_all_rl);
        submitBtn = view.findViewById(R.id.submit_btn);
        recyclerView = view.findViewById(R.id.recyclerView);
        totalTv = view.findViewById(R.id.total_tv);
        radioBtn = view.findViewById(R.id.radio_btn);
        viewModel = new ViewModelProvider(this).get(SellBarcodeReciveViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        orderInfos = LitePal.where("BB_STATE = ?", "3").find(ProduceoutboundOrderInfo.class);
        for (int i = 0; i < orderInfos.size(); i++){
            ProduceoutboundOrderInfo orderInfo = orderInfos.get(i);
            orderInfo.setIspressed(false);
        }
        adapter = new CompletedAdapter(getActivity(), orderInfos);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CompletedAdapter.OnItemClickListener() {
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
        adapter.setOnCheckListener(new CompletedAdapter.onCheckListener() {
            @Override
            public void onCheckChange(List<ProduceoutboundOrderInfo> orderInfos) {
                uuids.clear();
                for (ProduceoutboundOrderInfo orderInfo : orderInfos){
                    if (orderInfo.isIspressed()){
                        uuids.add(orderInfo.getBB_UUID());
                    }
                }
                int size = uuids.size();
                totalTv.setText(size+"");
                if (size == orderInfos.size()){
                    radioBtn.setImageResource(R.mipmap.radion_pressed);
                    ispressed = true;
                }
                if (size == 0){
                    radioBtn.setImageResource(R.mipmap.radio_normal);
                    ispressed = false;
                }
            }
        });
        selectAllRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ispressed){
                    radioBtn.setImageResource(R.mipmap.radio_normal);
                    ispressed = false;
                    for (ProduceoutboundOrderInfo orderInfo : orderInfos){
                        orderInfo.setIspressed(false);
                        uuids.clear();
                    }
                    adapter.notifyDataSetChanged();
                    int size = uuids.size();
                    totalTv.setText(size+"");
                }else {
                    radioBtn.setImageResource(R.mipmap.radion_pressed);
                    ispressed = true;
                    uuids.clear();
                    for (ProduceoutboundOrderInfo orderInfo : orderInfos){
                        orderInfo.setIspressed(true);
                        uuids.add(orderInfo.getBB_UUID());
                    }
                    adapter.notifyDataSetChanged();
                    int size = uuids.size();
                    totalTv.setText(size+"");
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SellParamer> sellParamers = new ArrayList<>();
                for (ProduceoutboundOrderInfo orderInfo : orderInfos){
                    if (orderInfo.isIspressed()){
                        String uuid = orderInfo.getBB_UUID();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String BI_FEEDBACK_TIME = format.format(new Date());
                        List<ProduceoutboundDetail> details = LitePal.where("BD_BB_UUID = ?", uuid).find(ProduceoutboundDetail.class);
                        List<SalesOrderParamer> paramers = new ArrayList<>();
                        for (int i = 0; i < details.size(); i++){
                            String pcigcode = details.get(i).getBD_PCIG_CODE();
                            String pnum = details.get(i).getBD_BILL_PNUM();
                            String snum = details.get(i).getBD_SCAN_NUM();
                            List<SalesBarcodeParamer> salesBarcodeParamers = new ArrayList<>();
                            List<ProduceoutboundBarcode> barcodes = LitePal.where("UUID = ? and pcigcode = ? and isSubmit = 0", uuid, pcigcode).find(ProduceoutboundBarcode.class);
                            if (barcodes.size() > 0){
                                for (int j = 0; j < barcodes.size(); j++){
                                    String barcode = barcodes.get(j).getBarcode();
                                    String scantime = barcodes.get(j).getScantime();
                                    String scancode = barcodes.get(j).getScancode();
                                    SalesBarcodeParamer salesBarcodeParamer = new SalesBarcodeParamer(barcode, scantime, BI_FEEDBACK_TIME);
                                    salesBarcodeParamer.setBI_SERIAL_NO("");
                                    salesBarcodeParamer.setBI_LOCAL_SCAN_DATE(scantime);
                                    salesBarcodeParamer.setBI_PACK_ID("");
                                    salesBarcodeParamer.setBI_SCANNER_CODE(scancode);
                                    salesBarcodeParamers.add(salesBarcodeParamer);
                                }
                                SalesOrderParamer salesOrderParamer = new SalesOrderParamer(Integer.parseInt(pnum), pcigcode, Integer.parseInt(snum), salesBarcodeParamers);
                                paramers.add(salesOrderParamer);
                            }
                        }
                        SellParamer sellParamer = new SellParamer(uuid, paramers);
                        sellParamers.add(sellParamer);
                    }
                }
                if (sellParamers.size() == 0){
                    Toast.makeText(getActivity(), "数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String SYSTEM_SERV = "INDUT_COOPERATIVE_RETURN";
                UploadSellParamer uploadSellParamer = new UploadSellParamer(sellParamers, SYSTEM_SERV);
                SellBarcodeReciveParamer paramer = new SellBarcodeReciveParamer(uploadSellParamer);
                Log.i("TAG", "paramer:" + paramer.toString());
                viewModel.sellBarcodeRecive(paramer).observe(getActivity(), new Observer<DataResult<JsonObject>>() {
                    @Override
                    public void onChanged(DataResult<JsonObject> dataResult) {
                        int errcode = dataResult.getErrcode();
                        if (errcode == 0){
                            JsonObject jsonObject = dataResult.getT();
                            String code = jsonObject.get("code").toString().replace("\"", "");
                            String message = jsonObject.get("message").toString().replace("\"", "");
                            if (code.equals("0")){
                                ContentValues cv = new ContentValues();
                                cv.put("isSubmit", true);
                                for (int i = 0; i < uuids.size(); i++){
                                    String uuid = uuids.get(i);
                                    LitePal.updateAll(ProduceoutboundBarcode.class, cv, "UUID = ?", uuid);
                                    List<ProduceoutboundOrderInfo> salesFactoryOrderInfos = LitePal.where("BB_UUID = ?", uuid).find(ProduceoutboundOrderInfo.class);
                                    List<ProduceoutboundBarcode> barcodes = LitePal.where("UUID = ?", uuid).find(ProduceoutboundBarcode.class);
                                    int size = barcodes.size();
                                    String totalNum = salesFactoryOrderInfos.get(0).getBB_TOTAL_PNUM();
                                    String scanNum = salesFactoryOrderInfos.get(0).getBB_TOTAL_SCAN_NUM();
                                    int mTotalNum = Integer.valueOf(totalNum);
                                    int mScanNum = Integer.valueOf(scanNum);
                                    if (mTotalNum == (size + mScanNum)){
                                        LitePal.deleteAll(ProduceoutboundBarcode.class, "BB_UUID = ?", uuid);
                                    }
                                }
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        uuids.clear();
        orderInfos.clear();
    }
}