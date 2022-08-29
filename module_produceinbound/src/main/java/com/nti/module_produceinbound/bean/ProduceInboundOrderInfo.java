package com.nti.module_produceinbound.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @author: weiqiyuan
 * @date: 2022/8/3
 * @describe
 */
public class ProduceInboundOrderInfo extends LitePalSupport {
    //单据uuid
    private String BB_UUID;
    //合同号
    private String BB_CONTRACT_NO;
    //票据类型
    private String BB_BT_CODE;
    //票号
    private String BB_TICKET_NO;
    //仓库编号
    private String BB_WS_CODE;
    //关联合同号
    private String BB_RELATE_CONTRACT_NO;
    //需方名称
    private String B_NAME;
    //单据录入日期
    private String BB_INPUT_DATE;
    //总万支
    private String BB_TOTAL_ALL_NUM1;
    //实际扫码量件
    private String BB_TOTAL_SCAN_NUM;
    //流向名称
    private String BB_FLOW_NAME;
    //总件
    private String BB_TOTAL_PNUM;
    //单据状态 0-未启动扫描 1-扫描中 2-已确认 3-未确认 4-已启动 5-已回送
    private String BB_STATE;

    private boolean ispressed;

    private String PDA_SCANNER_IS_END;

    public ProduceInboundOrderInfo(String BB_UUID, String BB_CONTRACT_NO, String BB_BT_CODE, String BB_TICKET_NO,
                                 String BB_WS_CODE, String BB_RELATE_CONTRACT_NO, String b_NAME, String BB_INPUT_DATE,
                                 String BB_TOTAL_ALL_NUM1, String BB_TOTAL_SCAN_NUM, String BB_TOTAL_PNUM, String BB_FLOW_NAME,
                                 String BB_STATE, String PDA_SCANNER_IS_END) {
        this.BB_UUID = BB_UUID;
        this.BB_CONTRACT_NO = BB_CONTRACT_NO;
        this.BB_BT_CODE = BB_BT_CODE;
        this.BB_TICKET_NO = BB_TICKET_NO;
        this.BB_WS_CODE = BB_WS_CODE;
        this.BB_RELATE_CONTRACT_NO = BB_RELATE_CONTRACT_NO;
        this.B_NAME = b_NAME;
        this.BB_INPUT_DATE = BB_INPUT_DATE;
        this.BB_TOTAL_ALL_NUM1 = BB_TOTAL_ALL_NUM1;
        this.BB_TOTAL_SCAN_NUM = BB_TOTAL_SCAN_NUM;
        this.BB_TOTAL_PNUM = BB_TOTAL_PNUM;
        this.BB_FLOW_NAME = BB_FLOW_NAME;
        this.BB_STATE = BB_STATE;
        this.PDA_SCANNER_IS_END = PDA_SCANNER_IS_END;
    }

    public String getBB_UUID() {
        return BB_UUID;
    }

    public void setBB_UUID(String BB_UUID) {
        this.BB_UUID = BB_UUID;
    }

    public String getBB_CONTRACT_NO() {
        return BB_CONTRACT_NO;
    }

    public void setBB_CONTRACT_NO(String BB_CONTRACT_NO) {
        this.BB_CONTRACT_NO = BB_CONTRACT_NO;
    }

    public String getBB_BT_CODE() {
        return BB_BT_CODE;
    }

    public void setBB_BT_CODE(String BB_BT_CODE) {
        this.BB_BT_CODE = BB_BT_CODE;
    }

    public String getBB_TICKET_NO() {
        return BB_TICKET_NO;
    }

    public void setBB_TICKET_NO(String BB_TICKET_NO) {
        this.BB_TICKET_NO = BB_TICKET_NO;
    }

    public String getBB_WS_CODE() {
        return BB_WS_CODE;
    }

    public void setBB_WS_CODE(String BB_WS_CODE) {
        this.BB_WS_CODE = BB_WS_CODE;
    }

