package com.nti.module_version.viewmodel;

import android.app.Application;
import android.app.ProgressDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nti.lib_common.bean.DataResult;
import com.nti.module_version.repository.VersionRepository;

import org.jetbrains.annotations.NotNull;

import okhttp3.ResponseBody;
import retrofit2.http.Url;

/**
 * @author: weiqiyuan
 * @date: 2022/9/16
 * @describe
 */
public class VersionViewModel extends AndroidViewModel {

    public MutableLiveData<DataResult<ResponseBody>> data;
    public MutableLiveData<Integer> data2;
    public VersionRepository repository = new VersionRepository();

    public VersionViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public MutableLiveData<DataResult<ResponseBody>> download(){
        data = repository.download();
        return data;
    }

    public MutableLiveData<Integer> downloadapk(){
        data2 = repository.downloadapk();
        return data2;
    }
}
