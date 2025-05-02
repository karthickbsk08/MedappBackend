package com.example.medapp.model.medapp_stock;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Component
@Entity(name = "medapp_medicine_master")
public class MedappMedicineMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_master_id")
    private Integer medicineMasterId;
    @Column(name = "medicine_name")
    private String medicineName;
    @Column(name = "brand")
    private String brand;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public MedappMedicineMaster(Integer medicineMasterId, String medicineName, String brand, String createdBy,
            LocalDateTime createdDate, String updatedBy, LocalDateTime updatedDate) {
        this.medicineMasterId = medicineMasterId;
        this.medicineName = medicineName;
        this.brand = brand;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    public MedappMedicineMaster() {
    }

    public Integer getMedicineMasterId() {
        return medicineMasterId;
    }

    public void setMedicineMasterId(Integer medicineMasterId) {
        this.medicineMasterId = medicineMasterId;
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

}
