package com.nti.module_scrap.activity;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.nti.lib_common.activity.BaseActivity;
import com.nti.lib_common.activity.CustomCaptureActivity;
import com.nti.lib_common.bean.DataResult;
import com.nti.lib_common.bean.ErrorBarcode;
import com.nti.lib_common.bean.ErrorBarcodeParamer;
import com.nti.lib_common.bean.ErrorSignReceiveParamer;
import com.nti.lib_common.bean.MessageEvent;
import com.nti.lib_common.bean.SalesBarcodeParamer;
import com.nti.lib_common.bean.SalesOrderParamer;
import com.nti.lib_common.bean.SellBarcodeReciveParamer;
import com.nti.lib_common.bean.SellParamer;
import com.nti.lib_common.bean.UpParamer;
import com.nti.lib_common.bean.UpdataStatuesParamer;
import com.nti.lib_common.bean.UploadSellParamer;
import com.nti.lib_common.constants.ARouterPath;
import com.nti.lib_common.constants.BusinessType;
import com.nti.lib_common.utils.DateUtil;
import com.nti.lib_common.utils.DeviceUtils;
import com.nti.lib_common.view.BarcodeListPopup;
import com.nti.lib_common.viewmodel.SellBarcodeReciveViewModel;
import com.nti.module_scrap.R;
import com.nti.module_scrap.adapter.ScrapDetailAdapter;
import com.nti.module_scrap.bean.ScrapBarcode;
import com.nti.module_scrap.bean.ScrapDetail;
import com.nti.module_scrap.bean.ScrapOrderInfo;
import com.nti.module_scrap.databinding.ActivityScrapDetailBinding;
import com.nti.module_scrap.viewmodel.ScrapViewModel;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Route(path = ARouterPath.SCRAPDETAIL_PATH)
public class ScrapDetailActivity extends BaseActivity implements View.OnClickListener{

    private ActivityScrapDetailBinding binding;
    private ScrapDetailAdapter adapter;
    private List<ScrapDetail> detailList = new ArrayList<>();
    private List<ScrapDetail> detailList2 = new ArrayList<>();
    private SoundPool soundPool;
    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private AudioManager am;
    private float volumnRatio;
    private String BI_SCANNER_CODE;
    private ScrapViewModel viewModel;
    private SellBarcodeReciveViewModel viewModel3;
    private String A_NO;

