package com.example.medapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.billentry.BillerDashRespStruct;
import com.example.medapp.service.billentry.BillerDashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class BillerDash {

    @Autowired
    private BillerDashService billerdashsrvrepo;

    @GetMapping("/billerdash")
    public ResponseEntity<BillerDashRespStruct> getBillerDashDetail(@RequestHeader("LOGINID") Integer loginid) {
        // String lLoginId = String.valueOf(loginid);
        return ResponseEntity.ok(billerdashsrvrepo.GetBillerDashDetails(loginid));

    }

}
// type BillerDashRespStruct struct {
// Today_Sales float64 `json:"todaysales"`
// Yesterday_Sales float64 `json:"yesterdaysales"`
// ErrMsg string `json:"errmsg"`
// Status string `json:"status"`
// Msg string `json:"msg"`
// }
