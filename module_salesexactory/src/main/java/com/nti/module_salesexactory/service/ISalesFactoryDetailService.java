package com.nti.module_salesexactory.service;

import com.google.gson.JsonObject;
import com.nti.module_salesexactory.bean.ErrorSignReceiveParamer;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: weiqiyuan
 * @date: 2022/7/23
 * @describe
 */
public interface ISalesFactoryDetailService {

    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_UNIFIED_ERROR_BARCODE")
    Observable<JsonObject> errorSignReceive(@Body ErrorSignReceiveParamer paramer);

}
