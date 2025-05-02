package com.example.medapp.model.medapp_stock;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "medapp_stock")
public class MedappStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer stockId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_master_id")
    private MedappMedicineMaster medicineMaster;

    // Add fields for incoming JSON but do not persist them to the database
    @Transient // JPA will ignore this field during persistence
    private String medicineName;

    @Transient // JPA will ignore this field during persistence
    private String brand;

    @Min(0)
    @Column(name = "quantity")
    private Integer quantity;

    @Min(0)
    @Column(name = "unit_price")
    private Float unitPrice;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public MedappMedicineMaster getMedicineMaster() {
        return medicineMaster;
    }

    public void setMedicineMaster(MedappMedicineMaster medicineMaster) {
        this.medicineMaster = medicineMaster;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public MedappStock() {
    }

    public MedappStock(Integer stockId, MedappMedicineMaster medicineMaster, @Min(0) Integer quantity,
            @Min(0) Float unitPrice, String createdBy, LocalDateTime createdDate, String updatedBy,
            LocalDateTime updatedDate) {
        this.stockId = stockId;
        this.medicineMaster = medicineMaster;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "MedappStock [stockId=" + stockId + ", medicineMaster=" + medicineMaster + ", medicineName="
                + medicineName + ", brand=" + brand + ", quantity=" + quantity + ", unitPrice=" + unitPrice
                + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + "]";
    }

    

}
