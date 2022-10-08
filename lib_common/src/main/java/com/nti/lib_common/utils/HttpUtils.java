package com.nti.lib_common.utils;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class HttpUtils {
    private static volatile HttpUtils instance;


    private HttpUtils() {

    }

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }

        return instance;
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志
     *
     * @return
     */
    public Retrofit buildRetrofit(boolean iscer) {
        Retrofit retrofit = null;
        OkHttpClient.Builder mBuilder = null;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
//            if (iscer){
                mBuilder = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .addInterceptor(interceptor)
                        .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                        .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                        .hostnameVerifier((s, sslSession) -> true);
                retrofit = new Retrofit.Builder()
                        .client(mBuilder.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://it.nti56.com").build();
//            }
//            else {
//                mBuilder = new OkHttpClient.Builder()
//                        .connectTimeout(60, TimeUnit.SECONDS)
//                        .writeTimeout(60, TimeUnit.SECONDS)
//                        .readTimeout(60, TimeUnit.SECONDS);
//                retrofit = new Retrofit.Builder()
//                        .client(mBuilder.build())
//                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .baseUrl("http://10.1.5.179:8080").build();
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return retrofit;
    }


    public static <T> T with(Class<T> clz, boolean iscer) {

        return getInstance().buildRetrofit(iscer).create(clz);
    }

    //信任中心
    public static X509TrustManager trustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T mapGet(Object map, String name) {
        return (T) ((Map) map).get(name);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Map<T, R> mapOf(Object... args) {
        Map<T, R> map = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            map.put((T) args[i], (R) args[i + 1]);
        }
        return map;
    }

    public static <T> List<T> arrayOf(T... args) {
        return new ArrayList<>(Arrays.asList(args));
    }
}
