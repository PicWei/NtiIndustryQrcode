package com.nti.lib_common.bean;

import java.util.List;

/**
 * @author: weiqiyuan
 * @date: 2022/7/27
 * @describe
 */
public class SalesOrderParamer {
    private int BB_TOTAL_PNUM;
    private String BB_PCIG_CODE;
    private int BB_TOTAL_SCAN_NUM;
    private List<SalesBarcodeParamer> LIST;

    public SalesOrderParamer(int BB_TOTAL_PNUM, String BB_PCIG_CODE, int BB_TOTAL_SCAN_NUM, List<SalesBarcodeParamer> LIST) {
        this.BB_TOTAL_PNUM = BB_TOTAL_PNUM;
        this.BB_PCIG_CODE = BB_PCIG_CODE;
        this.BB_TOTAL_SCAN_NUM = BB_TOTAL_SCAN_NUM;
        this.LIST = LIST;
    }

    public int getBB_TOTAL_PNUM() {
        return BB_TOTAL_PNUM;
    }

    public void setBB_TOTAL_PNUM(int BB_TOTAL_PNUM) {
        this.BB_TOTAL_PNUM = BB_TOTAL_PNUM;
    }

    public int getBB_TOTAL_SCAN_NUM() {
        return BB_TOTAL_SCAN_NUM;
    }

    public void setBB_TOTAL_SCAN_NUM(int BB_TOTAL_SCAN_NUM) {
        this.BB_TOTAL_SCAN_NUM = BB_TOTAL_SCAN_NUM;
    }

    public String getBB_PCIG_CODE() {
        return BB_PCIG_CODE;
    }

    public void setBB_PCIG_CODE(String BB_PCIG_CODE) {
        this.BB_PCIG_CODE = BB_PCIG_CODE;
    }

    public List<SalesBarcodeParamer> getLIST() {
        return LIST;
    }

    public void setLIST(List<SalesBarcodeParamer> LIST) {
        this.LIST = LIST;
    }

    @Override
    public String toString() {
        return "SalesOrderParamer{" +
                "BB_TOTAL_PNUM=" + BB_TOTAL_PNUM +
                ", BB_PCIG_CODE='" + BB_PCIG_CODE + '\'' +
                ", BB_TOTAL_SCAN_NUM=" + BB_TOTAL_SCAN_NUM +
                ", LIST=" + LIST +
                '}';
    }
}
