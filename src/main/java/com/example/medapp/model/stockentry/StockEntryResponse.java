package com.example.medapp.model.stockentry;

import java.util.List;

public class StockEntryResponse {

    private List<MedicineNameAndBrand> stockarr;
    private String errmsg;
    private String status;
    private String msg;

    public List<MedicineNameAndBrand> getStockarr() {
        return stockarr;
    }

    public void setStockarr(List<MedicineNameAndBrand> stockarr) {
        this.stockarr = stockarr;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
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

    public StockEntryResponse() {
    }

    public StockEntryResponse(List<MedicineNameAndBrand> stockarr, String errmsg, String status, String msg) {
        this.stockarr = stockarr;
        this.errmsg = errmsg;
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "StockEntryResponse [stockarr=" + stockarr + ", errmsg=" + errmsg + ", status=" + status + ", msg=" + msg
                + "]";
    }

}
