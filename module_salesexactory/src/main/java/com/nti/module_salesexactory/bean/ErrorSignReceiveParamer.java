package com.nti.module_salesexactory.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/7/23
 * @describe
 */
public class ErrorSignReceiveParamer {
    private ErrorBarcodeParamer params;

    public ErrorSignReceiveParamer(ErrorBarcodeParamer params) {
        this.params = params;
    }

    public ErrorBarcodeParamer getParams() {
        return params;
    }

    public void setParams(ErrorBarcodeParamer params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ErrorSignReceiveParamer{" +
                "params=" + params +
                '}';
    }
}
