package com.example.medapp.model.managerdash;

import java.util.List;

public class ManagerDashRespDetail {

    Float todaySales;
    Float inventoryValue;
    String errMsg;
    String status;
    String msg;

    List<ChartDetail> dailySalesArr;
    List<ChartDetail> monthlySalesArr;
    List<ChartDetail> currentMonthArr;
    List<ChartDetail> todaySalesArr;

    public Float getTodaySales() {
        return todaySales;
    }

    public void setTodaySales(Float todaySales) {
        this.todaySales = todaySales;
    }

    public Float getInventoryValue() {
        return inventoryValue;
    }

    public void setInventoryValue(Float inventoryValue) {
        this.inventoryValue = inventoryValue;
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

    public List<ChartDetail> getDailySalesArr() {
        return dailySalesArr;
    }

    public void setDailySalesArr(List<ChartDetail> dailySalesArr) {
        this.dailySalesArr = dailySalesArr;
    }

    public List<ChartDetail> getMonthlySalesArr() {
        return monthlySalesArr;
    }

    public void setMonthlySalesArr(List<ChartDetail> monthlySalesArr) {
        this.monthlySalesArr = monthlySalesArr;
    }

    public List<ChartDetail> getCurrentMonthArr() {
        return currentMonthArr;
    }

    public void setCurrentMonthArr(List<ChartDetail> currentMonthArr) {
        this.currentMonthArr = currentMonthArr;
    }

    public List<ChartDetail> getTodaySalesArr() {
        return todaySalesArr;
    }

    public void setTodaySalesArr(List<ChartDetail> todaySalesArr) {
        this.todaySalesArr = todaySalesArr;
    }

    public ManagerDashRespDetail(Float todaySales, Float inventoryValue, String errMsg, String status, String msg,
            List<ChartDetail> dailySalesArr, List<ChartDetail> monthlySalesArr, List<ChartDetail> currentMonthArr,
            List<ChartDetail> todaySalesArr) {
        this.todaySales = todaySales;
        this.inventoryValue = inventoryValue;
        this.errMsg = errMsg;
        this.status = status;
        this.msg = msg;
        this.dailySalesArr = dailySalesArr;
        this.monthlySalesArr = monthlySalesArr;
        this.currentMonthArr = currentMonthArr;
        this.todaySalesArr = todaySalesArr;
    }

    public ManagerDashRespDetail() {
    }

    @Override
    public String toString() {
        return "ManagerDashRespDetail [todaySales=" + todaySales + ", inventoryValue=" + inventoryValue + ", errMsg="
                + errMsg + ", status=" + status + ", msg=" + msg + ", dailySalesArr=" + dailySalesArr
                + ", monthlySalesArr=" + monthlySalesArr + ", currentMonthArr=" + currentMonthArr + ", todaySalesArr="
                + todaySalesArr + "]";
    }

    

}
