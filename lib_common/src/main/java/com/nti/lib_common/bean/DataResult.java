package com.nti.lib_common.bean;

/**
 * @author: weiqiyuan
 * @date: 2022/8/31
 * @describe
 */
public class DataResult<T> {
    private int errcode;
    private T t;

    public DataResult(int errcode, T t) {
        this.errcode = errcode;
        this.t = t;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "errcode=" + errcode +
                ", t=" + t +
                '}';
    }
}
