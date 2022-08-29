package com.nti.lib_common.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;
import com.nti.lib_common.service.ISellBarcodeReciveService;
import com.nti.lib_common.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class SellBarcodeReciveRepository {
    public MutableLiveData<JsonObject> sellBarcodeRecive(SellBarcodeReciveParamer paramer){
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();

        HttpUtils.getInstance().with(ISellBarcodeReciveService.class).sellBarcodeRecive(paramer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        data.setValue(jsonObject);
                        Log.i("TAG", "jsonObject:" + jsonObject.toString());
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }
}
