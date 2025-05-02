package com.example.medapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.medapp_stock.MedAppStockResponse;
import com.example.medapp.service.stockview.MedappStockService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MedappStockController {

    @Autowired
    MedappStockService lMedappStockService;

    @GetMapping("/stockview")
    public ResponseEntity<MedAppStockResponse> getMedAppStock() {

        MedAppStockResponse lStockResponse = lMedappStockService.getMedAppStock();      

        return ResponseEntity.ok(lStockResponse);
    }

}
