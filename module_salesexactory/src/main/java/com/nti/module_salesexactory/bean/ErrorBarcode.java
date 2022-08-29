package com.nti.module_salesexactory.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/7/23
 * @describe
 */
public class ErrorBarcode {
    private String BI_BARCODE;
    private String BI_SCANNER_CODE;
    private String BI_SCAN_DATE;

    public ErrorBarcode(String BI_BARCODE, String BI_SCANNER_CODE, String BI_SCAN_DATE) {
        this.BI_BARCODE = BI_BARCODE;
        this.BI_SCANNER_CODE = BI_SCANNER_CODE;
        this.BI_SCAN_DATE = BI_SCAN_DATE;
    }

    public String getBI_BARCODE() {
        return BI_BARCODE;
    }

    public void setBI_BARCODE(String BI_BARCODE) {
        this.BI_BARCODE = BI_BARCODE;
    }

    public String getBI_SCANNER_CODE() {
        return BI_SCANNER_CODE;
    }

    public void setBI_SCANNER_CODE(String BI_SCANNER_CODE) {
        this.BI_SCANNER_CODE = BI_SCANNER_CODE;
    }

    public String getBI_SCAN_DATE() {
        return BI_SCAN_DATE;
    }

    public void setBI_SCAN_DATE(String BI_SCAN_DATE) {
        this.BI_SCAN_DATE = BI_SCAN_DATE;
    }

    @Override
    public String toString() {
        return "ErrorBarcode{" +
                "BI_BARCODE='" + BI_BARCODE + '\'' +
                ", BI_SCANNER_CODE='" + BI_SCANNER_CODE + '\'' +
                ", BI_SCAN_DATE='" + BI_SCAN_DATE + '\'' +
                '}';
    }
}
