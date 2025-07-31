package com.example.medapp.controller.passkeyAuth.FinishRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.medapp.model.commonResponse.CommonResp;
import com.example.medapp.service.PasskeyAuth.FinishRegistration;
import com.yubico.webauthn.data.AuthenticatorAttestationResponse;
import com.yubico.webauthn.data.ClientRegistrationExtensionOutputs;
import com.yubico.webauthn.data.PublicKeyCredential;

@RestController
public class FinishRegistrationController {

    @Autowired
    private FinishRegistration finishRegistrationservice;

    @PostMapping("/webauthn/register")
    public ResponseEntity<CommonResp> finishRegistration1(@RequestParam String userId,
            @RequestBody PublicKeyCredential<AuthenticatorAttestationResponse, ClientRegistrationExtensionOutputs> credential) {
        System.out.println("011: " + credential);
        System.out.println("012: " + userId);
        return ResponseEntity.ok(finishRegistrationservice.finishRegistrationService(userId, credential));
    }
}
