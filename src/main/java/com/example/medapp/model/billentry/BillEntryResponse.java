package com.example.medapp.model.billentry;

public class BillEntryResponse {

    Integer bill_no;
    String errMsg;
    String status;
    String msg;

    public BillEntryResponse(Integer bill_no, String errMsg, String status, String msg) {
        this.bill_no = bill_no;
        this.errMsg = errMsg;
        this.status = status;
        this.msg = msg;
    }

    public BillEntryResponse() {
    }

    public Integer getBill_no() {
        return bill_no;
    }

    public void setBill_no(Integer bill_no) {
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
        return "BillEntryResponse [bill_no=" + bill_no + ", errMsg=" + errMsg + ", status=" + status + ", msg=" + msg
                + "]";
    }

}
