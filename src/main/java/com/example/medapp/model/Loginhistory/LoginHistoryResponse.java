package com.example.medapp.model.Loginhistory;

import java.util.List;

import com.example.medapp.model.Login.Medapp_login_history;

public class LoginHistoryResponse {

    private List<Medapp_login_history> login_historyarr;
    private String errMsg;
    private String status;
    private String msg;

    public LoginHistoryResponse(List<Medapp_login_history> login_historyarr, String errMsg, String status, String msg) {
        this.login_historyarr = login_historyarr;
        this.errMsg = errMsg;
        this.status = status;
        this.msg = msg;
    }

    public LoginHistoryResponse() {

    }

    public List<Medapp_login_history> getLogin_historyarr() {
        return login_historyarr;
    }

    public void setLogin_historyarr(List<Medapp_login_history> login_historyarr) {
        this.login_historyarr = login_historyarr;
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
