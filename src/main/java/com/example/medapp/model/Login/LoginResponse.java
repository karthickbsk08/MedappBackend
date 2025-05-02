package com.example.medapp.model.Login;

public class LoginResponse {

    private UserDetails userDetails;
    private String errMsg;
    private String status;
    private String msg;

    public LoginResponse(UserDetails userDetails, String errMsg, String status, String msg) {
        this.userDetails = userDetails;
        this.errMsg = errMsg;
        this.status = status;
        this.msg = msg;
    }

    public LoginResponse() {
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
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
        return "LoginResponse [userDetails=" + userDetails + ", errMsg=" + errMsg + ", status=" + status + ", msg="
                + msg + "]";
    }

}

/*
 * type LoginRespStruct struct {
 * User_Details UserStruct `json:"userdetails"`
 * ErrMsg string `json:"errmsg"`
 * Status string `json:"status"`
 * Msg string `json:"msg"`
 * }
 */