package com.example.medapp.model.billentry;

import java.util.List;

public class BillStockResponse {

    private List<MedicineNameAndQuantity> billstockarr;
    private int bill_no;
    private String errMsg;
    private String status;
    private String msg;

    public List<MedicineNameAndQuantity> getBillstockarr() {
        return billstockarr;
    }

    public void setBillstockarr(List<MedicineNameAndQuantity> billstockarr) {
        this.billstockarr = billstockarr;
    }

    public int getBill_no() {
        return bill_no;
    }

    public void setBill_no(int bill_no) {
        this.bill_no = bill_no;
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

    @Override
    public String toString() {
        return "BillStockResponse [billstockarr=" + billstockarr + ", bill_no=" + bill_no + ", errMsg=" + errMsg
                + ", status=" + status + ", msg=" + msg + "]";
    }

    public BillStockResponse(List<MedicineNameAndQuantity> billstockarr, int bill_no, String errMsg, String status,
            String msg) {
        this.billstockarr = billstockarr;
        this.bill_no = bill_no;
        this.errMsg = errMsg;
        this.status = status;
        this.msg = msg;
    }

    public BillStockResponse() {
    }

}
