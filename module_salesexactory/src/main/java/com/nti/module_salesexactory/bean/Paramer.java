package com.nti.module_salesexactory.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/7/21
 * @describe
 */
public class Paramer {
    private Params params;

    public Paramer(Params params) {
        this.params = params;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Paramer{" +
                "params=" + params +
                '}';
    }
}
