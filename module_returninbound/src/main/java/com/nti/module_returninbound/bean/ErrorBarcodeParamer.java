package com.nti.module_returninbound.bean;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
 * @describe
 */
public class ErrorBarcodeParamer {
    private String BB_UUID;
    private String SYSTEM_SERVICE_TYPE;
    private List<ErrorBarcode> LIST;

    public ErrorBarcodeParamer(String BB_UUID, String SYSTEM_SERVICE_TYPE, List<ErrorBarcode> LIST) {
        this.BB_UUID = BB_UUID;
        this.LIST = LIST;
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    public String getBB_UUID() {
        return BB_UUID;
    }

    public void setBB_UUID(String BB_UUID) {
        this.BB_UUID = BB_UUID;
    }

    public String getSYSTEM_SERVICE_TYPE() {
        return SYSTEM_SERVICE_TYPE;
    }

    public void setSYSTEM_SERVICE_TYPE(String SYSTEM_SERVICE_TYPE) {
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    public List<ErrorBarcode> getLIST() {
        return LIST;
    }

    public void setLIST(List<ErrorBarcode> LIST) {
        this.LIST = LIST;
    }

    @Override
    public String toString() {
        return "ErrorBarcodeParamer{" +
                "BB_UUID='" + BB_UUID + '\'' +
                ", SYSTEM_SERVICE_TYPE='" + SYSTEM_SERVICE_TYPE + '\'' +
                ", LIST=" + LIST +
                '}';
    }
}
