package com.example.medapp.model.stockentry;

import java.util.List;

import com.example.medapp.model.medapp_stock.MedappStockView;

public class MedappStockViewResponse {

    private List<MedappStockView> stockArr;
    private String errMsg;
    private String status;
    private String msg;

    public MedappStockViewResponse(List<MedappStockView> stockArr, String errMsg, String status, String msg) {
        this.stockArr = stockArr;
        this.errMsg = errMsg;
        this.status = status;
        this.msg = msg;
    }

    public MedappStockViewResponse() {
    }

    public List<MedappStockView> getStockArr() {
        return stockArr;
    }

    public void setStockArr(List<MedappStockView> stockArr) {
        this.stockArr = stockArr;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

