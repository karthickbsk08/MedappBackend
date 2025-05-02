package com.example.medapp.model.medapp_stock;

public class MedappStockView {

    private String medicineName;
    private String brand;
    private Float unitPrice;
    private Integer quantity;

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

    public MedappStockView(String medicineName, String brand, Float unitPrice, Integer quantity) {
        this.medicineName = medicineName;
        this.brand = brand;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public MedappStockView() {
    }

}
