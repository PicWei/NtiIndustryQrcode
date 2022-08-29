package com.nti.module_device.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/8/1
 * @describe
 */
public class PdaRegisterParamer {
    private PdaRegisterParam params;

    public PdaRegisterParamer(PdaRegisterParam params) {
        this.params = params;
    }

    public PdaRegisterParam getParams() {
        return params;
    }

    public void setParams(PdaRegisterParam params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "PdaRegisterParamer{" +
                "params=" + params +
                '}';
    }
}
