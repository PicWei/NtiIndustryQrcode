package com.nti.lib_common.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;
import com.nti.lib_common.service.ISellBarcodeReciveService;
import com.nti.lib_common.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

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
    public MutableLiveData<DataResult<JsonObject>> sellBarcodeRecive(SellBarcodeReciveParamer paramer){
        final MutableLiveData<DataResult<JsonObject>> data = new MutableLiveData<>();

        HttpUtils.getInstance().with(ISellBarcodeReciveService.class, true).sellBarcodeRecive(paramer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull JsonObject jsonObject) {
                        DataResult<JsonObject> dataResult = new DataResult<>(0, jsonObject);
                        data.setValue(dataResult);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        DataResult<JsonObject> dataResult = new DataResult<>(-1, null);
                        data.setValue(dataResult);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }
}
