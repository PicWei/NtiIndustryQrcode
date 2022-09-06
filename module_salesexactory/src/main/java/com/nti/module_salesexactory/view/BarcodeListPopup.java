package com.nti.module_salesexactory.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.nti.lib_common.bean.MessageEvent;
import com.nti.lib_common.constants.BusinessType;
import com.nti.lib_common.view.SlideListView;
import com.nti.module_salesexactory.adapter.BarcodeAdapter;
import com.nti.module_salesexactory.bean.SalesBarcode;
import com.nti.module_salesexactory.bean.SalesFactoryDetail;
import com.nti.module_salesexactory.bean.SalesFactoryOrderInfo;

import org.greenrobot.eventbus.EventBus;
import org.litepal.LitePal;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/24
 * @describe
 */
public class BarcodeListPopup extends BottomPopupView {

    private Context context;
    private SlideListView listview;
    private BarcodeAdapter adapter;
    private List<SalesBarcode> list;
    private TextView close_btn;

    public BarcodeListPopup(@NonNull Context context, List<SalesBarcode> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    protected int getImplLayoutId() {
        return com.nti.lib_common.R.layout.barcode_list;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        listview = findViewById(com.nti.lib_common.R.id.listview);
        close_btn = findViewById(com.nti.lib_common.R.id.close_btn);
        adapter = new BarcodeAdapter(context, list);
        adapter.setOnDeleteListener(new BarcodeAdapter.OnDeleteListener() {
            @Override
            public void onDeleteListener(View view, int position) {
                String barcode = list.get(position).getBarcode();
                String pcigcode = list.get(position).getPcigcode();
                List<SalesFactoryDetail> detailList = LitePal.where("BD_PCIG_CODE = ?", pcigcode).find(SalesFactoryDetail.class);
                SalesFactoryDetail detail = detailList.get(0);
                String uuid = detail.getBD_BB_UUID();
                String BD_SCAN_NUM = detail.getBD_SCAN_NUM();
                int scannum = Integer.parseInt(BD_SCAN_NUM) - 1;
                String scanednumStr = String.valueOf(scannum);
                detail.setBD_SCAN_NUM(scanednumStr);
                detail.saveOrUpdate("BD_PCIG_CODE = ?", pcigcode);
                List<SalesFactoryOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(SalesFactoryOrderInfo.class);
                SalesFactoryOrderInfo orderInfo = orderInfos.get(0);
                String totalscannumStr = orderInfo.getBB_TOTAL_SCAN_NUM();
                int totalscannum = Integer.parseInt(totalscannumStr) - 1;
                String totalscanednumStr = String.valueOf(totalscannum);
                orderInfo.setBB_TOTAL_SCAN_NUM(totalscanednumStr);
                orderInfo.saveOrUpdate("BB_UUID = ?", uuid);
                LitePal.deleteAll(SalesBarcode.class, "barcode = ?", barcode);
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        listview.setAdapter(adapter);
        close_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.what = BusinessType.BUSINESS_SALESFACTORY;
                EventBus.getDefault().post(messageEvent);
                dismiss();
            }
        });
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    //完全消失执行
    @Override
    protected void onDismiss() {
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * .7f);
    }

}