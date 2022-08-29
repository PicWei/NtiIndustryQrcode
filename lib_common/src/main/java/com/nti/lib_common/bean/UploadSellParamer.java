package com.nti.lib_common.bean;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class UploadSellParamer {
    private List<SellParamer> receive;
    private String SYSTEM_SERVICE_TYPE;

    public UploadSellParamer(List<SellParamer> receive, String SYSTEM_SERVICE_TYPE) {
        this.receive = receive;
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    public List<SellParamer> getReceive() {
        return receive;
    }

    public void setReceive(List<SellParamer> receive) {
        this.receive = receive;
    }

    public String getSYSTEM_SERVICE_TYPE() {
        return SYSTEM_SERVICE_TYPE;
    }

    public void setSYSTEM_SERVICE_TYPE(String SYSTEM_SERVICE_TYPE) {
        this.SYSTEM_SERVICE_TYPE = SYSTEM_SERVICE_TYPE;
    }

    @Override
    public String toString() {
        return "UploadSellParamer{" +
                "receive=" + receive +
                ", SYSTEM_SERVICE_TYPE='" + SYSTEM_SERVICE_TYPE + '\'' +
                '}';
    }
}
