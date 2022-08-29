package com.nti.module_scrap.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @author: weiqiyuan
 * @date: 2022/8/4
 * @describe
 */
public class ScrapDetail extends LitePalSupport {
    // 件烟品牌规格名称
    private String BD_PCIG_NAME;

    //应出/入货量(件)
    private String BD_BILL_PNUM;

    //应出/入货量(条)
    private String BD_BILL_BNUM;

    //单据uuid
    private String BD_BB_UUID;


    //件烟品牌规格代码
    private String BD_PCIG_CODE;

    //实际扫描量条
    private String BD_SCAN_BNUM;

    //实际扫描量件
    private String BD_SCAN_NUM;


    public ScrapDetail(String BD_PCIG_NAME, String BD_BILL_PNUM, String BD_BILL_BNUM, String BD_BB_UUID, String BD_PCIG_CODE, String BD_SCAN_BNUM, String BD_SCAN_NUM) {
        this.BD_PCIG_NAME = BD_PCIG_NAME;
        this.BD_BILL_PNUM = BD_BILL_PNUM;
        this.BD_BILL_BNUM = BD_BILL_BNUM;
        this.BD_BB_UUID = BD_BB_UUID;
        this.BD_PCIG_CODE = BD_PCIG_CODE;
        this.BD_SCAN_BNUM = BD_SCAN_BNUM;
        this.BD_SCAN_NUM = BD_SCAN_NUM;
    }

    public String getBD_PCIG_NAME() {
        return BD_PCIG_NAME;
    }

    public void setBD_PCIG_NAME(String BD_PCIG_NAME) {
        this.BD_PCIG_NAME = BD_PCIG_NAME;
    }

    public String getBD_BILL_PNUM() {
        return BD_BILL_PNUM;
    }

    public void setBD_BILL_PNUM(String BD_BILL_PNUM) {
        this.BD_BILL_PNUM = BD_BILL_PNUM;
    }

    public String getBD_BILL_BNUM() {
        return BD_BILL_BNUM;
    }

    public void setBD_BILL_BNUM(String BD_BILL_BNUM) {
        this.BD_BILL_BNUM = BD_BILL_BNUM;
    }

    public String getBD_BB_UUID() {
        return BD_BB_UUID;
    }

    public void setBD_BB_UUID(String BD_BB_UUID) {
        this.BD_BB_UUID = BD_BB_UUID;
    }

    public String getBD_PCIG_CODE() {
        return BD_PCIG_CODE;
    }

    public void setBD_PCIG_CODE(String BD_PCIG_CODE) {
        this.BD_PCIG_CODE = BD_PCIG_CODE;
    }

    public String getBD_SCAN_BNUM() {
        return BD_SCAN_BNUM;
    }

    public void setBD_SCAN_BNUM(String BD_SCAN_BNUM) {
        this.BD_SCAN_BNUM = BD_SCAN_BNUM;
    }

    public String getBD_SCAN_NUM() {
        return BD_SCAN_NUM;
    }

    public void setBD_SCAN_NUM(String BD_SCAN_NUM) {
        this.BD_SCAN_NUM = BD_SCAN_NUM;
    }

    @Override
    public String toString() {
        return "ScrapDetail{" +
                "BD_PCIG_NAME='" + BD_PCIG_NAME + '\'' +
                ", BD_BILL_PNUM='" + BD_BILL_PNUM + '\'' +
                ", BD_BILL_BNUM='" + BD_BILL_BNUM + '\'' +
                ", BD_BB_UUID='" + BD_BB_UUID + '\'' +
                ", BD_PCIG_CODE='" + BD_PCIG_CODE + '\'' +
                ", BD_SCAN_BNUM='" + BD_SCAN_BNUM + '\'' +
                ", BD_SCAN_NUM='" + BD_SCAN_NUM + '\'' +
                '}';
    }
}
