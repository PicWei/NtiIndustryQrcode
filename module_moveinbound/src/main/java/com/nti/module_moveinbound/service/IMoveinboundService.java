package com.nti.module_moveinbound.service;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.ErrorSignReceiveParamer;
import com.nti.lib_common.bean.Paramer;
import com.nti.lib_common.bean.UpParamer;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: weiqiyuan
 * @date: 2022/8/3
 * @describe
 */
public interface IMoveinboundService {
    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_DATA_LOADING")
    Observable<JsonObject> PDA_H(@Body Paramer paramer);

    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_CHANGE_STATE01")
    Observable<JsonObject> updataSellListStatues(@Body UpParamer paramer);

    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_UNIFIED_ERROR_BARCODE")
    Observable<JsonObject> errorSignReceive(@Body ErrorSignReceiveParamer paramer);
}
