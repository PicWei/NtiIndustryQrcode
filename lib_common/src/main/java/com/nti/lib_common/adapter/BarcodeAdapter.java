package com.nti.lib_common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nti.lib_common.R;
import com.nti.lib_common.view.SlideItem;


import org.litepal.LitePal;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/24
 * @describe
 */
public class BarcodeAdapter {

    private Context context;
    private LayoutInflater inflater;
    private OnDeleteListener listener;

    public void setOnDeleteListener(OnDeleteListener listener){
        this.listener = listener;
    }

    class ViewHolder{
        TextView order_number;
        TextView barcode;
        TextView detail;
        TextView itemTvDelete;

        public ViewHolder(View center, View menu) {
            this.order_number = center.findViewById(R.id.order_number);
            this.barcode = center.findViewById(R.id.barcode);
            this.detail = center.findViewById(R.id.detail);
            this.itemTvDelete = menu.findViewById(R.id.item_delete);
        }
    }

    public interface OnDeleteListener{
        void onDeleteListener(View view, int position);
    }

}
