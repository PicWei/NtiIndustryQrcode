package com.nti.lib_common.bean;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class SellParamer {
    private String BB_UUID;
    private List<SalesOrderParamer> CIGLIST;

    public SellParamer(String BB_UUID, List<SalesOrderParamer> CIGLIST) {
        this.BB_UUID = BB_UUID;
        this.CIGLIST = CIGLIST;
    }

    public String getBB_UUID() {
        return BB_UUID;
    }

    public void setBB_UUID(String BB_UUID) {
        this.BB_UUID = BB_UUID;
    }

    public List<SalesOrderParamer> getCIGLIST() {
        return CIGLIST;
    }

    public void setCIGLIST(List<SalesOrderParamer> CIGLIST) {
        this.CIGLIST = CIGLIST;
    }

    @Override
    public String toString() {
        return "SellParamer{" +
                "BB_UUID='" + BB_UUID + '\'' +
                ", CIGLIST=" + CIGLIST +
                '}';
    }
}
