package com.nti.module_salesexactory.activity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.nti.lib_common.activity.BaseActivity;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.MessageEvent;
import com.nti.lib_common.bean.SalesBarcodeParamer;
import com.nti.lib_common.bean.SalesOrderParamer;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;
import com.nti.lib_common.bean.SellParamer;
import com.nti.lib_common.bean.UploadSellParamer;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.lib_common.constants.BusinessType;
import com.nti.lib_common.utils.DateUtil;
import com.nti.lib_common.utils.DeviceUtils;
import com.nti.lib_common.viewmodel.SellBarcodeReciveViewModel;
import com.nti.module_salesexactory.R;
import com.nti.module_salesexactory.adapter.SalesFactoryDetailAdapter;
import com.nti.module_salesexactory.bean.ErrorBarcode;
import com.nti.module_salesexactory.bean.ErrorBarcodeParamer;
import com.nti.module_salesexactory.bean.ErrorSignReceiveParamer;
import com.nti.module_salesexactory.bean.SalesBarcode;
import com.nti.module_salesexactory.bean.SalesFactoryDetail;
import com.nti.module_salesexactory.bean.SalesFactoryOrderInfo;
import com.nti.module_salesexactory.bean.UpParamer;
import com.nti.module_salesexactory.bean.UpdataStatuesParamer;
import com.nti.module_salesexactory.databinding.ActivitySalesFactoryDetailBinding;
import com.nti.module_salesexactory.view.BarcodeListPopup;
import com.nti.module_salesexactory.viewmodel.IncompleteViewModel;
import com.nti.module_salesexactory.viewmodel.SalesFactoryDetailViewModel;
import com.xuexiang.xaop.annotation.IOThread;
import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xaop.consts.PermissionConsts;
import com.xuexiang.xaop.enums.ThreadType;
import com.xuexiang.xqrcode.XQRCode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

