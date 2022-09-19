package com.nti.module_salesexactory.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.utils.HttpUtils;
import com.nti.module_salesexactory.bean.ErrorSignReceiveParamer;
import com.nti.module_salesexactory.service.ISalesFactoryDetailService;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: weiqiyuan
 * @date: 2022/7/23
 * @describe
 */
public class SalesFactoryDetailRepository {

    public MutableLiveData<JsonObject> errorSignReceive(ErrorSignReceiveParamer paramer){
        final MutableLiveData<JsonObject> data = new MutableLiveData<>();
        HttpUtils.getInstance().with(ISalesFactoryDetailService.class, true).errorSignReceive(paramer)
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
                        data.setValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return data;
    }

}