    /**
     * 扫描跳转Activity RequestCode
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrap_detail);
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
        viewModel = new ViewModelProvider(this).get(ScrapViewModel.class);
        viewModel3 = new ViewModelProvider(this).get(SellBarcodeReciveViewModel.class);
        List<ScrapOrderInfo> infos = LitePal.where("BB_UUID = ?", uuid).find(ScrapOrderInfo.class);
        A_NO = infos.get(0).getA_NO();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (detailList2 != null){
            detailList2.clear();
        }
        if (detailList != null){
            detailList.clear();
        }
        List<ScrapOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(ScrapOrderInfo.class);
        detailList2 = LitePal.where("BD_BB_UUID = ?", uuid).find(ScrapDetail.class);
        String pum = orderInfos.get(0).getBB_TOTAL_PNUM();
        String scanNum = orderInfos.get(0).getBB_TOTAL_SCAN_NUM();
        int unscanNum = Integer.parseInt(pum) - Integer.parseInt(scanNum);
        binding.scanTotalTv.setText(pum);
        binding.unscanTotalTv.setText(unscanNum+"");
        binding.scanedTotalTv.setText(scanNum);
        detailList.addAll(detailList2);
        adapter = new ScrapDetailAdapter(detailList, this);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ScrapDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ScrapDetail detail) {
                String pcigcode = detail.getBD_PCIG_CODE();
                List<ScrapBarcode> barcodes = LitePal.where("pcigcode = ? and isSubmit = 0", pcigcode).find(ScrapBarcode.class);
                Collections.sort(barcodes, new Comparator<ScrapBarcode>() {
                    @Override
                    public int compare(ScrapBarcode salesBarcode, ScrapBarcode t1) {
                        return t1.getScantime().compareTo(salesBarcode.getScantime());
                    }
                });
                BasePopupView popupView = new BarcodeListPopup(ScrapDetailActivity.this);
                new XPopup.Builder(ScrapDetailActivity.this)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(popupView)
                        .show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scan_btn){
            startScan();
        }
        if (view.getId() == R.id.confirm_brn){
            List<ScrapOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(ScrapOrderInfo.class);
            String pum = orderInfos.get(0).getBB_TOTAL_PNUM();
            String scanednum = orderInfos.get(0).getBB_TOTAL_SCAN_NUM();
            List<ScrapBarcode> barcodes = LitePal.where("UUID = ? and isSubmit = 0", uuid).find(ScrapBarcode.class);
            if (barcodes.size() == 0){
                BasePopupView popupView = new XPopup.Builder(this)
                        .isDestroyOnDismiss(true)
                        .asConfirm(null, "未扫描条码!", "取消", "确定",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {

                                    }
                                }, null, false, com.nti.lib_common.R.layout.layout_confirm_popup);
                popupView.show();
            }else {
                if (!pum.equals(scanednum)){
                    BasePopupView popupView = new XPopup.Builder(this)
                            .isDestroyOnDismiss(true)
                            .asConfirm(null, "当前单据还未扫描完全，确认完成？",
                                    "取消", "确定",
                                    new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            String BB_STATE = "3";
                                            String SYSTEM_SERVICE_TYPE= "INDUT_OUT_SCRAP";
                                            UpdataStatuesParamer updataStatuesParamer = new UpdataStatuesParamer(uuid, BB_STATE, SYSTEM_SERVICE_TYPE);
                                            UpParamer upParamer = new UpParamer(updataStatuesParamer);
                                            viewModel.updataSellListStatues(upParamer).observe(ScrapDetailActivity.this, new Observer<JsonObject>() {
                                                @Override
                                                public void onChanged(JsonObject jsonObject) {
                                                    if (jsonObject == null){
                                                        Toast.makeText(ScrapDetailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                                    }else {
                                                        String code = jsonObject.get("code").toString().replace("\"", "");
                                                        if (code.equals("0")){
                                                            ScrapOrderInfo orderInfo = orderInfos.get(0);
                                                            orderInfo.setBB_STATE("3");
                                                            orderInfo.saveOrUpdate("BB_UUID = ?", uuid);
                                                            Toast.makeText(ScrapDetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
                                                        }else {
                                                            Toast.makeText(ScrapDetailActivity.this, "更改状态失败", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }, null, false, com.nti.lib_common.R.layout.layout_confirm_popup);
                    popupView.show();

                }else {
                    String BB_STATE = "3";
                    String SYSTEM_SERVICE_TYPE= "INDUT_OUT_SCRAP";
                    UpdataStatuesParamer updataStatuesParamer = new UpdataStatuesParamer(uuid, BB_STATE, SYSTEM_SERVICE_TYPE);
                    UpParamer upParamer = new UpParamer(updataStatuesParamer);
                    viewModel.updataSellListStatues(upParamer).observe(ScrapDetailActivity.this, new Observer<JsonObject>() {
                        @Override
                        public void onChanged(JsonObject jsonObject) {
                            if (jsonObject == null){
                                Toast.makeText(ScrapDetailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                            }else {
                                String code = jsonObject.get("code").toString().replace("\"", "");
                                if (code.equals("0")){
                                    ScrapOrderInfo orderInfo = orderInfos.get(0);
                                    orderInfo.setBB_STATE("3");
                                    orderInfo.saveOrUpdate("BB_UUID = ?", uuid);
                                    Toast.makeText(ScrapDetailActivity.this, "确认成功", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ScrapDetailActivity.this, "更改状态失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * 初始化扫码铃声
     */
    private void initSound(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(this, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(this, R.raw.ding, 1));
        am = (AudioManager) this.getSystemService(AUDIO_SERVICE);// 实例化AudioManager对象
    }

