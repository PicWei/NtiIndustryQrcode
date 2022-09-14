package com.nti.module_produceoutbound.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.ErrorSignReceiveParamer;
import com.nti.lib_common.bean.Paramer;
import com.nti.lib_common.bean.UpParamer;
import com.nti.module_produceoutbound.bean.ProduceoutboundOrderInfo;
import com.nti.module_produceoutbound.repository.ProduceoutboundRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/8/4
 * @describe
 */
public class ProduceoutboundViewModel extends AndroidViewModel {

    private ProduceoutboundRepository repository = new ProduceoutboundRepository();
    private MutableLiveData<JsonObject> data;
    private MutableLiveData<JsonObject> data3;
    private MutableLiveData<DataResult<List<ProduceoutboundOrderInfo>>> data2 = new MutableLiveData<>();

    public ProduceoutboundViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<DataResult<List<ProduceoutboundOrderInfo>>> PDA_H(Paramer paramer){
        data2 = repository.PDA_H(paramer);
        return data2;
    }

    public MutableLiveData<JsonObject> updataSellListStatues(UpParamer paramer){
        data = repository.updataSellListStatues(paramer);
        return data;
    }

    public LiveData<JsonObject> errorSignReceive(ErrorSignReceiveParamer paramer){
        data3 = repository.errorSignReceive(paramer);
        return data3;
    }

}
