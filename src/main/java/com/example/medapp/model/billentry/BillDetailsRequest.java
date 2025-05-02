package com.example.medapp.model.billentry;

public class BillDetailsRequest {

    String brand;
    Integer medicineMasterId;
    String medicineName;
    Integer quantity;
    Float totalPrice;
    Float unitPrice;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BillDetailsRequest(String brand, Integer medicineMasterId, String medicineName, Integer quantity,
            Float totalPrice, Float unitPrice) {
        this.brand = brand;
        this.medicineMasterId = medicineMasterId;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.unitPrice = unitPrice;
    }

    public BillDetailsRequest() {
    }

    @Override
    public String toString() {
        return "BillDetailsRequest [brand=" + brand + ", medicineMasterId=" + medicineMasterId + ", medicineName="
                + medicineName + ", quantity=" + quantity + ", totalPrice=" + totalPrice + ", unitPrice=" + unitPrice
                + "]";
    }

}
