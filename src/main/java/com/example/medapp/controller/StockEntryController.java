package com.example.medapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.Login.LoginResponse;
import com.example.medapp.model.medapp_stock.MedappMedicineMaster;
import com.example.medapp.model.medapp_stock.MedappStock;
import com.example.medapp.model.stockentry.StockEntryResponse;
import com.example.medapp.service.stockentry.StockEntryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class StockEntryController {

    @Autowired
    private StockEntryService medappStockService;

    @GetMapping("/getstock")
    public ResponseEntity<StockEntryResponse> GetAvailableStocks() {

        return ResponseEntity.ok(medappStockService.getAvailableStocks());
    }

    @PutMapping("/addmedicine")
    public ResponseEntity<StockEntryResponse> AddMedicine(@RequestHeader("USER") String user,
            @RequestBody MedappMedicineMaster pNewMedicine) {

        return ResponseEntity.ok(medappStockService.AddNewMedicine(pNewMedicine, user));
    }

    @PutMapping("/updatestock")
    public ResponseEntity<LoginResponse> UpdateStockEntry(@RequestHeader("USER") String user,
            @RequestBody MedappStock pNewMedicine) {

        return ResponseEntity.ok(medappStockService.UpdateStockEntry(pNewMedicine, user));
    }

}