    /**
     * 开启二维码扫描
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
     * 处理二维码扫描结果
     *
     * @param data
     */
    private void handleScanResult(Intent data) {
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_SUCCESS) {
                    String result = bundle.getString(XQRCode.RESULT_DATA);
                    if (result.length() != 32){
                        playSound(2);
                        sendErrorCode(result);
                        Toast.makeText(this, "条码格式错误", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        if (!result.startsWith("91")){
                            playSound(2);
                            sendErrorCode(result);
                            Toast.makeText(this, "条码格式错误", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String type = result.substring(22, 23);
                        if (!type.equals("1") && !type.equals("2") && !type.equals("3") && !type.equals("4")){
                            playSound(2);
                            sendErrorCode(result);
                            Toast.makeText(this, "条码经营方式未知", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String date = result.substring(16, 22);
                        if (!DateUtil.isValidDate(date)){
                            playSound(2);
                            sendErrorCode(result);
                            Toast.makeText(this, "条码日期无效", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String unitcode = result.substring(8, 16);
                        if (!unitcode.equals(A_NO)){
                            playSound(2);
                            sendErrorCode(result);
                            Toast.makeText(this, "生产厂家与当前单位不一致", Toast.LENGTH_LONG).show();
                            return;
                        }
                        int count = 0;
                        List<ScrapBarcode> barcodes = LitePal.where("barcode = ?", result).find(ScrapBarcode.class);
                        List<SalesOrderParamer> paramers = new ArrayList<>();
                        if (barcodes.size() > 0){
                            playSound(2);
                            sendErrorCode(result);
                            Toast.makeText(this, "此条码已扫过", Toast.LENGTH_LONG).show();
                            return;
                        }
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String scantime = format.format(new Date());
                        for (int i = 0; i < detailList2.size(); i++){
                            int mPlanqty = 0;
                            String picgname = detailList2.get(i).getBD_PCIG_NAME();
                            try {
                                String planqty = detailList2.get(i).getBD_BILL_PNUM();
                                mPlanqty = Integer.valueOf(planqty);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            ScrapDetail detail = detailList2.get(i);
                            String pcigCode = detail.getBD_PCIG_CODE();
                            String pcigcodesub = pcigCode.substring(7, 13);
                            String pcigName = detail.getBD_PCIG_NAME();
                            String code = result.substring(2, 8);
                            if (pcigcodesub.equals(code)){
                                String scanQty = detail.getBD_SCAN_NUM();
                                int mscanQty;
                                if (TextUtils.isEmpty(scanQty)){
                                    mscanQty = 1;
                                }else {
                                    mscanQty = Integer.valueOf(scanQty) + 1;
                                }
                                if (mscanQty > mPlanqty){
                                    playSound(2);
                                    sendErrorCode(result);
                                    Toast.makeText(this, "扫描量大于计划量,暂停扫描", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String scancode = DeviceUtils.getDevUUID(this);
                                ScrapBarcode salesBarcode = new ScrapBarcode(uuid, pcigCode, pcigName, result, scantime, scancode);
                                salesBarcode.save();
                                String new_scanQty = String.valueOf(mscanQty);
                                ContentValues cv = new ContentValues();
                                cv.put("BD_SCAN_NUM", new_scanQty);
                                LitePal.updateAll(ScrapDetail.class, cv, "BD_PCIG_CODE = ?", pcigCode);
                                ContentValues cv2 = new ContentValues();
                                List<ScrapOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(ScrapOrderInfo.class);
                                String pum = orderInfos.get(0).getBB_TOTAL_PNUM();
                                String BB_TOTAL_SCAN_NUM = orderInfos.get(0).getBB_TOTAL_SCAN_NUM();
                                int scannum = Integer.parseInt(BB_TOTAL_SCAN_NUM) + 1;
                                int unscannum = Integer.parseInt(pum) - scannum;
                                BB_TOTAL_SCAN_NUM = String.valueOf(scannum);
                                cv2.put("BB_TOTAL_SCAN_NUM", BB_TOTAL_SCAN_NUM);
                                LitePal.updateAll(ScrapOrderInfo.class, cv2, "BB_UUID = ?", uuid);
                                binding.brandnameTv.setText(picgname);
                                binding.barcodeTv.setText(result);
                                binding.scanTotalTv.setText(pum);
                                binding.unscanTotalTv.setText(unscannum+"");
                                binding.scanedTotalTv.setText(scannum+"");

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
                                String SYSTEM_SERV = "INDUT_OUT_SCRAP";
                                UploadSellParamer uploadSellParamer = new UploadSellParamer(sellParamers, SYSTEM_SERV);
                                SellBarcodeReciveParamer paramer = new SellBarcodeReciveParamer(uploadSellParamer);
                                Log.i("TAG", "paramer:" + paramer.toString());
                                viewModel3.sellBarcodeRecive(paramer).observe(this, new Observer<DataResult<JsonObject>>() {
                                    @Override
                                    public void onChanged(DataResult<JsonObject> dataResult) {
                                        Log.i("TAG", "dataResult:" + dataResult.toString());
                                        int errcode = dataResult.getErrcode();
                                        if (errcode == 0){
                                            JsonObject jsonObject = dataResult.getT();
                                            String code = jsonObject.get("code").toString().replace("\"", "");
                                            String message = jsonObject.get("message").toString().replace("\"", "");
                                            if (code.equals("0")){
                                                ScrapBarcode scrapBarcode = new ScrapBarcode(uuid, pcigCode, picgname, result, scantime,scancode);
                                                scrapBarcode.setSubmit(true);
                                                scrapBarcode.saveOrUpdate("barcode = ?", result);
                                                Toast.makeText(ScrapDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(ScrapDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                                            }
                                        }else if (errcode == -1){
                                            Toast.makeText(ScrapDetailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                break;
                            }else {
                                count++;
                            }
                        }
                        if (count == detailList2.size()){
                            playSound(2);
                            sendErrorCode(result);
                            Toast.makeText(this, "条码不符", Toast.LENGTH_LONG).show();
                            return;
                        }
                        detailList2.clear();
                        detailList2 = LitePal.where("BD_BB_UUID = ?", uuid).find(ScrapDetail.class);
                        detailList.clear();
                        detailList.addAll(detailList2);
                        String pum = detailList2.get(0).getBD_BILL_PNUM();
                        String scanNum = detailList2.get(0).getBD_SCAN_NUM();
                        int unscanNum = Integer.parseInt(pum) - Integer.parseInt(scanNum);
                        binding.scanedTotalTv.setText(pum);
                        binding.unscanTotalTv.setText(unscanNum+"");
                        binding.scanedTotalTv.setText(scanNum);

                        adapter.notifyDataSetChanged();
                    }
                } else if (bundle.getInt(XQRCode.RESULT_TYPE) == XQRCode.RESULT_FAILED) {
                    playSound(2);
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void sendErrorCode(String barcode){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String BI_FEEDBACK_TIME = format.format(new Date());
        ErrorBarcode errorBarcode = new ErrorBarcode(barcode, BI_SCANNER_CODE, BI_FEEDBACK_TIME);
        List<ErrorBarcode> errorBarcodes = new ArrayList<>();
        errorBarcodes.add(errorBarcode);
        String SYSTEM_SERVICE_TYPE = "INDUT_OUT_SCRAP";
        ErrorBarcodeParamer errorBarcodeParamer = new ErrorBarcodeParamer(uuid, SYSTEM_SERVICE_TYPE, errorBarcodes);
        ErrorSignReceiveParamer esrparamer = new ErrorSignReceiveParamer(errorBarcodeParamer);

        viewModel.errorSignReceive(esrparamer).observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject == null){
                    Toast.makeText(ScrapDetailActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ScrapDetailActivity.this, "异常条码上传成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void playSound(int id) {
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        switch (event.what){
            case BusinessType.BUSINESS_SALESFACTORY:
                if (detailList2 != null){
                    detailList2.clear();
                }
                if (detailList != null){
                    detailList.clear();
                }
                List<ScrapOrderInfo> orderInfos = LitePal.where("BB_UUID = ?", uuid).find(ScrapOrderInfo.class);
                detailList2 = LitePal.where("BD_BB_UUID = ?", uuid).find(ScrapDetail.class);
                String pum = orderInfos.get(0).getBB_TOTAL_PNUM();
                String scanNum = orderInfos.get(0).getBB_TOTAL_SCAN_NUM();
                int unscanNum = Integer.parseInt(pum) - Integer.parseInt(scanNum);
                binding.scanTotalTv.setText(pum);
                binding.unscanTotalTv.setText(unscanNum+"");
                binding.scanedTotalTv.setText(scanNum);
                detailList.addAll(detailList2);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        detailList2.clear();
        detailList.clear();
        if (soundPool != null){
            soundPool.release();
        }
    }

}