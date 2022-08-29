package com.nti.module_salesexactory.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/7/21
 * @describe
 */
public class UpParamer {
    private UpdataStatuesParamer params;

    public UpParamer(UpdataStatuesParamer params) {
        this.params = params;
    }

    public UpdataStatuesParamer getParams() {
        return params;
    }

    public void setParams(UpdataStatuesParamer params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "UpParamer{" +
                "params=" + params +
                '}';
    }
}
