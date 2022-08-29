package com.nti.lib_common.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class SellBarcodeReciveParamer {
    private UploadSellParamer params;

    public SellBarcodeReciveParamer(UploadSellParamer params) {
        this.params = params;
    }

    public UploadSellParamer getParams() {
        return params;
    }

    public void setParams(UploadSellParamer params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "SellBarcodeReciveParamer{" +
                "params=" + params +
                '}';
    }
}
