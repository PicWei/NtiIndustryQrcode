package com.nti.lib_common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;
import com.nti.lib_common.repository.SellBarcodeReciveRepository;

import org.jetbrains.annotations.NotNull;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class SellBarcodeReciveViewModel extends AndroidViewModel {

    private SellBarcodeReciveRepository repository = new SellBarcodeReciveRepository();
    private MutableLiveData<DataResult<JsonObject>> data;

    public SellBarcodeReciveViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<DataResult<JsonObject>> sellBarcodeRecive(SellBarcodeReciveParamer paramer){
        data = repository.sellBarcodeRecive(paramer);
        return data;
    }
}
