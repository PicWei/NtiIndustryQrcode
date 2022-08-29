package com.nti.module_returninbound.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.nti.module_returninbound.R;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
 * @describe
 */
public class CustomCaptureActivity extends CaptureActivity implements View.OnClickListener {

    public static final String APP_LINK_ACTION = "com.xuexiang.xui.applink";
    public static final String APP_LINK_HOST = "xuexiangjys.club";

    /**
     * 开始二维码扫描
     *
     * @param fragment
     * @param requestCode 请求码
     * @param theme       主题
     */
    public static void start(Fragment fragment, int requestCode, int theme) {
        Intent intent = new Intent(fragment.getContext(), CustomCaptureActivity.class);
        intent.putExtra(KEY_CAPTURE_THEME, theme);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 开始二维码扫描
     *
     * @param activity
     * @param requestCode 请求码
     * @param theme       主题
     */
    public static void start(Activity activity, int requestCode, int theme) {
        Intent intent = new Intent(activity, CustomCaptureActivity.class);
        intent.putExtra(KEY_CAPTURE_THEME, theme);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 开始二维码扫描
     *
     * @param fragment
     * @param requestCode 请求码
     */
    public static void start(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), CustomCaptureActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 开始二维码扫描
     *
     * @param activity
     * @param requestCode 请求码
     */
    public static void start(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CustomCaptureActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    private AppCompatImageView mIvFlashLight;
    private AppCompatImageView mIvFlashLight1;
    private boolean mIsOpen;

    //===============================重写UI===================================//

    @Override
    protected int getCaptureLayoutId() {
        return R.layout.activity_custom_capture;
    }

    @Override
    protected void beforeCapture() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mIvFlashLight = findViewById(R.id.iv_flash_light);
        mIvFlashLight1 = findViewById(R.id.iv_flash_light1);
    }

    @Override
    protected void onCameraInitSuccess() {
        mIvFlashLight.setVisibility(View.VISIBLE);
        mIvFlashLight1.setVisibility(View.VISIBLE);

        mIsOpen = XQRCode.isFlashLightOpen();
        refreshFlashIcon();
        mIvFlashLight.setOnClickListener(this);
        mIvFlashLight1.setOnClickListener(this);
    }

    @Override
    protected void onCameraInitFailed() {
        mIvFlashLight.setVisibility(View.GONE);
        mIvFlashLight1.setVisibility(View.GONE);
    }

    private void refreshFlashIcon() {
        if (mIsOpen) {
            mIvFlashLight.setImageResource(R.drawable.ic_flash_light_on);
            mIvFlashLight1.setImageResource(R.drawable.ic_flash_light_open);
        } else {
            mIvFlashLight.setImageResource(R.drawable.ic_flash_light_off);
            mIvFlashLight1.setImageResource(R.drawable.ic_flash_light_close);
        }
    }

    private void switchFlashLight() {
        mIsOpen = !mIsOpen;
        try {
            XQRCode.switchFlashLight(mIsOpen);
            refreshFlashIcon();
        } catch (RuntimeException e) {
            e.printStackTrace();
            Toast.makeText(CustomCaptureActivity.this, "设备不支持闪光灯!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
            finish();
        }
        if (v.getId() == R.id.iv_flash_light){

        }
        if (v.getId() == R.id.iv_flash_light1){
            switchFlashLight();
        }
    }

    //===============================重写业务处理===================================//

    /**
     * 处理扫描成功（重写扫描成功，增加applink拦截）
     *
     * @param bitmap
     * @param result
     */
    @Override
    protected void handleAnalyzeSuccess(Bitmap bitmap, String result) {
        if (isAppLink(result)) {
            openAppLink(this, result);
        } else if (isWeb(result)) {
            //   Utils.goWeb(this, result);
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(XQRCode.RESULT_TYPE, XQRCode.RESULT_SUCCESS);
            bundle.putString(XQRCode.RESULT_DATA, result);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    /**
     * 格式：https://xuexiangjys.club/xpage/transfer?pageName=xxxxx&....
     * 例子：https://xuexiangjys.club/xpage/transfer?pageName=UserGuide&position=2
     *
     * @param url
     * @return
     */
    private boolean isAppLink(String url) {
        Uri uri = Uri.parse(url);
        return uri != null
                && APP_LINK_HOST.equals(uri.getHost())
                && (url.startsWith("http") || url.startsWith("https"))
                && url.contains("xpage");
    }

    private boolean isWeb(String url) {
        return !StringUtils.isEmpty(url)
                && (url.startsWith("http") || url.startsWith("https"));
    }

    private void openAppLink(Context context, String url) {
        try {
            Intent intent = new Intent(APP_LINK_ACTION);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(CustomCaptureActivity.this, "您所打开的第三方App未安装！", Toast.LENGTH_SHORT).show();
        }
    }

}
