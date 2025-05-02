package com.example.medapp.model.managerdash;

public class SalesReportReq {
    String from_date;
    String to_date;

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public SalesReportReq(String from_date, String to_date) {
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public SalesReportReq() {
    }

    @Override
    public String toString() {
        return "SalesReportReq [from_date=" + from_date + ", to_date=" + to_date + "]";
    }

}
