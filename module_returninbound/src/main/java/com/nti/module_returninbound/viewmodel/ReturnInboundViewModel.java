package com.nti.module_returninbound.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.module_returninbound.bean.ErrorSignReceiveParamer;
import com.nti.module_returninbound.bean.Paramer;
import com.nti.module_returninbound.bean.ReturnInboundOrderInfo;
import com.nti.module_returninbound.bean.UpParamer;
import com.nti.module_returninbound.repository.ReturnInboundRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
 * @describe
 */
public class ReturnInboundViewModel extends AndroidViewModel {

    private ReturnInboundRepository repository = new ReturnInboundRepository();
    private MutableLiveData<JsonObject> data;
    private MutableLiveData<JsonObject> data3;
    private MutableLiveData<List<ReturnInboundOrderInfo>> data2 = new MutableLiveData<>();

    public ReturnInboundViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<List<ReturnInboundOrderInfo>> PDA_H(Paramer paramer){
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
