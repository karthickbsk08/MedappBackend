package com.example.medapp.model.billentry;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.medapp.model.Login.Medapp_Login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Component
@Table(name = "medapp_bill_master")
public class Medapp_bill_master {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "bill_master_id")
    private Integer billMasterId;

    @Column(name = "bill_no", unique = true, nullable = false)
    private Integer billNo;
    @Column(name = "bill_date")
    private LocalDate billDate;
    @Column(name = "bill_amount")
    private Float billAmount;
    @Column(name = "bill_gst", nullable = false)
    private Float billGst;
    @Column(name = "net_price", nullable = false)
    private Float netPrice;

    @ManyToOne
    @JoinColumn(name = "login_id")
    private Medapp_Login medappLogin;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Integer getBillMasterId() {
        return billMasterId;
    }

    public void setBillMasterId(Integer billMasterId) {
        this.billMasterId = billMasterId;
    }

    public Integer getBillNo() {
        return billNo;
    }

    public void setBillNo(Integer billNo) {
        this.billNo = billNo;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public Float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Float billAmount) {
        this.billAmount = billAmount;
    }

    public Float getBillGst() {
        return billGst;
    }

    public void setBillGst(Float billGst) {
        this.billGst = billGst;
    }

    public Float getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(Float netPrice) {
        this.netPrice = netPrice;
    }

    public Medapp_Login getMedappLogin() {
        return medappLogin;
    }

    public void setMedappLogin(Medapp_Login medappLogin) {
        this.medappLogin = medappLogin;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Medapp_bill_master() {
    }

    public Medapp_bill_master(Integer billMasterId, Integer billNo, LocalDate billDate, Float billAmount,
            Float billGst, Float netPrice, Medapp_Login medappLogin, String createdBy, LocalDateTime createdDate,
            String updatedBy, LocalDateTime updatedDate) {
        this.billMasterId = billMasterId;
        this.billNo = billNo;
        this.billDate = billDate;
        this.billAmount = billAmount;
        this.billGst = billGst;
        this.netPrice = netPrice;
        this.medappLogin = medappLogin;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Medapp_bill_master [billMasterId=" + billMasterId + ", billNo=" + billNo + ", billDate=" + billDate
                + ", billAmount=" + billAmount + ", billGst=" + billGst + ", netPrice=" + netPrice + ", medappLogin="
                + medappLogin + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + "]";
    }

}

/*
 * CREATE TABLE medapp_bill_master (
 * bill_master_id SERIAL PRIMARY KEY,
 * bill_no INT UNIQUE NOT NULL,
 * bill_date DATE,
 * bill_amount VARCHAR(20),
 * bill_gst VARCHAR(20) NOT NULL,
 * net_price VARCHAR(20) NOT NULL,
 * login_id INT REFERENCES medapp_login(login_id),
 * created_by VARCHAR(30),
 * created_date TIMESTAMP DEFAULT NOW(),
 * updated_by VARCHAR(30),
 * updated_date TIMESTAMP DEFAULT NOW()
 * );
 */