@Route(path = ARouterPath.SALESFACTORYDETAIL_PATH)
public class SalesFactoryDetailActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySalesFactoryDetailBinding binding;
    private SalesFactoryDetailAdapter adapter;

    private List<SalesFactoryDetail> detailList = new ArrayList<>();

    private List<SalesFactoryDetail> detailList2 = new ArrayList<>();


    public static final String SYSTEM_SERVICE_TYPE = "INDUT_SALES_FACTORY";


    /**
     * ??????
     */
    private SoundPool soundPool;
    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private AudioManager am;
    private float volumnRatio;

    private String BI_SCANNER_CODE;

    private SalesFactoryDetailViewModel salesFactoryDetailViewModel;
    //????????????
    private IncompleteViewModel incompleteViewModel;
    //??????
    private SellBarcodeReciveViewModel sellBarcodeReciveViewModel;


    private String A_NO;

    private String mScannerResult;

    private LoadingPopupView loadingPopup;


    /**
     * PDA???????????????
     */
    public static final String BROADCAST_ACTION = "com.scanner.broadcast";

    private ScannerBroascast scannerBroadcast;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (!TextUtils.isEmpty(mScannerResult))  handleScannerResult(mScannerResult);
                    break;
            }
        }
    };

    /**
     * ????????????Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;


    @Autowired
    public String contractNo;
    @Autowired
    public String flowName;
    @Autowired
    public String uuid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sales_factory_detail);
        EventBus.getDefault().register(this);
        initSound();
        ARouter.getInstance().inject(this);

        uuid = getIntent().getStringExtra("uuid");
        BI_SCANNER_CODE = DeviceUtils.getDevUUID(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.scanBtn.setOnClickListener(this);
        binding.confirmBrn.setOnClickListener(this);
        binding.orderTv.setText(contractNo);
        binding.inflowTv.setText(flowName);

        salesFactoryDetailViewModel = new ViewModelProvider(this).get(SalesFactoryDetailViewModel.class);
        incompleteViewModel = new ViewModelProvider(this).get(IncompleteViewModel.class);
        sellBarcodeReciveViewModel = new ViewModelProvider(this).get(SellBarcodeReciveViewModel.class);

        //??????????????????
        loadLatelyData();

        initRegister();
    }



    private void loadLatelyData() {
        SalesFactoryOrderInfo info = LitePal.where("BB_UUID = ?", uuid).findFirst(SalesFactoryOrderInfo.class);
        if (info!=null){
            A_NO = info.getA_NO();
            Log.i("ccc","A_NO" + A_NO);
        }
    }

    private void initRegister() {
        scannerBroadcast = new ScannerBroascast();
        IntentFilter intentFilter = new IntentFilter();
        // 2. ???????????????????????????
        intentFilter.addAction(BROADCAST_ACTION);// ?????????????????????action?????????????????????????????????
        // 3. ?????????????????????Context???registerReceiver????????????
        registerReceiver(scannerBroadcast, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (detailList2 != null) {
            detailList2.clear();
        }
        if (detailList != null) {
            detailList.clear();
        }



       SalesFactoryOrderInfo orderInfo = LitePal.where("BB_UUID = ?", uuid).findFirst(SalesFactoryOrderInfo.class);

       if (orderInfo !=  null){
           String pum = orderInfo.getBB_TOTAL_PNUM();
           String scanNum = orderInfo.getBB_TOTAL_SCAN_NUM();
           int unscanNum = Integer.parseInt(pum) - Integer.parseInt(scanNum);
           binding.scanTotalTv.setText(pum);
           binding.unscanTotalTv.setText(unscanNum + "");
           binding.scanedTotalTv.setText(scanNum);
       }


        detailList2 = LitePal.where("BD_BB_UUID = ?", uuid).find(SalesFactoryDetail.class);

        detailList.addAll(detailList2);

        adapter = new SalesFactoryDetailAdapter(detailList, this);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SalesFactoryDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SalesFactoryDetail detail) {
                String pcigcode = detail.getBD_PCIG_CODE();
                List<SalesBarcode> barcodes = LitePal.where("pcigcode = ? and isSubmit = 0", pcigcode).find(SalesBarcode.class);
                Collections.sort(barcodes, new Comparator<SalesBarcode>() {
                    @Override
                    public int compare(SalesBarcode salesBarcode, SalesBarcode t1) {
                        return t1.getScantime().compareTo(salesBarcode.getScantime());
                    }
                });
                BasePopupView popupView = new BarcodeListPopup(SalesFactoryDetailActivity.this, barcodes);
                new XPopup.Builder(SalesFactoryDetailActivity.this)
                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                        .asCustom(popupView)
                        .show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scan_btn) {
            startScan();
        }
        if (view.getId() == R.id.confirm_brn) {

            SalesFactoryOrderInfo orderInfo = LitePal.where("BB_UUID = ?", uuid).findFirst(SalesFactoryOrderInfo.class);

            String pum = "";
            String scanednum = "";

            if (orderInfo != null){
                pum  = orderInfo.getBB_TOTAL_PNUM();
                scanednum = orderInfo.getBB_TOTAL_SCAN_NUM();
            }


            int barcode_count  = LitePal.where("UUID = ? and isSubmit = 0", uuid).count(SalesBarcode.class);

            if (barcode_count == 0) {
                BasePopupView popupView = new XPopup.Builder(this)
                        .isDestroyOnDismiss(true)
                        .asConfirm(null, "???????????????!", "??????", "??????",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {

                                    }
                                }, null, false, com.nti.lib_common.R.layout.layout_confirm_popup);
                popupView.show();
            } else {
                if (!pum.equals(scanednum)) {
                    BasePopupView popupView = new XPopup.Builder(this)
                            .isDestroyOnDismiss(true)
                            .asConfirm(null, "????????????????????????????????????????????????",
                                    "??????", "??????",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            String BB_STATE = "3";
                                            UpdataStatuesParamer updataStatuesParamer = new UpdataStatuesParamer(uuid, BB_STATE, SYSTEM_SERVICE_TYPE);
                                            UpParamer upParamer = new UpParamer(updataStatuesParamer);
                                            incompleteViewModel.updataSellListStatues(upParamer).observe(SalesFactoryDetailActivity.this, new Observer<JsonObject>() {
                                                @Override
                                                public void onChanged(JsonObject jsonObject) {
                                                    if (jsonObject == null) {
                                                        Toast.makeText(SalesFactoryDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        String code = jsonObject.get("code").toString().replace("\"", "");
                                                        if (code.equals("0")) {
                                                            orderInfo.setBB_STATE("3");
                                                            orderInfo.saveOrUpdate("BB_UUID = ?", uuid);
                                                            Toast.makeText(SalesFactoryDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(SalesFactoryDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }, null, false, com.nti.lib_common.R.layout.layout_confirm_popup);
                    popupView.show();

                } else {
                    String BB_STATE = "3";

                    UpdataStatuesParamer updataStatuesParamer = new UpdataStatuesParamer(uuid, BB_STATE, SYSTEM_SERVICE_TYPE);
                    UpParamer upParamer = new UpParamer(updataStatuesParamer);
                    incompleteViewModel.updataSellListStatues(upParamer).observe(SalesFactoryDetailActivity.this, new Observer<JsonObject>() {
                        @Override
                        public void onChanged(JsonObject jsonObject) {
                            if (jsonObject == null) {
                                Toast.makeText(SalesFactoryDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                            } else {
                                String code = jsonObject.get("code").toString().replace("\"", "");
                                if (code.equals("0")) {

                                    orderInfo.setBB_STATE("3");
                                    orderInfo.saveOrUpdate("BB_UUID = ?", uuid);
                                    Toast.makeText(SalesFactoryDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SalesFactoryDetailActivity.this, "??????????????????", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * ?????????????????????
     */
    private void initSound() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(this, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(this, R.raw.ding, 1));
        am = (AudioManager) this.getSystemService(AUDIO_SERVICE);// ?????????AudioManager??????
    }

    /**
     * ?????????????????????
     */
    @Permission(PermissionConsts.CAMERA)
    @IOThread(ThreadType.Single)
    private void startScan() {
        CustomCaptureActivity.start(this, REQUEST_CODE, R.style.XQRCodeTheme_Custom);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            handleScanResult(data);
        }
    }


    /**
     * ???????????????????????????
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    handleScannerResult(result);
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    playSound(2);
                    Toast.makeText(this, "?????????????????????", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void handleScannerResult(String result) {

        if (result.length() != 32) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        if (!result.startsWith("91")) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        String type = result.substring(22, 23);
        if (!type.equals("1") && !type.equals("2") && !type.equals("3") && !type.equals("4")) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        String date = result.substring(16, 22);
        if (!DateUtil.isValidDate(date)) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        String unitcode = result.substring(8, 16);
        if (!unitcode.equals(A_NO)) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "????????????????????????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        int count = 0;
        int size = LitePal.where("barcode = ?", result).count(SalesBarcode.class);
        List<SalesOrderParamer> paramers = new ArrayList<>();
        if (size > 0) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String scantime = format.format(new Date());
        for (int i = 0; i < detailList2.size(); i++) {
            int mPlanqty = 0;
            String picgname = detailList2.get(i).getBD_PCIG_NAME();
            try {
                String planqty = detailList2.get(i).getBD_BILL_PNUM();
                mPlanqty = Integer.valueOf(planqty);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SalesFactoryDetail detail = detailList2.get(i);
            String pcigCode = detail.getBD_PCIG_CODE();
            String pcigcodesub = pcigCode.substring(7, 13);
            String pcigName = detail.getBD_PCIG_NAME();
            String code = result.substring(2, 8);
            if (pcigcodesub.equals(code)) {
                String scanQty = detail.getBD_SCAN_NUM();
                int mscanQty;
                if (TextUtils.isEmpty(scanQty)) {
                    mscanQty = 1;
                } else {
                    mscanQty = Integer.valueOf(scanQty) + 1;
                }
                if (mscanQty > mPlanqty) {
                    playSound(2);
                    sendErrorCode(result);
                    Toast.makeText(this, "????????????????????????,????????????", Toast.LENGTH_SHORT).show();
                    return;
                }
                String scancode = DeviceUtils.getDevUUID(this);
                SalesBarcode salesBarcode = new SalesBarcode(uuid, pcigCode, pcigName, result, scantime, scancode);
                salesBarcode.save();
                String new_scanQty = String.valueOf(mscanQty);
                ContentValues cv = new ContentValues();
                cv.put("BD_SCAN_NUM", new_scanQty);
                LitePal.updateAll(SalesFactoryDetail.class, cv, "BD_PCIG_CODE = ?", pcigCode);
                ContentValues cv2 = new ContentValues();
                List<SalesFactoryOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(SalesFactoryOrderInfo.class);
                String pum = orderInfos.get(0).getBB_TOTAL_PNUM();
                String BB_TOTAL_SCAN_NUM = orderInfos.get(0).getBB_TOTAL_SCAN_NUM();
                int scannum = Integer.parseInt(BB_TOTAL_SCAN_NUM) + 1;
                int unscannum = Integer.parseInt(pum) - scannum;
                BB_TOTAL_SCAN_NUM = String.valueOf(scannum);
                cv2.put("BB_TOTAL_SCAN_NUM", BB_TOTAL_SCAN_NUM);
                LitePal.updateAll(SalesFactoryOrderInfo.class, cv2, "BB_UUID = ?", uuid);
                binding.brandnameTv.setText(picgname);
                binding.barcodeTv.setText(result);
                binding.scanTotalTv.setText(pum);
                binding.unscanTotalTv.setText(unscannum + "");
                binding.scanedTotalTv.setText(scannum + "");

                List<SalesBarcodeParamer> salesBarcodeParamers = new ArrayList<>();
                String BI_FEEDBACK_TIME = format.format(new Date());
                SalesBarcodeParamer salesBarcodeParamer = new SalesBarcodeParamer(result, scantime, BI_FEEDBACK_TIME);
                salesBarcodeParamer.setBI_SCANNER_CODE(BI_SCANNER_CODE);
                salesBarcodeParamer.setBI_SERIAL_NO("");
                salesBarcodeParamer.setBI_LOCAL_SCAN_DATE(scantime);
                salesBarcodeParamer.setBI_PACK_ID("");
                salesBarcodeParamers.add(salesBarcodeParamer);
                int mscanqty = salesBarcodeParamers.size();
                SalesOrderParamer salesOrderParamer = new SalesOrderParamer(mPlanqty, pcigCode, mscanqty, salesBarcodeParamers);
                paramers.add(salesOrderParamer);
                SellParamer sellParamer = new SellParamer(uuid, paramers);
                List<SellParamer> sellParamers = new ArrayList<>();
                sellParamers.add(sellParamer);
                UploadSellParamer uploadSellParamer = new UploadSellParamer(sellParamers, SYSTEM_SERVICE_TYPE);
                SellBarcodeReciveParamer paramer = new SellBarcodeReciveParamer(uploadSellParamer);
                Log.i("TAG", "paramer:" + paramer.toString());

                if (loadingPopup == null) {
                    loadingPopup = (LoadingPopupView) new XPopup.Builder(this)
                            .dismissOnTouchOutside(false)
                            .dismissOnBackPressed(false)
                            .isLightNavigationBar(true)
                            .asLoading("?????????...")
                            .show();
                } else {
                    loadingPopup.show();
                }
                sellBarcodeReciveViewModel.sellBarcodeRecive(paramer).observe(this, new Observer<DataResult<JsonObject>>() {
                    @Override
                    public void onChanged(DataResult<JsonObject> dataResult) {


                        loadingPopup.dismiss();

                        int errcode = dataResult.getErrcode();
                        if (errcode == 0) {

                            SalesBarcode salesBarcode = new SalesBarcode(uuid, pcigCode, picgname, result, scantime, scancode);
                            salesBarcode.setSubmit(true);
                            salesBarcode.save();
                            Toast.makeText(SalesFactoryDetailActivity.this, "sucess", Toast.LENGTH_SHORT).show();

//                                String code = jsonObject.get("code").toString().replace("\"", "");
//                                String message = jsonObject.get("message").toString().replace("\"", "");
//                                if (code.equals("0")){
//
//                                }else {
//                                    Toast.makeText(SalesFactoryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
//                                }

                        } else if (errcode == -1) {
                            Toast.makeText(SalesFactoryDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            } else {
                count++;
            }
        }
        if (count == detailList2.size()) {
            playSound(2);
            sendErrorCode(result);
            Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
            return;
        }
        detailList2.clear();
        detailList2 = LitePal.where("BD_BB_UUID = ?", uuid).find(SalesFactoryDetail.class);
        detailList.clear();
        detailList.addAll(detailList2);
        String pum = detailList2.get(0).getBD_BILL_PNUM();
        String scanNum = detailList2.get(0).getBD_SCAN_NUM();
        int unscanNum = Integer.parseInt(pum) - Integer.parseInt(scanNum);
        binding.scanedTotalTv.setText(pum);
        binding.unscanTotalTv.setText(unscanNum + "");
        binding.scanedTotalTv.setText(scanNum);
        adapter.notifyDataSetChanged();
    }


    private void sendErrorCode(String barcode) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String BI_FEEDBACK_TIME = format.format(new Date());
        ErrorBarcode errorBarcode = new ErrorBarcode(barcode, BI_SCANNER_CODE, BI_FEEDBACK_TIME);
        List<ErrorBarcode> errorBarcodes = new ArrayList<>();
        errorBarcodes.add(errorBarcode);
        ErrorBarcodeParamer errorBarcodeParamer = new ErrorBarcodeParamer(uuid, SYSTEM_SERVICE_TYPE, errorBarcodes);
        ErrorSignReceiveParamer esrparamer = new ErrorSignReceiveParamer(errorBarcodeParamer);

        salesFactoryDetailViewModel.errorSignReceive(esrparamer).observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject == null) {
                    Toast.makeText(SalesFactoryDetailActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SalesFactoryDetailActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void playSound(int id) {
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // ????????????AudioManager????????????????????????
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// ????????????AudioManager??????????????????
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // ???????????????
                    volumnRatio, // ???????????????
                    1, // ????????????0?????????
                    0, // ???????????????0???????????????-1???????????????
                    1 // ???????????? ????????????0.5-2.0?????????1???????????????
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case BusinessType.BUSINESS_SALESFACTORY:
                if (detailList2 != null) {
                    detailList2.clear();
                }
                if (detailList != null) {
                    detailList.clear();
                }
                List<SalesFactoryOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(SalesFactoryOrderInfo.class);
                detailList2 = LitePal.where("BD_BB_UUID = ?", uuid).find(SalesFactoryDetail.class);
                String pum = orderInfos.get(0).getBB_TOTAL_PNUM();
                String scanNum = orderInfos.get(0).getBB_TOTAL_SCAN_NUM();
                int unscanNum = Integer.parseInt(pum) - Integer.parseInt(scanNum);
                binding.scanTotalTv.setText(pum);
                binding.unscanTotalTv.setText(unscanNum + "");
                binding.scanedTotalTv.setText(scanNum);
                detailList.addAll(detailList2);
                adapter.notifyDataSetChanged();
                break;
        }
    }


    public class ScannerBroascast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {

                Bundle bundle = intent.getExtras();

                if (bundle != null) {

                    String result = bundle.getString("data").trim();

                    if (!TextUtils.isEmpty(result)) {
                        mScannerResult = result;
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        detailList2.clear();
        detailList.clear();
        if (soundPool != null) {
            soundPool.release();
        }
        if (scannerBroadcast != null) {
            unregisterReceiver(scannerBroadcast);
        }
    }
}