package com.example.medapp.model.medapp_stock;

import java.util.List;

public class MedAppStockResponse {

    private String status;
    private String errMsg;
    private String msg;
    private List<MedappStockView> stockviewarr;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<MedappStockView> getStockviewarr() {
        return stockviewarr;
    }

    public void setStockviewarr(List<MedappStockView> lstockArr) {
        this.stockviewarr = lstockArr;
    }

    @Override
    public String toString() {
        return "MedAppStockResponse [status=" + status + ", errMsg=" + errMsg + ", msg=" + msg + ", lstockArr="
                + stockviewarr + "]";
    }

}
