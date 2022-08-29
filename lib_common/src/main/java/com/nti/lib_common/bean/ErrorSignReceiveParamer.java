package com.nti.lib_common.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
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
