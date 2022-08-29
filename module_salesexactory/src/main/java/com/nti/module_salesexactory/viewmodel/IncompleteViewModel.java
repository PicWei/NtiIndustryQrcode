package com.nti.module_salesexactory.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.module_salesexactory.bean.Paramer;
import com.nti.module_salesexactory.bean.SalesFactoryOrderInfo;
import com.nti.module_salesexactory.bean.UpParamer;
import com.nti.module_salesexactory.repository.IncompleteRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/21
 * @describe
 */
public class IncompleteViewModel extends AndroidViewModel {

    private MutableLiveData<JsonObject> data;
    private MutableLiveData<List<SalesFactoryOrderInfo>> data2 = new MutableLiveData<>();
    private IncompleteRepository repository = new IncompleteRepository();

    public IncompleteViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<List<SalesFactoryOrderInfo>> PDA_H(Paramer paramer){
        data2 = repository.PDA_H(paramer);
        return data2;
    }

    public MutableLiveData<JsonObject> updataSellListStatues(UpParamer paramer){
        data = repository.updataSellListStatues(paramer);
        return data;
    }
}
