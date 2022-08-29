package com.nti.module_salesexactory.service;

import com.google.gson.JsonObject;
import com.nti.module_salesexactory.bean.Paramer;
import com.nti.module_salesexactory.bean.UpParamer;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: weiqiyuan
 * @date: 2022/7/21
 * @describe
 */
public interface IncompleteService {
    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_DATA_LOADING")
    Observable<JsonObject> PDA_H(@Body Paramer paramer);

    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_CHANGE_STATE01")
    Observable<JsonObject> updataSellListStatues(@Body UpParamer paramer);

}
