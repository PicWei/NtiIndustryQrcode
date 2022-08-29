package com.nti.lib_common.view;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.nti.lib_common.R;
import com.nti.lib_common.adapter.BarcodeAdapter;


import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/23
 * @describe
 */
public class BarcodeListPopup extends BottomPopupView {

    private Context context;
    private SlideListView listview;
    private BarcodeAdapter adapter;


    public BarcodeListPopup(@NonNull Context context) {
        super(context);
        this.context = context;

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.barcode_list;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        listview = findViewById(R.id.listview);
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
