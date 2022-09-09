package com.nti.module_device.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.DataResult;
import com.nti.module_device.bean.PdaRegisterParamer;
import com.nti.module_device.repository.DeviceRepository;

import org.jetbrains.annotations.NotNull;

/**
 * @author: weiqiyuan
 * @date: 2022/8/1
 * @describe
 */
public class DeviceViewModel extends AndroidViewModel {

    private MutableLiveData<DataResult<JsonObject>> data;
    private DeviceRepository repository = new DeviceRepository();

    public DeviceViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public LiveData<DataResult<JsonObject>> register (PdaRegisterParamer paramer){
        data = repository.register(paramer);
        return data;
    }
}
