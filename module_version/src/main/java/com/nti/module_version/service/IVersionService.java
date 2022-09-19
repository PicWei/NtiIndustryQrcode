package com.nti.module_version.service;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author: weiqiyuan
 * @date: 2022/9/16
 * @describe
 */
public interface IVersionService {
    @GET("/ApkUpdate/apkVersion.xml")
    Observable<ResponseBody> download();

    @GET("/ApkUpdate/dsmapp.apk")
    Observable<ResponseBody> downloadapk();
}
