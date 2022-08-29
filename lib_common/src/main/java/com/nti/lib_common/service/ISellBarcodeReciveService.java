package com.nti.lib_common.service;

import com.google.gson.JsonObject;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public interface ISellBarcodeReciveService {
    //接收PDA回送的扫描条码信息
    @POST("/api/fdevelop/tool/front/runtime-server/common/transflow/executeCode/INDUT_RECEIVE_QR")
    Observable<JsonObject> sellBarcodeRecive(@Body SellBarcodeReciveParamer paramer);
}
