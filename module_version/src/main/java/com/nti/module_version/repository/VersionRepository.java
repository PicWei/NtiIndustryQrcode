package com.nti.module_version.repository;

import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.Utils;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.utils.HttpUtils;
import com.nti.module_version.activity.VersionActivity;
import com.nti.module_version.bean.UpdataInfo;
import com.nti.module_version.service.IVersionService;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Url;

import static com.nti.module_version.activity.VersionActivity.getUpdataInfo;

/**
 * @author: weiqiyuan
 * @date: 2022/9/16
 * @describe
 */
public class VersionRepository {
    public MutableLiveData<DataResult<ResponseBody>> download(){
        MutableLiveData<DataResult<ResponseBody>> data = new MutableLiveData<>();
        HttpUtils.getInstance().with(IVersionService.class).download()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                     @Override
                     public void onSubscribe(@NotNull Disposable d) {

                     }

                     @Override
                     public void onNext(@NotNull ResponseBody responseBody) {
                         DataResult<ResponseBody> dataResult = new DataResult<>(0, responseBody);
                         data.setValue(dataResult);
                     }

                     @Override
                     public void onError(@NotNull Throwable e) {
                         e.printStackTrace();
                         DataResult<ResponseBody> dataResult = new DataResult<>(-1, null);
                         data.setValue(dataResult);
                     }

                     @Override
                     public void onComplete() {

                     }
                 });
        return data;
    }

    public MutableLiveData<Integer> downloadapk(){
        MutableLiveData<Integer> data = new MutableLiveData<>();
        HttpUtils.getInstance().with(IVersionService.class).downloadapk()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull ResponseBody responseBody) {

                        try {
                            File file = new File(Environment.getExternalStorageDirectory(), "dsmapp.apk");
                            long size = responseBody.contentLength();
                            InputStream is = responseBody.byteStream();
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len = 0;
                            int total = 0;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                                total += len;
                                int progress = (int) (total*100/size);
                                data.setValue(progress);
                            }
                            fos.close();
                            bis.close();
                            is.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.i("TAG", "e:" + e.getMessage());
                        e.printStackTrace();
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return data;
    }
}
