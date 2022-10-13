package com.nti.lib_common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * created by caiyanghua on 2022/10/13
 */
public class SPUtils{

    public static final String FILE_NAME = "share_data";


    public static final String SERVER_URL = "SERVER_URL";


    public static final String DEVICES_NAME = "DEVICES_NAME";




    /**
     *  服务器地址
     * @param context
     * @return
     */

    public static void setServerUrl(Context context,String server_url) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SERVER_URL, server_url);
        editor.commit();
    }


    public static String  getServerUrl(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(SERVER_URL, "");
    }


    /**
     * 设备名称
     * @param context
     * @return
     */
    public static void setDevicesName(Context context,String devices_name) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(DEVICES_NAME, devices_name);
        editor.commit();
    }


    public static String  getDevicesName(Context context){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(DEVICES_NAME, "");
    }


}
