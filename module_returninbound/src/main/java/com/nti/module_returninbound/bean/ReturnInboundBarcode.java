package com.nti.module_returninbound.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
 * @describe
 */
public class ReturnInboundBarcode extends LitePalSupport {

    private String UUID;
    private String pcigcode;
    private String pcigname;
    private String barcode;
    private String scantime;
    private String scancode;
    private boolean isSubmit;

    public ReturnInboundBarcode(String UUID, String pcigcode, String pcigname, String barcode, String scantime, String scancode) {
        this.UUID = UUID;
        this.pcigcode = pcigcode;
        this.pcigname = pcigname;
        this.barcode = barcode;
        this.scancode = scancode;
        this.scantime = scantime;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPcigcode() {
        return pcigcode;
    }

    public void setPcigcode(String pcigcode) {
        this.pcigcode = pcigcode;
    }

    public String getPcigname() {
        return pcigname;
    }

    public void setPcigname(String pcigname) {
        this.pcigname = pcigname;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getScantime() {
        return scantime;
    }

    public void setScantime(String scantime) {
        this.scantime = scantime;
    }

    public String getScancode() {
        return scancode;
    }

    public void setScancode(String scancode) {
        this.scancode = scancode;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean submit) {
        isSubmit = submit;
    }

    @Override
    public String toString() {
        return "ReturnInboundBarcode{" +
                "UUID='" + UUID + '\'' +
                ", pcigcode='" + pcigcode + '\'' +
                ", pcigname='" + pcigname + '\'' +
                ", barcode='" + barcode + '\'' +
                ", scantime='" + scantime + '\'' +
                ", scancode='" + scancode + '\'' +
                ", isSubmit=" + isSubmit +
                '}';
    }

}
