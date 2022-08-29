package com.nti.module_device.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.nti.lib_common.activity.BaseActivity;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.lib_common.utils.DeviceUtils;
import com.nti.module_device.R;
import com.nti.module_device.bean.PdaRegisterParam;
import com.nti.module_device.bean.PdaRegisterParamer;
import com.nti.module_device.databinding.ActivityDeviceBinding;
import com.nti.module_device.viewmodel.DeviceViewModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

@Route(path = ARouterPath.DEVICE_PATH)
public class DeviceActivity extends BaseActivity {

    @Autowired
    public boolean flag;

    private ActivityDeviceBinding binding;
    private DeviceViewModel viewModel;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_device);
        sp = getSharedPreferences("PDA_ACCOUNT", 0);
        editor = sp.edit();
        String devidename = sp.getString("pdaname", "");
        ARouter.getInstance().inject(this);
        viewModel = new ViewModelProvider(this).get(DeviceViewModel.class);
        if (flag){
            binding.deviceNameTv.setVisibility(View.VISIBLE);
            binding.deviceNameTv.setText(devidename);
            binding.deviceNameEt.setVisibility(View.GONE);
            binding.confirmBtn.setVisibility(View.GONE);
        }else {
            binding.deviceNameTv.setVisibility(View.GONE);
            binding.deviceNameEt.setVisibility(View.VISIBLE);
            binding.deviceNameEt.setText(devidename);
            binding.confirmBtn.setVisibility(View.VISIBLE);
        }
        String deviceModel = DeviceUtils.getModel();
        binding.deviceModelTv.setText(deviceModel);
        String ip = getLocalIpAddress(this);
        String mac = getNewMac();
        binding.ipAddressTv.setText(ip);
        binding.macAddressTv.setText(mac);
        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdaname = binding.deviceNameEt.getText().toString();
                String deviceId = DeviceUtils.getDevUUID(DeviceActivity.this);
                Log.i("TAG", "deviceId:" + deviceId);
                PdaRegisterParam pdaRegisterParam = new PdaRegisterParam(pdaname, deviceId, deviceModel);
                PdaRegisterParamer paramer = new PdaRegisterParamer(pdaRegisterParam);
                viewModel.register(paramer).observe(DeviceActivity.this, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if (jsonObject == null){
                            Toast.makeText(DeviceActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        }else {
                            String code = jsonObject.get("code").toString().replace("\"", "");
                            String message = jsonObject.get("message").toString().replace("\"", "");
                            if (code.equals("0")){
                                editor.putString("pdaname", pdaname);
                                editor.commit();
                                Toast.makeText(DeviceActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(DeviceActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

    }

    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
        // return null;
    }

    private static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}