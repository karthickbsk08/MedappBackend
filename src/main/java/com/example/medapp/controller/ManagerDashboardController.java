package com.example.medapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.managerdash.ManagerDashRespDetail;
import com.example.medapp.model.managerdash.SalesReportReq;
import com.example.medapp.model.managerdash.SalesReportResponse;
import com.example.medapp.service.managerDash.ManagerDashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class ManagerDashboardController {

    @Autowired
    private ManagerDashService managerDashSrv;

    @PutMapping("/salesreport")
    public ResponseEntity<SalesReportResponse> getSalesReport(@RequestBody SalesReportReq pBody) {
        return ResponseEntity.ok(managerDashSrv.getSalesReport(pBody));
    }

    @GetMapping("/managerdashboard")
    public ResponseEntity<ManagerDashRespDetail> GetFinalManagerDashBoardDetl() {
        return ResponseEntity.ok(managerDashSrv.GetFinalDashDetails());
    }

}
