package com.nti.module_returninbound.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
 * @describe
 */
public class Params {
    private String PDA_CODE;
    private String SYSTEM_SERVICE_TYPE;

    public Params(String PDA_CODE, String SYSTEM_SERVICE_TYPE) {
        this.PDA_CODE = PDA_CODE;
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    public String getPDA_CODE() {
        return PDA_CODE;
    }

    public void setPDA_CODE(String PDA_CODE) {
        this.PDA_CODE = PDA_CODE;
    }

    public String getSYSTEM_SERVICE_TYPE() {
        return SYSTEM_SERVICE_TYPE;
    }

    public void setSYSTEM_SERVICE_TYPE(String SYSTEM_SERVICE_TYPE) {
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    @Override
    public String toString() {
        return "Params{" +
                "PDA_CODE='" + PDA_CODE + '\'' +
                ", SYSTEM_SERVICE_TYPE='" + SYSTEM_SERVICE_TYPE + '\'' +
                '}';
    }
}
