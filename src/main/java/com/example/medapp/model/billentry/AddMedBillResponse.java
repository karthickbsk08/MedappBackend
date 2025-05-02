package com.example.medapp.model.billentry;

public class AddMedBillResponse {

    private AddMedicine medicinearr;
    private String status;
    private String msg;
    private String errMsg;

    public AddMedicine getMedicinearr() {
        return medicinearr;
    }

    public void setMedicinearr(AddMedicine medicinearr) {
        this.medicinearr = medicinearr;
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

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public AddMedBillResponse(AddMedicine medicinearr, String status, String msg, String errMsg) {
        this.medicinearr = medicinearr;
        this.status = status;
        this.msg = msg;
        this.errMsg = errMsg;
    }

    public AddMedBillResponse() {
    }

    @Override
    public String toString() {
        return "AddMedBillResponse [medicinearr=" + medicinearr + ", status=" + status + ", msg=" + msg + ", errMsg="
                + errMsg + "]";
    }

}
