package com.example.medapp.model.billentry;

/* public interface AddMedicine {

    Integer getMedicineMasterId();

    String getMedicineName();

    String getBrand();

    Integer getQuantity();

    Float getUnitPrice();

} */

public interface AddMedicine {

    Integer getMedicineMasterId();

    String getMedicineName();

    String getBrand();

    Integer getQuantity();

    Float getUnitPrice();

    Float getTotalPrice(); 
}

/* type BillSaveRespstruct struct {
	Bill_No int    `json:"bill_no"`
	ErrMsg  string `json:"errmsg"`
	Status  string `json:"status"`
	Msg     string `json:"msg"`
} */
// bill_details_id, bill_no, medicine_master_id, quantity, unit_price, amount, created_by, created_date, updated_by, updated_date