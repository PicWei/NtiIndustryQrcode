package com.nti.module_salesexactory.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.module_salesexactory.bean.ErrorSignReceiveParamer;
import com.nti.module_salesexactory.repository.SalesFactoryDetailRepository;

import org.jetbrains.annotations.NotNull;

/**
 * @author: weiqiyuan
 * @date: 2022/7/23
 * @describe
 */
public class SalesFactoryDetailViewModel extends AndroidViewModel {

    private SalesFactoryDetailRepository repository = new SalesFactoryDetailRepository();
    private MutableLiveData<JsonObject> data;

    public SalesFactoryDetailViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public LiveData<JsonObject> errorSignReceive(ErrorSignReceiveParamer paramer){
        data = repository.errorSignReceive(paramer);
        return data;
    }
}
