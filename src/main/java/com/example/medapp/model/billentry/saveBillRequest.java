package com.example.medapp.model.billentry;

import java.time.LocalDate;

public class saveBillRequest {

    String user_id;
    Integer bill_no;
    LocalDate bill_date;
    Integer login_id;
    BillDetailsRequest[] billsarr;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getBill_no() {
        return bill_no;
    }

    public void setBill_no(Integer bill_no) {
        this.bill_no = bill_no;
    }

    public LocalDate getBill_date() {
        return bill_date;
    }

    public void setBill_date(LocalDate bill_date) {
        this.bill_date = bill_date;
    }

    public Integer getLogin_id() {
        return login_id;
    }

    public void setLogin_id(Integer login_id) {
        this.login_id = login_id;
    }

    public BillDetailsRequest[] getBillsarr() {
        return billsarr;
    }

    public void setBillsarr(BillDetailsRequest[] billsarr) {
        this.billsarr = billsarr;
    }

    public saveBillRequest(String user_id, Integer bill_no, LocalDate bill_date, Integer login_id,
            BillDetailsRequest[] billsarr) {
        this.user_id = user_id;
        this.bill_no = bill_no;
        this.bill_date = bill_date;
        this.login_id = login_id;
        this.billsarr = billsarr;
    }

    public saveBillRequest() {
    }

    @Override
    public String toString() {
        return "saveBill [user_id=" + user_id + ", bill_no=" + bill_no + ", bill_date=" + bill_date + ", login_id="
                + login_id + ", billsarr=" + billsarr + "]";
    }

}
