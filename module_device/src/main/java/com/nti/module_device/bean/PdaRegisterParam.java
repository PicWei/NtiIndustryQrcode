package com.nti.module_device.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/8/1
 * @describe
 */
public class PdaRegisterParam {
    private String PDA_NAME;
    private String PDA_CODE;
    private String PDA_TYPE;

    public PdaRegisterParam(String PDA_NAME, String PDA_CODE, String PDA_TYPE) {
        this.PDA_NAME = PDA_NAME;
        this.PDA_CODE = PDA_CODE;
        this.PDA_TYPE = PDA_TYPE;
    }

    public String getPDA_NAME() {
        return PDA_NAME;
    }

    public void setPDA_NAME(String PDA_NAME) {
        this.PDA_NAME = PDA_NAME;
    }

    public String getPDA_CODE() {
        return PDA_CODE;
    }

    public void setPDA_CODE(String PDA_CODE) {
        this.PDA_CODE = PDA_CODE;
    }

    public String getPDA_TYPE() {
        return PDA_TYPE;
    }

    public void setPDA_TYPE(String PDA_TYPE) {
        this.PDA_TYPE = PDA_TYPE;
    }

    @Override
    public String toString() {
        return "PdaRegisterParam{" +
                "PDA_NAME='" + PDA_NAME + '\'' +
                ", PDA_CODE='" + PDA_CODE + '\'' +
                ", PDA_TYPE='" + PDA_TYPE + '\'' +
                '}';
    }
}
