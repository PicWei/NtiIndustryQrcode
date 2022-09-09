package com.nti.lib_common.bean;

public class SearchCodeParamer {

    //条码信息
    private String BB_PCIG_CODE;

    //件码信息
    private String BD_BB_PCIG_CODE;

    public String getBB_PCIG_CODE() {
        return BB_PCIG_CODE;
    }

    public void setBB_PCIG_CODE(String BB_PCIG_CODE) {
        this.BB_PCIG_CODE = BB_PCIG_CODE;
    }

    public String getBD_BB_PCIG_CODE() {
        return BD_BB_PCIG_CODE;
    }

    public void setBD_BB_PCIG_CODE(String BD_BB_PCIG_CODE) {
        this.BD_BB_PCIG_CODE = BD_BB_PCIG_CODE;
    }

    @Override
    public String toString() {
        return "SearchCodeParamer{" +
                "BB_PCIG_CODE='" + BB_PCIG_CODE + '\'' +
                ", BD_BB_PCIG_CODE='" + BD_BB_PCIG_CODE + '\'' +
                '}';
    }
}
