package com.nti.lib_common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.ColorUtils;
import com.nti.lib_common.R;
import com.nti.lib_common.databinding.CommonTitleBarBinding;

/**
 * @author: weiqiyuan
 * @date: 2022/7/20
 * @describe
 */
public class CommonTitleBar extends ConstraintLayout {

    private Context context;
    private AttributeSet attrs;
    private Boolean isAutoClose = true;
    CommonTitleBarBinding binding;

    public CommonTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
//        LayoutInflater.from(context).inflate(R.layout.common_title_bar, this);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.common_title_bar, this, true);
        init();
    }

    /**
     * 返回按钮点击回调，默认已经实现关闭页面,
     * 只是关闭页面则无需调用监听回调
     */

    View.OnClickListener backClickListener = null;
    //   private CommonTitleBarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.common_title_bar, this, true);
    private void init(){
        TypedArray ta =  context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);
        binding.tvTitle.setText(ta.getString(R.styleable.CommonTitleBar_title));
        binding.tvTitle.setVisibility(VISIBLE);
        binding.tvSubTitle.setText(ta.getString(R.styleable.CommonTitleBar_sub_title));
        binding.tvSubTitle.setVisibility(VISIBLE);
        if (ta.getBoolean(R.styleable.CommonTitleBar_is_back_visible, true)){
            binding.ivBack.setVisibility(VISIBLE);
        }else {
            binding.ivBack.setVisibility(INVISIBLE);
        }
        if (ta.getBoolean(R.styleable.CommonTitleBar_is_refresh_visible, true)){
            binding.ivRightIcon.setVisibility(VISIBLE);
            binding.tvRightText.setText(ta.getString(R.styleable.CommonTitleBar_right_title));
        }else {
            binding.ivRightIcon.setVisibility(INVISIBLE);
        }

        if (ta.getBoolean(R.styleable.CommonTitleBar_is_right_visible, true)){
            binding.tvRightText.setVisibility(VISIBLE);
        }else {
            binding.tvRightText.setVisibility(INVISIBLE);
        }
        binding.getRoot().setBackgroundColor(ta.getColor(R.styleable.CommonTitleBar_background_color, getResources().getColor(R.color.app_theme_color)));
        isAutoClose = ta.getBoolean(R.styleable.CommonTitleBar_is_auto_close, true);
        setTitleCenter();
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAutoClose){
                    if (getContext() instanceof Activity){
                        ((Activity) getContext()).finish();
                    }
                }
            }
        });
    }

    private void setTitleCenter(){
        ConstraintLayout.LayoutParams titleLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        titleLayoutParams.leftToLeft = 0;
        titleLayoutParams.rightToRight = 0;
        titleLayoutParams.leftMargin = 0;
        binding.tvTitle.setLayoutParams(titleLayoutParams);
    }

    public void setTitleColor(int resId) {
        binding.tvTitle.setTextColor(ColorUtils.getColor(resId));
    }

    public void setWhiteIcon( int resId) {
        binding.ivBack.setImageResource(resId);

    }
}
