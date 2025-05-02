package com.example.medapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.example.medapp.model.Loginhistory.LoginHistoryResponse;
import com.example.medapp.service.login.LoginHistoryService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class LoginHistoryController {

    @Autowired
    private LoginHistoryService loginHistoryService;

    @GetMapping("/loginhistory")
    public ResponseEntity<LoginHistoryResponse> getLoginHistory() {
        LoginHistoryResponse loginHistory = loginHistoryService.GetAllLoginHistory();
        return ResponseEntity.ok(loginHistory);
    }

}
