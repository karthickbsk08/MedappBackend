package com.example.medapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.medapp.model.billentry.AddMedBillResponse;
import com.example.medapp.model.billentry.BillEntryResponse;
import com.example.medapp.model.billentry.BillStockResponse;
import com.example.medapp.model.billentry.saveBillRequest;
import com.example.medapp.model.medapp_stock.MedappStock;
import com.example.medapp.service.billentry.BillEntryService;

@RestController
public class BillEntryController {

    @Autowired
    private BillEntryService billEntryService;

    @GetMapping("/billstock")
    public ResponseEntity<BillStockResponse> getBillStock() {
        return ResponseEntity.ok(billEntryService.getBillStock());
    }

    @PutMapping("/addbill")
    public ResponseEntity<AddMedBillResponse> AddBillEntry(@RequestBody MedappStock newBill) {
        // System.out.println("NEWBILL : "+ newBill);
        return ResponseEntity.ok(billEntryService.AddBill(newBill));

    }

    @PutMapping("/savebill")
    public ResponseEntity<BillEntryResponse> saveBillEntry(@RequestBody saveBillRequest newBill) {
        return ResponseEntity.ok(billEntryService.saveBill(newBill));
    }

}
