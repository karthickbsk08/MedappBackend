package com.example.medapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.Login.LoginResponse;
import com.example.medapp.model.Login.Medapp_Login;
import com.example.medapp.service.login.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PutMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Medapp_Login login) {

        System.out.println(login.toString());
        LoginResponse response = loginService.LoginValidate(login);
        // if (response.getStatus().equals("E")) {
        // return ResponseEntity.badRequest().body(response);
        // }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/adduser")
    public ResponseEntity<LoginResponse> AddUser(@RequestBody Medapp_Login login, @RequestHeader("USER") String pUser) {
        return ResponseEntity.ok(loginService.AddUser(login, pUser));
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/api/logout")
    public ResponseEntity<LoginResponse> Logout(@RequestHeader("USER") String pUser, HttpServletRequest request) {

        System.out.println(request.getRequestURL().toString());

        Integer lLoginHistoryId = 0;
        String lUser = "";
        Map<String, Object> lLoginMap = Map.of();

        ObjectMapper obj = new ObjectMapper();
        // Medapp_Login lLogin = new Medapp_Login();

        try {
            lLoginMap = obj.readValue(pUser, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lLoginMap != null) {
            lLoginHistoryId = (Integer) lLoginMap.get("loginHistoryId");
            lUser = (String) lLoginMap.get("userId");

            System.out.println("lLoginHistoryId : " + lLoginHistoryId);
            System.out.println("lUser : " + lUser);
        }

        // System.out.println(lLogin.toString());
        return ResponseEntity.ok(loginService.Logout(lLoginHistoryId, lUser));

    }

}
