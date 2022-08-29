package com.nti.module_returninbound.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/8/2
 * @describe
 */
public class UpdataStatuesParamer {
    private String BB_UUID;
    private String BB_STATE;
    private String SYSTEM_SERVICE_TYPE;

    public UpdataStatuesParamer(String BB_UUID, String BB_STATE, String SYSTEM_SERVICE_TYPE) {
        this.BB_UUID = BB_UUID;
        this.BB_STATE = BB_STATE;
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    public String getBB_UUID() {
        return BB_UUID;
    }

    public void setBB_UUID(String BB_UUID) {
        this.BB_UUID = BB_UUID;
    }

    public String getBB_STATE() {
        return BB_STATE;
    }

    public void setBB_STATE(String BB_STATE) {
        this.BB_STATE = BB_STATE;
    }

    public String getSYSTEM_SERVICE_TYPE() {
        return SYSTEM_SERVICE_TYPE;
    }

    public void setSYSTEM_SERVICE_TYPE(String SYSTEM_SERVICE_TYPE) {
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    @Override
    public String toString() {
        return "UpdataStatuesParamer{" +
                "BB_UUID='" + BB_UUID + '\'' +
                ", BB_STATE='" + BB_STATE + '\'' +
                ", SYSTEM_SERVICE_TYPE='" + SYSTEM_SERVICE_TYPE + '\'' +
                '}';
    }
}
