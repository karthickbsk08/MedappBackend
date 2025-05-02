package com.example.medapp.model.managerdash;

import java.math.BigDecimal;
import java.sql.Date;

public class SalesReportData {

    Integer bill_no;
    Date bill_date;
    String medicine_name;
    Integer quantity;
    BigDecimal amount;

    public Integer getBill_no() {
        return bill_no;
    }

    public void setBill_no(Integer bill_no) {
        this.bill_no = bill_no;
    }

    public Date getBill_date() {
        return bill_date;
    }

    public void setBill_date(Date bill_date) {
        this.bill_date = bill_date;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public SalesReportData(Integer bill_no, Date bill_date, String medicine_name, Integer quantity, BigDecimal amount) {
        this.bill_no = bill_no;
        this.bill_date = bill_date;
        this.medicine_name = medicine_name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public SalesReportData() {
    }

    @Override
    public String toString() {
        return "SalesReportData [bill_no=" + bill_no + ", bill_date=" + bill_date + ", medicine_name=" + medicine_name
                + ", quantity=" + quantity + ", amount=" + amount + "]";
    }

}

/*
 * type SalesReportStruct struct {
 * Bill_No string `json:"bill_no"`
 * Bill_date string `json:"bill_date"`
 * Medicine_Name string `json:"medicine_name"`
 * Quantity int `json:"quantity"`
 * Amount float64 `json:"amount"`
 * }
 */