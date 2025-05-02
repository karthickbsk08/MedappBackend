package com.example.medapp.model.managerdash;
import java.util.List;

public class SalesReportResponse {

    List<Object> salesarr;
    String errmsg;
    String status;
    String msg;

    public List<Object> getSalesarr() {
        return salesarr;
    }

    public void setSalesarr(List<Object> salesarr) {
        this.salesarr = salesarr;
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

    public SalesReportResponse(List<Object> salesarr, String errmsg, String status, String msg) {
        this.salesarr = salesarr;
        this.errmsg = errmsg;
        this.status = status;
        this.msg = msg;
    }

    public SalesReportResponse() {
    }

    @Override
    public String toString() {
        return "SalesReportResponse{" +
                "salesarr=" + salesarr +
                ", errmsg='" + errmsg + '\'' +
                ", status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
