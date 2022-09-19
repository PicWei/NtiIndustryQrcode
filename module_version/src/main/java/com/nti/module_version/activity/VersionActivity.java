package com.nti.module_version.activity;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.nti.lib_common.activity.BaseActivity;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.module_version.R;
import com.nti.module_version.bean.UpdataInfo;
import com.nti.module_version.databinding.ActivityVersionBinding;
import com.nti.module_version.viewmodel.VersionViewModel;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Route(path = ARouterPath.VERSION_PATH)
public class VersionActivity extends BaseActivity implements View.OnClickListener {

    private ActivityVersionBinding binding;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String localVersion;
    private VersionViewModel viewModel;
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_version);
        viewModel = new ViewModelProvider(this).get(VersionViewModel.class);
        setPermissions();
        binding.updateBtn.setOnClickListener(this);
    }

    private void setPermissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            for (String str : permissions) {
                if (VersionActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    VersionActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    doNext();
                } else {
                    //这里就是权限打开之后自己要操作的逻辑
                    doNext();
                }
            }
        }
    }

    private void doNext() {
        try {
            localVersion = String.valueOf(getVersionName());
            binding.curVersionTv.setText(localVersion);
            getData();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }

    private void getData(){
        viewModel.download().observe(this, new Observer<DataResult<ResponseBody>>() {
            @Override
            public void onChanged(DataResult<ResponseBody> dataResult) {
                int errcode = dataResult.getErrcode();
                if (errcode == 0){
                    ResponseBody responseBody = dataResult.getT();
                    InputStream is = responseBody.byteStream();
                    try {
                        UpdataInfo info = getUpdataInfo(is);
                        binding.lastVersionTv.setText(info.getVersion());
                        if (info.getVersion().equals(localVersion)){
                            binding.versionExplain.setText("当前版本已是最新版本");
                        }else{
                            binding.versionExplain.setText("发现新版本");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(VersionActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.update_btn){
            OnConfirmListener onConfirmListener = new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    pd = new ProgressDialog(VersionActivity.this);
                    pd.setCanceledOnTouchOutside(false);
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setMessage("正在下载更新，请稍等片刻...");
                    pd.show();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                getFileFromServer(VersionActivity.this,
                                        "http://10.1.5.179:8080/ApkUpdate/dsmapp.apk", pd);
                            } catch (Exception e) {

                            }
                        }
                    }.start();
                }
            };
            OnCancelListener onCancelListener = new OnCancelListener() {
                @Override
                public void onCancel() {

                }
            };
            XPopup.Builder builder = new XPopup.Builder(this);
            builder.isDestroyOnDismiss(true)//对于只使用一次的弹窗，推荐设置这个
                    .asConfirm("版本更新", "", "取消", "确认", onConfirmListener, onCancelListener, false, R.layout.layout_confirm_popup)
                    .show();
        }
    }

    public static File getFileFromServer(Context context, String path, ProgressDialog pd) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), "dsmapp.apk");
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            OkHttpClient okHttpClient = getOkHttpClient();
            Request request = new Request.Builder()
                    .url(path)
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    long size = response.body().contentLength();
                    pd.setMax(((int)size) / 1024);
                    InputStream is = response.body().byteStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    int total = 0;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        pd.setProgress(total / 1024);
                    }
                    pd.dismiss();
                    installApk(context, file);
                    fos.close();
                    bis.close();
                    is.close();
                }
            });

        } else {
            return null;
        }
        return file;
    }

    public static OkHttpClient getOkHttpClient(){
        OkHttpClient client = null;
        //初始化OkHttpClient对象时进行信任证书的操作
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.hostnameVerifier((hostname, session) -> true);
        if(mBuilder != null) {
            client = mBuilder.readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    /*
     * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
     */
    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");//设置解析的数据源
        int type = parser.getEventType();
        UpdataInfo info = new UpdataInfo();//实体
        while(type != XmlPullParser.END_DOCUMENT ){
            switch (type) {
                case XmlPullParser.START_TAG:
                    if("version".equals(parser.getName())){
                        info.setVersion(parser.nextText());	//获取版本号
                    }else if ("url".equals(parser.getName())){
                        info.setUrl(parser.nextText());	//获取要升级的APK文件
                    }else if ("description".equals(parser.getName())){
                        info.setDescription(parser.nextText());	//获取该文件的信息
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }


    // 安装apk
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri data = FileProvider.getUriForFile(context, "com.nti.module_version.fileProvider", file);
            intent.setDataAndType(data, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }
}