package com.example.medapp.model.billentry;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.medapp.model.medapp_stock.MedappMedicineMaster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Component
@Table(name = "medapp_bill_details")
public class Medapp_bill_details {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "bill_details_id")
    private Integer billDetailsId;
    @Column(name = "bill_no")
    private Integer billNo;

    @ManyToOne
    @JoinColumn(name = "medicine_master_id")
    private MedappMedicineMaster medicineMaster;

    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unit_price")
    private Float unitPrice;
    @Column(name = "amount")
    private Float amount;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Integer getBillDetailsId() {
        return billDetailsId;
    }

    public void setBillDetailsId(Integer billDetailsId) {
        this.billDetailsId = billDetailsId;
    }

    public Integer getBillNo() {
        return billNo;
    }

    public void setBillNo(Integer billNo) {
        this.billNo = billNo;
    }

    public MedappMedicineMaster getMedicineMaster() {
        return medicineMaster;
    }

    public void setMedicineMaster(MedappMedicineMaster medicineMaster) {
        this.medicineMaster = medicineMaster;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public Medapp_bill_details() {
    }

    public Medapp_bill_details(Integer billDetailsId, Integer billNo, MedappMedicineMaster medicineMaster,
            Integer quantity, Float unitPrice, Float amount, String createdBy, LocalDateTime createdDate,
            String updatedBy, LocalDateTime updatedDate) {
        this.billDetailsId = billDetailsId;
        this.billNo = billNo;
        this.medicineMaster = medicineMaster;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Medapp_bill_details [billDetailsId=" + billDetailsId + ", billNo=" + billNo + ", medicineMaster="
                + medicineMaster + ", quantity=" + quantity + ", unitPrice=" + unitPrice + ", amount=" + amount
                + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + "]";
    }
    // Getters and Setters

}