    public String getBB_RELATE_CONTRACT_NO() {
        return BB_RELATE_CONTRACT_NO;
    }

    public void setBB_RELATE_CONTRACT_NO(String BB_RELATE_CONTRACT_NO) {
        this.BB_RELATE_CONTRACT_NO = BB_RELATE_CONTRACT_NO;
    }

    public String getB_NAME() {
        return B_NAME;
    }

    public void setB_NAME(String b_NAME) {
        B_NAME = b_NAME;
    }

    public String getBB_INPUT_DATE() {
        return BB_INPUT_DATE;
    }

    public void setBB_INPUT_DATE(String BB_INPUT_DATE) {
        this.BB_INPUT_DATE = BB_INPUT_DATE;
    }

    public String getBB_TOTAL_ALL_NUM1() {
        return BB_TOTAL_ALL_NUM1;
    }

    public void setBB_TOTAL_ALL_NUM1(String BB_TOTAL_ALL_NUM1) {
        this.BB_TOTAL_ALL_NUM1 = BB_TOTAL_ALL_NUM1;
    }

    public String getBB_TOTAL_SCAN_NUM() {
        return BB_TOTAL_SCAN_NUM;
    }

    public void setBB_TOTAL_SCAN_NUM(String BB_TOTAL_SCAN_NUM) {
        this.BB_TOTAL_SCAN_NUM = BB_TOTAL_SCAN_NUM;
    }

    public String getBB_FLOW_NAME() {
        return BB_FLOW_NAME;
    }

    public void setBB_FLOW_NAME(String BB_FLOW_NAME) {
        this.BB_FLOW_NAME = BB_FLOW_NAME;
    }

    public String getBB_TOTAL_PNUM() {
        return BB_TOTAL_PNUM;
    }

    public void setBB_TOTAL_PNUM(String BB_TOTAL_PNUM) {
        this.BB_TOTAL_PNUM = BB_TOTAL_PNUM;
    }

    public String getBB_STATE() {
        return BB_STATE;
    }

    public void setBB_STATE(String BB_STATE) {
        this.BB_STATE = BB_STATE;
    }

    public boolean isIspressed() {
        return ispressed;
    }

    public void setIspressed(boolean ispressed) {
        this.ispressed = ispressed;
    }

    public String getPDA_SCANNER_IS_END() {
        return PDA_SCANNER_IS_END;
    }

    public void setPDA_SCANNER_IS_END(String PDA_SCANNER_IS_END) {
        this.PDA_SCANNER_IS_END = PDA_SCANNER_IS_END;
    }

    @Override
    public String toString() {
        return "ProduceInboundOrderInfo{" +
                "BB_UUID='" + BB_UUID + '\'' +
                ", BB_CONTRACT_NO='" + BB_CONTRACT_NO + '\'' +
                ", BB_BT_CODE='" + BB_BT_CODE + '\'' +
                ", BB_TICKET_NO='" + BB_TICKET_NO + '\'' +
                ", BB_WS_CODE='" + BB_WS_CODE + '\'' +
                ", BB_RELATE_CONTRACT_NO='" + BB_RELATE_CONTRACT_NO + '\'' +
                ", B_NAME='" + B_NAME + '\'' +
                ", BB_INPUT_DATE='" + BB_INPUT_DATE + '\'' +
                ", BB_TOTAL_ALL_NUM1='" + BB_TOTAL_ALL_NUM1 + '\'' +
                ", BB_TOTAL_SCAN_NUM='" + BB_TOTAL_SCAN_NUM + '\'' +
                ", BB_FLOW_NAME='" + BB_FLOW_NAME + '\'' +
                ", BB_TOTAL_PNUM='" + BB_TOTAL_PNUM + '\'' +
                ", BB_STATE='" + BB_STATE + '\'' +
                ", ispressed=" + ispressed +
                ", PDA_SCANNER_IS_END='" + PDA_SCANNER_IS_END + '\'' +
                '}';
    }
}
