package com.nti.lib_common.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class SalesBarcodeParamer {
    //扫描器编号
    private String BI_SCANNER_CODE;
    //扫描的条码
    private String BI_BARCODE;
    //条码扫描标准时间
    private String BI_STANDARD_SCAN_DATE;
    //条码实时回送时间
    private String BI_FEEDBACK_TIME;
    //二维码序列号
    private String BI_SERIAL_NO;
    //条码扫描本地时间
    private String BI_LOCAL_SCAN_DATE;
    //条码包ID
    private String BI_PACK_ID;

    public SalesBarcodeParamer(String BI_BARCODE, String BI_STANDARD_SCAN_DATE, String BI_FEEDBACK_TIME) {
        this.BI_BARCODE = BI_BARCODE;
        this.BI_STANDARD_SCAN_DATE = BI_STANDARD_SCAN_DATE;
        this.BI_FEEDBACK_TIME = BI_FEEDBACK_TIME;
    }

    public String getBI_SCANNER_CODE() {
        return BI_SCANNER_CODE;
    }

    public void setBI_SCANNER_CODE(String BI_SCANNER_CODE) {
        this.BI_SCANNER_CODE = BI_SCANNER_CODE;
    }

    public String getBI_BARCODE() {
        return BI_BARCODE;
    }

    public void setBI_BARCODE(String BI_BARCODE) {
        this.BI_BARCODE = BI_BARCODE;
    }

    public String getBI_STANDARD_SCAN_DATE() {
        return BI_STANDARD_SCAN_DATE;
    }

    public void setBI_STANDARD_SCAN_DATE(String BI_STANDARD_SCAN_DATE) {
        this.BI_STANDARD_SCAN_DATE = BI_STANDARD_SCAN_DATE;
    }

    public String getBI_FEEDBACK_TIME() {
        return BI_FEEDBACK_TIME;
    }

    public void setBI_FEEDBACK_TIME(String BI_FEEDBACK_TIME) {
        this.BI_FEEDBACK_TIME = BI_FEEDBACK_TIME;
    }

    public String getBI_SERIAL_NO() {
        return BI_SERIAL_NO;
    }

    public void setBI_SERIAL_NO(String BI_SERIAL_NO) {
        this.BI_SERIAL_NO = BI_SERIAL_NO;
    }

    public String getBI_LOCAL_SCAN_DATE() {
        return BI_LOCAL_SCAN_DATE;
    }

    public void setBI_LOCAL_SCAN_DATE(String BI_LOCAL_SCAN_DATE) {
        this.BI_LOCAL_SCAN_DATE = BI_LOCAL_SCAN_DATE;
    }

    public String getBI_PACK_ID() {
        return BI_PACK_ID;
    }

    public void setBI_PACK_ID(String BI_PACK_ID) {
        this.BI_PACK_ID = BI_PACK_ID;
    }

    @Override
    public String toString() {
        return "SalesBarcodeParamer{" +
                "BI_SCANNER_CODE='" + BI_SCANNER_CODE + '\'' +
                ", BI_BARCODE='" + BI_BARCODE + '\'' +
                ", BI_STANDARD_SCAN_DATE='" + BI_STANDARD_SCAN_DATE + '\'' +
                ", BI_FEEDBACK_TIME='" + BI_FEEDBACK_TIME + '\'' +
                ", BI_SERIAL_NO='" + BI_SERIAL_NO + '\'' +
                ", BI_LOCAL_SCAN_DATE='" + BI_LOCAL_SCAN_DATE + '\'' +
                ", BI_PACK_ID='" + BI_PACK_ID + '\'' +
                '}';
    }
}
