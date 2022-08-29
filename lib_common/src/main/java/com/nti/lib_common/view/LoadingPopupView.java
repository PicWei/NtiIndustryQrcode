package com.nti.lib_common.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/**
 * @author: weiqiyuan
 * @date: 2022/7/26
 * @describe
 */
public class LoadingPopupView extends CenterPopupView {

    public enum Style{
        Spinner, ProgressBar
    }

    private Style loadingStyle = Style.Spinner;
    private TextView tv_title;

    /**
     * @param context
     * @param bindLayoutId layoutId 如果要显示标题，则要求必须有id为tv_title的TextView，否则无任何要求
     */
    public LoadingPopupView(@NonNull Context context, int bindLayoutId) {
        super(context);
        this.bindLayoutId = bindLayoutId;
        addInnerContent();
    }

    @Override
    protected int getImplLayoutId() {
        return bindLayoutId != 0 ? bindLayoutId : com.lxj.xpopup.R.layout._xpopup_center_impl_loading;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tv_title = findViewById(com.lxj.xpopup.R.id.tv_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getPopupImplView().setElevation(10f);
        }
        if (bindLayoutId == 0) {
            getPopupImplView().setBackground(XPopupUtils.createDrawable(Color.parseColor("#CF000000"), popupInfo.borderRadius));
        }
        setup();
    }
    private boolean first = true;
    protected void setup() {
        if (tv_title == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                if(!first) {
                    TransitionSet set = new TransitionSet()
                            .setDuration(getAnimationDuration())
                            .addTransition(new Fade()).addTransition(new ChangeBounds());
                    TransitionManager.beginDelayedTransition(centerPopupContainer, set);
                }
                first = false;
                if (title == null || title.length() == 0) {
                    tv_title.setVisibility(GONE);
                } else {
                    tv_title.setVisibility(VISIBLE);
                    tv_title.setText(title);
                }

                View progressBar = findViewById(com.nti.lib_common.R.id.loadProgress);
                View spinnerView = findViewById(com.nti.lib_common.R.id.loadview);
                if(loadingStyle==Style.Spinner){
                    progressBar.setVisibility(GONE);
                    spinnerView.setVisibility(VISIBLE);
                }else {
                    progressBar.setVisibility(VISIBLE);
                    spinnerView.setVisibility(GONE);
                }
            }
        });
    }

    private CharSequence title;

    public LoadingPopupView setTitle(CharSequence title) {
        this.title = title;
        setup();
        return this;
    }

    public LoadingPopupView setStyle(Style style){
        this.loadingStyle = style;
        setup();
        return this;
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        if (tv_title == null) return;
        tv_title.setText("");
        tv_title.setVisibility(GONE);
    }
}
