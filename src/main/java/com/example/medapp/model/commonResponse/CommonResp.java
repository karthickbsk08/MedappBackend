package com.example.medapp.model.commonResponse;

public class CommonResp {

    private String status;
    private String errMsg;
    private String msg;

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

    public CommonResp(String status, String errMsg, String msg) {
        this.status = status;
        this.errMsg = errMsg;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CommonResp [status=" + status + ", errMsg=" + errMsg + ", msg=" + msg + "]";
    }

    public CommonResp() {
    }

}
