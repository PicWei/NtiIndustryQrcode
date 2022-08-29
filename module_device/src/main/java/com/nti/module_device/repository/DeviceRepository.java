package com.nti.module_device.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.utils.HttpUtils;
import com.nti.module_device.bean.PdaRegisterParamer;
import com.nti.module_device.service.IDeviceServie;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: weiqiyuan
 * @date: 2022/8/1
 * @describe
 */
public class DeviceRepository {
    public MutableLiveData<JsonObject> register (PdaRegisterParamer paramer){
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        HttpUtils.getInstance().with(IDeviceServie.class).register(paramer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        data.setValue(jsonObject);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Log.i("TAG", "e:"+ e.getMessage());
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }
}
