package com.example.medapp.model.billentry;

public class BillerDashRespStruct {

    private double todaysales;
    private double yesterdaysales;
    private String errmsg;
    private String status;
    private String msg;

    // No-args constructor
    public BillerDashRespStruct() {
    }

    // All-args constructor
    public BillerDashRespStruct(double todaysales, double yesterdaysales, String errmsg, String status, String msg) {
        this.todaysales = todaysales;
        this.yesterdaysales = yesterdaysales;
        this.errmsg = errmsg;
        this.status = status;
        this.msg = msg;
    }

    // Getters and Setters
    public double getTodaysales() {
        return todaysales;
    }

    public void setTodaysales(double todaysales) {
        this.todaysales = todaysales;
    }

    public double getYesterdaysales() {
        return yesterdaysales;
    }

    public void setYesterdaysales(double yesterdaysales) {
        this.yesterdaysales = yesterdaysales;
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
}

