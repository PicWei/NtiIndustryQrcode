package com.nti.module_salesexactory.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nti.lib_common.view.SlideItem;
import com.nti.module_salesexactory.R;
import com.nti.module_salesexactory.bean.SalesBarcode;

import org.litepal.LitePal;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/24
 * @describe
 */
public class BarcodeAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private OnDeleteListener listener;
    private List<SalesBarcode> list;

    public BarcodeAdapter(Context context, List<SalesBarcode> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null){
            View content = inflater.inflate(com.nti.lib_common.R.layout.barcode_item,null);
            View menu = inflater.inflate(R.layout.adapter_item_menu,null);
            holder = new ViewHolder(content,menu);
            SlideItem slideItem = new SlideItem(context);
            slideItem.setContentView(content, menu);
            convertView = slideItem;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        SalesBarcode salesBarcode = list.get(i);
        String barcode = salesBarcode.getBarcode();
        holder.order_number.setText((i + 1) + "");
        holder.barcode.setText(barcode);
        holder.detail.setText(salesBarcode.getPcigname() + "/" + salesBarcode.getScantime());
        holder.itemTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onDeleteListener(v, i);
                }
                /*list.remove(i);
                LitePal.deleteAll(SalesBarcode.class, "barcode = ?", barcode);*/
            }
        });
        return convertView;
    }

    public void setOnDeleteListener(OnDeleteListener listener){
        this.listener = listener;
    }

    class ViewHolder{
        TextView order_number;
        TextView barcode;
        TextView detail;
        TextView itemTvDelete;

        public ViewHolder(View center, View menu) {
            this.order_number = center.findViewById(com.nti.lib_common.R.id.order_number);
            this.barcode = center.findViewById(com.nti.lib_common.R.id.barcode);
            this.detail = center.findViewById(com.nti.lib_common.R.id.detail);
            this.itemTvDelete = menu.findViewById(R.id.item_delete);
        }
    }

    public interface OnDeleteListener{
        void onDeleteListener(View view, int position);
    }

}
